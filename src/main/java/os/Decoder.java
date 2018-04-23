package os;

import java.math.BigInteger;

public class Decoder {

    public int hexToDecimal(String hex) {
        return new BigInteger(hex, 16).intValue();
    }

    private String hexToBinary(String hex){
        return new BigInteger(hex, 16).toString(2);
    }

    private String binToHexidecimal(String bin){
        return new BigInteger(bin, 2).toString(16);
    }

    private String binToDecimal(String bin){
        return new BigInteger(bin,2).toString(10);
    }

    private int typeDetermination(String bits){
        int intbits = Integer.parseInt(bits);
        if(intbits == 0)
            return 1; //Arithmetic Instruction
        if(intbits == 1)
            return 2; //Conditional Branch and Immediate Format
        if(intbits == 10)
            return 3; //Unconditional Jump Format
        if(intbits == 11)
            return 4; //I/O Instruction Format
        return 0;
    }

    private Operation arithmeticInstruction(String instruction, String[] Registers){
//        System.out.println("Arithmetic Instruction");
        String OPCODE = binToHexidecimal(instruction.substring(2,8));
//        System.out.println("OPCODE: " + OPCODE);
        String stringSReg = binToDecimal(instruction.substring(8,12));
        int SRegIndex = Integer.parseInt(stringSReg);
        String stringSReg2 = binToDecimal(instruction.substring(12,16));
        int SReg2Index = Integer.parseInt(stringSReg2);
        String stringDReg = binToDecimal(instruction.substring(16,20));
        int DRegIndex = Integer.parseInt(stringDReg);

/*
        if(OPCODE.equals("4")){
            System.out.println("Assignment");
//            DReg = SReg;
        }
        if(OPCODE.equals("5")){
            System.out.println("Addition");
//            DReg = SReg + SReg2;
        }
        if(OPCODE.equals("6")){
            System.out.println("Subtraction");
//            DReg = SReg - SReg2;
        }
        if(OPCODE.equals("7")){
            System.out.println("Multiplication");
//            DReg = SReg * SReg2;
        }
        if(OPCODE.equals("8")){
            System.out.println("Division");
//            DReg = SReg/SReg2
        }
        if(OPCODE.equals("9")){
            System.out.println("Logical AND of two SRegs");
        }
        if(OPCODE.equals("10")){
            System.out.println("Is SReg greater than SReg2?");
//            if(SReg > SReg2)
//                DReg = 1
//            else
//                DReg = 0

        }
        else{ //OPCODE is 0A
            System.out.println("Logical OR of two SRegs");
        }*/

        return Operation.builder()
                .type(Operation.Type.MATH)
                .opCode(OPCODE)
                .sourceRegister1(stringSReg)
                .sourceRegister2(stringSReg2)
                .destinationRegister(stringDReg)
                .build();
    }
    private Operation branchInstruction(String instruction, String[] Registers){
//        System.out.println("---Branch Instruction---");
        String OPCODE = instruction.substring(2,8);
        String BReg = instruction.substring(8,12);
        String DReg = instruction.substring(12,16);
        String Address = instruction.substring(16,32);
        return Operation.builder()
                .type(Operation.Type.CONDITIONAL_BRANCH)
                .opCode(OPCODE)
                .branchRegister(BReg)
                .destinationRegister(DReg)
                .addressOrData(Address)
                .build();
    }
    private Operation jumpInstruction(String instruction, String[] Registers){
//        System.out.println("---Jump Instruction---");
        String OPCODE = binToHexidecimal(instruction.substring(2,8));
        String Address = instruction.substring(8,32);
//        System.out.println("Jump to specified address.");
        return Operation.builder()
                .type(Operation.Type.UNCONDITIONAL_JUMP)
                .opCode(OPCODE)
                .addressOrData(Address)
                .build();
    }
    private Operation inputOutputInstruction(String instruction, String[] Registers, String inputBuffer, String outputBuffer){
//        System.out.println("---Input/Output Instruction---");
        String OPCODE = binToHexidecimal(instruction.substring(2,8));
//        System.out.println("OPCODE: " + OPCODE);
        String stringSReg = binToDecimal(instruction.substring(8,12));
        int SRegIndex = Integer.parseInt(stringSReg);
        String stringSReg2 = binToDecimal(instruction.substring(12,16));
        int SReg2Index = Integer.parseInt(stringSReg2);
        String Address = instruction.substring(16,32);

        if(OPCODE.equals("0")){
//            System.out.println("Read content of I/P buffer into an accumulator");
        }
        else{ //OPCODE is 1
//            System.out.println("Writes content of accumulator into O/P buffer");
        }
        return Operation.builder()
                .type(Operation.Type.IO_INSTRUCTION)
                .opCode(OPCODE)
                .sourceRegister1(stringSReg)
                .sourceRegister2(stringSReg2)
                .addressOrData(Address)
                .build();
    }

    public Operation run(String hexInstruction) {
//        System.out.println("RUN: " + hexInstruction);
        //Buffers
        String inputBuffer = "Null";
        String outputBuffer = "Null";
        String tempBuffer = "Null";

        //Instruction                                                                                                   //INPUT NEEDS TO BE FIXED SAMPLE DATA
        //String instruction = args;
//        String hexInstruction = "0x00000045".substring(2,10); //0xC10000C0                                              //SOMETHING ISN'T RIGHT WITH 0x00000045
        String instruction = hexToBinary(hexInstruction.substring(2));
//        System.out.println(instruction);

        //Leading Zeros Fix
        if(instruction.length() != 32){
            int missingZeros = 32 - instruction.length();
//            System.out.println(missingZeros);
            String fixedInstruction = "";
            for (int i = 0; i < missingZeros; i++) {
                fixedInstruction = fixedInstruction.concat("0");
            }
            instruction = fixedInstruction + instruction;
//            System.out.println(instruction);
        }

        //VISUALIZATION - TO BE DELETED
//        System.out.println("-----BINARY SEPARATION-----");
//        System.out.println("ARITHMETIC");
//        System.out.println(instruction.substring(0,2) + " " + instruction.substring(2,8) + " " + instruction.substring(8,12) + " " + instruction.substring(12,16) + " " + instruction.substring(16,20) + " " +instruction.substring(20,32));
//        System.out.println();
//        System.out.println("JUMP");
//        System.out.println(instruction.substring(0,2) + " " + instruction.substring(2,8) + " " + instruction.substring(8,32));
//        System.out.println();
//        System.out.println("INPUT OUTPUT");
//        System.out.println(instruction.substring(0,2) + " " + instruction.substring(2,8) + " " + instruction.substring(8,12) + " " + instruction.substring(12,16) + " " + instruction.substring(16,32));
//        System.out.println();

        //Registers
        String[] Registers = new String[16];

        int type = typeDetermination(instruction.substring(0,2)); //First two bits passed into type determination

        if(type == 0){
            System.out.println("Error. Instruction not recognized. Moving to next instruction.");
            System.exit(0);                                                                                       // Do I need to handle this a different way?
        }
        if(type == 1)
            return arithmeticInstruction(instruction, Registers);
        else if(type == 2)
            return branchInstruction(instruction, Registers);
        else if(type == 3)
            return jumpInstruction(instruction, Registers);
        else if(type == 4)
            return inputOutputInstruction(instruction, Registers, inputBuffer, outputBuffer);
        else
            throw new IllegalArgumentException();
    }
}
