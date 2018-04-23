package os;

import lombok.Data;

@Data
public class Program {
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

    private String[] registers = new String[20];

    {
        // initialize 20 registers
        for (int i = 0; i < 20; i++) {
            registers[i] = "empty";
        }
    }

    private String status;

    public int getInputBufferCounterAndIncrement() {
        return inputBufferCounter++;
    }

    public void storeData(String data, int registerNumber) {
        registers[registerNumber - 1] = data;
    }

    public String getData(int registerNumber) {
        return registers[registerNumber - 1];
    }

    public void setStatus(String s) {
        status = s;
    }
}
