package os;

import lombok.Data;

@Data
public class Program implements Comparable<Program> {
    // All of these are in hex
    private String id;
    private String instructionCount;
    private String priorityNumber;
    private String inputBufferSize;
    private String outputBufferSize;
    private String temporaryBufferSize;
    // End hex vars
    private int programCounter;
    private int inputBufferCounter;
    private int cpuId;
    private String[] registers = new String[20];

    {
        // initialize 20 registers
        for (int i = 0; i < 20; i++) {
            registers[i] = "0";
        }
    }

    private String status;

    public int getInputBufferCounterAndIncrement() {
        return inputBufferCounter++;
    }

    public void storeData(String data, int registerNumber) {
        registers[registerNumber] = data;
    }

    public void storeData(String data, String registerNumberInBinary) {
        registers[binaryToDecimal(registerNumberInBinary)] = data;
    }

    public String getData(int registerNumber) {
        return registers[registerNumber];
    }

    public String getData(String registerNumberInBinary) {
        return registers[binaryToDecimal(registerNumberInBinary)];
    }

    public void setStatus(String s) {
        status = s;
    }

    private static int binaryToDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }

    @Override
    public int compareTo(Program o) {
        return -Integer.compare(Integer.parseInt(this.priorityNumber, 16), Integer.parseInt(o.priorityNumber, 16));
    }
}
