package os;

import java.util.Queue;

public class Cpu {
    private final CpuDmaController cpuDmaController;
    private final ProgramQueues programQueues;
    private final Fetcher fetcher;

    private boolean busControl = true;

    public Cpu(ProgramQueues programQueues, CpuDmaController cpuDmaController, Fetcher fetcher) {
        this.cpuDmaController = cpuDmaController;
        this.programQueues = programQueues;
        this.fetcher = fetcher;
    }

    public void read(int memoryAddressOfPhysicalData, int memoryAddressOfBuffer) {
        while (!busControl) {
            // wait
        }
        busControl = false;
        cpuDmaController.read(memoryAddressOfPhysicalData, memoryAddressOfBuffer);
        busControl = true;
    }

    public void write(int memoryAddressOfPhysicalData, int memoryAddressOfBuffer) {
        while (!busControl) {
            // wait
        }
        busControl = false;
        cpuDmaController.write(memoryAddressOfPhysicalData, memoryAddressOfBuffer);
        busControl = true;
    }

    public void run() {

        //todo - loop
    }
}
