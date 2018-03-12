package os;

import java.util.Queue;

public class Dispatcher {
    private final ProcessControlBlock pcb;
    private final Cpu cpu;

    public Dispatcher(ProcessControlBlock pcb, Cpu cpu) {
        this.pcb = pcb;
        this.cpu = cpu;
    }

    public void assignCpuRegisters() {
        // copy parameter data from PCB to CPU, this preps the OS for calling the CPU
    }

    public void run() {
        //todo - loop
    }
}
