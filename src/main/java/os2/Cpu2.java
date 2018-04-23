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

    private final Disk2 disk = Disk2.INSTANCE;

    private boolean busControl = true;
    private int programCounter = -1;
    private int endOfProgram;
    private int activeProcess;

    boolean active = false;
    boolean interrupted = false;

    public int compute(Program program) {
        active = true;
        programCounter = program.getProgramCounter();
        endOfProgram = programCounter + decoder.hexToDecimal(program.getInstructionCount());
        while (!interrupted && programCounter < endOfProgram) {
            String instruction = disk.getWord(programCounter);
            log.debug("instruction: {}", instruction);

            Operation operation = decoder.run(instruction);
            programCounter++;
            executeOperation(operation, program);
        }
        active = false;
        return 0;
    }

    private void executeOperation(Operation operation, Program program) {
        log.info("Executing {}", operation);
        switch (operation.getOpCode()) {
            case "00": // RD: Reads content of input buffer into an accumulator
                String data = disk.getWord(program.getInputBufferCounterAndIncrement());
                log.debug("data: {}", data);

                program.storeInAccumulator(data);

                break;
            case "01": // WR: Writes content of accumulator into output buffer
                break;
            case "02": // ST: Stores register's content to an address
                break;
            case "03": // LW: Loads content of an address into a register
                break;
            case "04": // MOV: Transfers the content of one register into another
                break;
            case "05": // ADD: Adds content of two S-regs into D-reg
                break;
            case "06": // SUB: Subtracts content of two S-regs into D-reg
                break;
            case "07": // MUL: Multiplies content of two S-regs into D-reg
                break;
            case "08": // DIV: Divides content of two S-regs into D-reg
                break;
            case "09": // AND: Logical AND of two S-regs into D-reg
                break;
            case "0A": // OR: Logical OR of two S-regs into D-reg
                break;
            case "0B": // MOVI: Transfers address/data directly into a register
                break;
            case "0C": // ADDI: Adds a data value directly to the content of a register
                break;
            case "0D": // MULI: Multiplies a data value directly with the content of a register
                break;
            case "0E": // DIVI: Divides a data directly to the content of a register
                break;
            case "0F": // LDI: Loads data/address directly to the content of a register
                break;
            case "10": // SLT: Sets the D-reg to 1 if first S-reg is less than the B-reg; 0 otherwise
                break;
            case "11": // SLTI: Sets the D-reg to 1 if first S-reg is less than a data; 0 otherwise
                break;
            case "12": // HLT: Logical end of program
                break;
            case "13": // NOP: Does nothing and moves to next instruction
                break;
            case "14": // JMP: Jumps to a specified location
                break;
            case "15": // BEQ: Branches to an address when content of B-reg = D-reg
                break;
            case "16": // BNE: Branches to an address when content of B-reg <> D-reg
                break;
            case "17": // BEZ: Branches to an address when content of B-reg = 0
                break;
            case "18": // BNZ: Branches to an address when content of B-reg <> 0
                break;
            case "19": // BGZ: Branches to an address when content of B-reg > 0
                break;
            case "1A": // BLZ: Branches to an address when content of B-reg < 0
                break;
        }
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public boolean isActive() {
        return active;
    }
}
