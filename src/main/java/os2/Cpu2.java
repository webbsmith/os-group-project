package os2;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import os.Decoder;
import os.Operation;
import os.Program;

@RequiredArgsConstructor
public class Cpu2 {
    private static final Logger log = LoggerFactory.getLogger(Cpu2.class);

    //    private final NewFetcher fetcher;
    private final Decoder decoder;
    private final int cpuId;

    private final Disk2 disk = Disk2.INSTANCE;

    private boolean busControl = true;
    private int programCounter = -1;
    private int operationCount = 0;
    private int endOfProgram;
    private int activeProcess;

    private volatile boolean active = false;
    private boolean interrupted = false;

    public int compute(Program program) {
        active = true;
        interrupted = false;
        setProgramCounter(program.getProgramCounter());
        endOfProgram = program.getProgramCounter() + decoder.hexToDecimal(program.getInstructionCount());
        while (!interrupted && programCounter < endOfProgram) {
            String instruction = disk.getWord(programCounter);
            log.debug("instruction: {}", instruction);

            Operation operation = decoder.run(instruction);
            incrementCounter();
            executeOperation(operation, program);
        }
        active = false;
        return 0;
    }


    private void executeOperation(Operation operation, Program program) {
        log.info("Executing {}", operation);
        switch (operation.getOpCode()) {
            case "00": // RD: Reads content of input buffer into an accumulator
                String data;
                if (operation.getSourceRegister2().equals("0000")) {
                    data = decoder.hexToBinary(disk.getWord(program.getInputBufferCounterAndIncrement()).substring(2));
                } else {
                    data = program.getData(operation.getSourceRegister2());
                }
                log.debug("data: {}", data);
                program.storeData(data, operation.getSourceRegister1());
                break;
            case "01": // WR: Writes content of accumulator into output buffer
                writeToOutput(program.getData(operation.getSourceRegister1()));
                break;
            case "02": // ST: Stores register's content to an address
                program.storeData(program.getData(operation.getDestinationRegister()), operation.getBranchRegister());
                break;
            case "03": // LW: Loads content of an address into a register
                program.storeData(program.getData(operation.getBranchRegister()), operation.getDestinationRegister());
                break;
            case "04": // MOV: Transfers the content of one register into another
                program.storeData(program.getData(operation.getSourceRegister1()), operation.getDestinationRegister());
                break;
            case "05": // ADD: Adds content of two S-regs into D-reg
                int sum = binaryToDecimal(program.getData(operation.getSourceRegister1())) + binaryToDecimal(program.getData(operation.getSourceRegister2()));
                program.storeData(decimalToBinary(sum), operation.getDestinationRegister());
                break;
            case "06": // SUB: Subtracts content of two S-regs into D-reg
                int difference = binaryToDecimal(program.getData(operation.getSourceRegister1())) - binaryToDecimal(program.getData(operation.getSourceRegister2()));
                program.storeData(decimalToBinary(difference), operation.getDestinationRegister());
                break;
            case "07": // MUL: Multiplies content of two S-regs into D-reg
                int product = binaryToDecimal(program.getData(operation.getSourceRegister1())) * binaryToDecimal(program.getData(operation.getSourceRegister2()));
                program.storeData(decimalToBinary(product), operation.getDestinationRegister());
                break;
            case "08": // DIV: Divides content of two S-regs into D-reg
                int quotient = binaryToDecimal(program.getData(operation.getSourceRegister1())) / binaryToDecimal(program.getData(operation.getSourceRegister2()));
                program.storeData(decimalToBinary(quotient), operation.getDestinationRegister());
                break;
            case "09": // AND: Logical AND of two S-regs into D-reg
                int logicalAnd = binaryToDecimal(program.getData(operation.getSourceRegister1())) & binaryToDecimal(program.getData(operation.getSourceRegister2()));
                program.storeData(decimalToBinary(logicalAnd), operation.getDestinationRegister());
                break;
            case "0A": // OR: Logical OR of two S-regs into D-reg
                int logicalOr = binaryToDecimal(program.getData(operation.getSourceRegister1())) | binaryToDecimal(program.getData(operation.getSourceRegister2()));
                program.storeData(decimalToBinary(logicalOr), operation.getDestinationRegister());
                break;
            case "0B": // MOVI: Transfers address/data directly into a register
                program.storeData(operation.getAddressOrData(), operation.getDestinationRegister());
                break;
            case "0C": // ADDI: Adds a data value directly to the content of a register
                int sumOfAddI = binaryToDecimal(operation.getAddressOrData()) + binaryToDecimal(program.getData(operation.getDestinationRegister()));
                program.storeData(decimalToBinary(sumOfAddI), operation.getDestinationRegister());
                break;
            case "0D": // MULI: Multiplies a data value directly with the content of a register
                int productOfMulI = binaryToDecimal(operation.getAddressOrData()) * binaryToDecimal(program.getData(operation.getDestinationRegister()));
                program.storeData(decimalToBinary(productOfMulI), operation.getDestinationRegister());
                break;
            case "0E": // DIVI: Divides a data directly to the content of a register
                int quotientOfDivI = binaryToDecimal(operation.getAddressOrData()) / binaryToDecimal(program.getData(operation.getDestinationRegister()));
                program.storeData(decimalToBinary(quotientOfDivI), operation.getDestinationRegister());
                break;
            case "0F": // LDI: Loads data/address directly to the content of a register
                program.storeData(operation.getAddressOrData(), operation.getDestinationRegister());
                break;
            case "10": // SLT: Sets the D-reg to 1 if first S-reg is less than the second S-reg; 0 otherwise (Bobbie's notes say B-reg instead of second S-reg)
                String oneOrZero = "0";
                if (binaryToDecimal(program.getData(operation.getSourceRegister1())) < binaryToDecimal(program.getData(operation.getSourceRegister2()))) {
                    oneOrZero = "1";
                }
                program.storeData(oneOrZero, operation.getDestinationRegister());
                break;
            case "11": // SLTI: Sets the D-reg to 1 if first S-reg is less than a data; 0 otherwise
                String oneOrZeroSltI = "0";
                if (binaryToDecimal(program.getData(operation.getSourceRegister1())) < binaryToDecimal(operation.getAddressOrData())) {
                    oneOrZeroSltI = "1";
                }
                program.storeData(oneOrZeroSltI, operation.getDestinationRegister());
                break;
            case "12": // HLT: Logical end of program
                log.info("HLT: End of program");
                break;
            case "13": // NOP: Does nothing and moves to next instruction
                log.info("NOP: Doing nothing and moving to next instruction");
                break;
            case "14": // JMP: Jumps to a specified location
                programCounter = binaryToDecimal(operation.getAddressOrData()) / 4;
                log.info("JMP: Jumping to {}", programCounter);
                break;
            case "15": // BEQ: Branches to an address when content of B-reg = D-reg
                if (binaryToDecimal(program.getData(operation.getBranchRegister())) == binaryToDecimal(program.getData(operation.getDestinationRegister()))) {
                    programCounter = binaryToDecimal(operation.getAddressOrData()) / 4;
                }
                break;
            case "16": // BNE: Branches to an address when content of B-reg <> D-reg
                if (binaryToDecimal(program.getData(operation.getBranchRegister())) != binaryToDecimal(program.getData(operation.getDestinationRegister()))) {
                    programCounter = binaryToDecimal(operation.getAddressOrData()) / 4;
                }
                break;
            case "17": // BEZ: Branches to an address when content of B-reg = 0
                if (binaryToDecimal(program.getData(operation.getBranchRegister())) == 0) {
                    programCounter = binaryToDecimal(operation.getAddressOrData()) / 4;
                }
                break;
            case "18": // BNZ: Branches to an address when content of B-reg <> 0
                if (binaryToDecimal(program.getData(operation.getBranchRegister())) != 0) {
                    programCounter = binaryToDecimal(operation.getAddressOrData()) / 4;
                }
                break;
            case "19": // BGZ: Branches to an address when content of B-reg > 0
                if (binaryToDecimal(program.getData(operation.getBranchRegister())) > 0) {
                    programCounter = binaryToDecimal(operation.getAddressOrData()) / 4;
                }
                break;
            case "1A": // BLZ: Branches to an address when content of B-reg < 0
                if (binaryToDecimal(program.getData(operation.getBranchRegister())) < 0) {
                    programCounter = binaryToDecimal(operation.getAddressOrData()) / 4;
                }
                break;
        }
        log.info("program status: " + program);
    }

    private void writeToOutput(String data) {
        log.info("OUTPUT BUFFER: {}", data);
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
        operationCount = 0;
    }

    private void incrementCounter() {
        programCounter++;
        operationCount++;
        if (operationCount == 300) {
            log.warn("CPU performed 300 operations for the current program. There may be something wrong.");
            interrupted = true;
        }
    }

    public boolean isActive() {
        return active;
    }

    private static int binaryToDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }

    private static String decimalToBinary(int decimal) {
        return Integer.toBinaryString(decimal);
    }

    public int getCpuId() {
        return cpuId;
    }
}
