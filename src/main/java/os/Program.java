package os;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    private List<String> registers = new ArrayList<>();

    private String status;

    public int getInputBufferCounterAndIncrement() {
        return inputBufferCounter++;
    }

    public void storeInAccumulator(String data) {
        registers.add(0, data);
    }

    public void setStatus(String s) {
        status = s;
    }
}
