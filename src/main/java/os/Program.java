package os;

import lombok.Data;

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

    private List<Integer> registers;
}
