package os;

import lombok.Data;

import static java.lang.Integer.parseInt;

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

    private long initializationTime = System.currentTimeMillis();
    private long executionStartTime;
    private long completionTime;
    private int ioOperationCount = 0;

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
        return parseInt(binary, 2);
    }

//    // Priority scheduling
//    @Override
//    public int compareTo(Program o) {
//        int priorityComparison = -Integer.compare(parseInt(this.priorityNumber, 16),
//                parseInt(o.priorityNumber, 16));
//        if (priorityComparison != 0) return priorityComparison;
//        // If priorities are the same, judge by program id
//        return Integer.compare(parseInt(id, 16), parseInt(o.id, 16));
//    }

    // Shortest job first scheduling
    @Override
    public int compareTo(Program o) {
        int shortestJobComparison = -Integer.compare(parseInt(this.instructionCount, 16),
                parseInt(o.instructionCount, 16));
        if (shortestJobComparison != 0) return shortestJobComparison;
        // If priorities are the same, judge by program id
        return Integer.compare(parseInt(id, 16), parseInt(o.id, 16));
    }

    public String stats() {
        return "id: " + id + ';' +
                " time_waiting: " + (executionStartTime - initializationTime) + ';' +
                " time_running: " + (completionTime - executionStartTime) + ';' +
                " io_operations: " + ioOperationCount + ';' +
                " memory_percentage: " + (parseInt(inputBufferSize) + ioOperationCount) / 1024.0;
    }

    public String statsCsv() {
        return stats().replaceAll("[a-z_ ;:]+", ",");
    }

    public void incrementIoOperations() {
        ioOperationCount++;
    }
}
