package os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

public class Cpu {
    private static final Logger log = LoggerFactory.getLogger(Cpu.class);

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
        log.debug("read(memoryAddressOfPhysicalData={}, memoryAddressOfBuffer={})", memoryAddressOfPhysicalData, memoryAddressOfBuffer);
        while (!busControl) {
            // busy wait
        }
        busControl = false;
        cpuDmaController.read(memoryAddressOfPhysicalData, memoryAddressOfBuffer);
        busControl = true;
    }

    public void write(int memoryAddressOfPhysicalData, int memoryAddressOfBuffer) {
        log.debug("write(memoryAddressOfPhysicalData={}, memoryAddressOfBuffer={})", memoryAddressOfPhysicalData, memoryAddressOfBuffer);
        while (!busControl) {
            // busy wait
        }
        busControl = false;
        cpuDmaController.write(memoryAddressOfPhysicalData, memoryAddressOfBuffer);
        busControl = true;
    }

    public void run() {
        log.debug("run()");
        //todo
    }
}
