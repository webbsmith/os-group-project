package os;

import java.math.BigInteger;

public class Decoder {

    static String hexToBinary(String hex){
        return new BigInteger(hex, 16).toString(2);
    }

    static String binToHexidecimal(String bin){
        return new BigInteger(bin, 2).toString(16);
    }

    static String binToDecimal(String bin){
        return new BigInteger(bin,2).toString(10);
    }

    static int TypeDetermination(String bits){
        int intbits = Integer.parseInt(bits);
        if(intbits == 00)
            return 1; //Arithmetic Instruction
        if(intbits == 01)
            return 2; //Conditional Branch and Immediate Format
        if(intbits == 10)
            return 3; //Unconditional Jump Format
        if(intbits == 11)
            return 4; //I/O Instruction Format
        return 0;
    }

    static void ArithmeticInstruction(String instruction, String[] Registers){
        System.out.println("Arithmetic Instruction");
        String OPCODE = binToHexidecimal(instruction.substring(2,8));
        System.out.println("OPCODE: " + OPCODE);
        String stringSReg = binToDecimal(instruction.substring(8,12));
        int SRegIndex = Integer.parseInt(stringSReg);
        String stringSReg2 = binToDecimal(instruction.substring(12,16));
        int SReg2Index = Integer.parseInt(stringSReg2);
        String stringDReg = binToDecimal(instruction.substring(16,20));
        int DRegIndex = Integer.parseInt(stringDReg);

        if(OPCODE == "4"){
            System.out.println("Assignment");
//            DReg = SReg;
        }
        if(OPCODE == "5"){
            System.out.println("Addition");
//            DReg = SReg + SReg2;
        }
        if(OPCODE == "6"){
            System.out.println("Subtraction");
//            DReg = SReg - SReg2;
        }
        if(OPCODE == "7"){
            System.out.println("Multiplication");
//            DReg = SReg * SReg2;
        }
        if(OPCODE == "8"){
            System.out.println("Division");
//            DReg = SReg/SReg2
        }
        if(OPCODE == "9"){
            System.out.println("Logical AND of two SRegs");
        }
        if(OPCODE == "10"){
            System.out.println("Is SReg greater than SReg2?");
//            if(SReg > SReg2)
//                DReg = 1
//            else
//                DReg = 0

        }
        else{ //OPCODE is 0A
            System.out.println("Logical OR of two SRegs");
        }
    }
    static void BranchInstruction(String instruction, String[] Registers){
        System.out.println("---Branch Instruction---");
        String OPCODE = instruction.substring(2,8);
        String BReg = instruction.substring(8,12);
        String DReg = instruction.substring(12,16);
        String Address = instruction.substring(16,32);
    }
    static void JumpInstruction(String instruction, String[] Registers){
        System.out.println("---Jump Instruction---");
        String OPCODE = binToHexidecimal(instruction.substring(2,8));
        String Address = instruction.substring(8,32);
        System.out.println("Jump to specified address.");
    }
    static void InputOutputInstruction(String instruction, String[] Registers, String inputBuffer, String outputBuffer){
        System.out.println("---Input/Output Instruction---");
        String OPCODE = binToHexidecimal(instruction.substring(2,8));
        System.out.println("OPCODE: " + OPCODE);
        String stringSReg = binToDecimal(instruction.substring(8,12));
        int SRegIndex = Integer.parseInt(stringSReg);
        String stringSReg2 = binToDecimal(instruction.substring(12,16));
        int SReg2Index = Integer.parseInt(stringSReg2);
        String Address = instruction.substring(16,32);

        if(OPCODE == "0"){
            System.out.println("Read content of I/P buffer into an accumulator");
        }
        else{ //OPCODE is 1
            System.out.println("Writes content of accumulator into O/P buffer");
        }
    }

    public static void main(String[] args) {
        //Buffers
        String inputBuffer = "Null";
        String outputBuffer = "Null";
        String tempBuffer = "Null";

        //Instruction                                                                                                   //INPUT NEEDS TO BE FIXED SAMPLE DATA
        //String instruction = args;
        String hexInstruction = "0x00000045".substring(2,10); //0xC10000C0                                              //SOMETHING ISN'T RIGHT WITH 0x00000045
        String instruction = hexToBinary(hexInstruction);
        System.out.println(instruction);

        //Leading Zeros Fix
        if(instruction.length() != 32){
            int missingZeros = 32 - instruction.length();
            System.out.println(missingZeros);
            String fixedInstruction = "";
            for (int i = 0; i < missingZeros; i++) {
                fixedInstruction = fixedInstruction.concat("0");
            }
            instruction = fixedInstruction + instruction;
            System.out.println(instruction);
        }

        //VISUALIZATION - TO BE DELETED
        System.out.println("-----BINARY SEPARATION-----");
        System.out.println("ARITHMETIC");
        System.out.println(instruction.substring(0,2) + " " + instruction.substring(2,8) + " " + instruction.substring(8,12) + " " + instruction.substring(12,16) + " " + instruction.substring(16,20) + " " +instruction.substring(20,32));
        System.out.println();
        System.out.println("JUMP");
        System.out.println(instruction.substring(0,2) + " " + instruction.substring(2,8) + " " + instruction.substring(8,32));
        System.out.println();
        System.out.println("INPUT OUTPUT");
        System.out.println(instruction.substring(0,2) + " " + instruction.substring(2,8) + " " + instruction.substring(8,12) + " " + instruction.substring(12,16) + " " + instruction.substring(16,32));
        System.out.println();

        //Registers
        String[] Registers = new String[16];

        int type = TypeDetermination(instruction.substring(0,2)); //First two bits passed into type determination

        if(type == 0){
            System.out.println("Error. Instruction not recognized. Moving to next instruction.");
            System.exit(0);                                                                                       // Do I need to handle this a different way?
        }
        if(type == 1)
            ArithmeticInstruction(instruction, Registers);
        else if(type == 2)
            BranchInstruction(instruction, Registers);
        else if(type == 3)
            JumpInstruction(instruction, Registers);
        else if(type == 4)
            InputOutputInstruction(instruction, Registers, inputBuffer, outputBuffer);
    }
}
