package os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

public class Dispatcher {

    private static final Logger log = LoggerFactory.getLogger(Dispatcher.class);

    private final ProcessControlBlock pcb;
    private final Cpu cpu;

    private int currentJob = 0;

    public Dispatcher(ProcessControlBlock pcb, Cpu cpu) {
        this.pcb = pcb;
        this.cpu = cpu;
    }

    public void run() {
        assignCpuRegisters();

    }

    private void assignCpuRegisters() {
        // copy parameter data from PCB to CPU, this preps the OS for calling the CPU
        log.info("Copying parameter data from PCB to CPU");

        cpu.setProgramCounter(pcb.getJobStart(currentJob));
        //set other registers //todo
    }

}
