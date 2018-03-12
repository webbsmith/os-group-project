package os;

public class Driver {
    private final Loader loader;
    private final Scheduler scheduler;
    private final Dispatcher dispatcher;
    private final Cpu cpu;
    private final Disk disk;
    private final ProcessControlBlock pcb;
    private final Memory memory;

    private final ProgramQueues programQueues = new ProgramQueues();

    public Driver() {
        this.cpu = new Cpu(programQueues, new CpuDmaController());
        this.disk = new Disk();
        this.pcb = new ProcessControlBlock();
        this.loader = new Loader(disk, pcb);
        this.dispatcher = new Dispatcher(pcb, cpu);
        this.memory = new Memory();
        this.scheduler = new Scheduler(programQueues, disk, memory);
    }

    public void run() {
        loader.run();
        while (true) {
            scheduler.run();
            dispatcher.run();
            cpu.run();
            waitForInterrupt();
        }
    }

    private void waitForInterrupt() {
        if (Thread.interrupted()) {
            System.out.println("Received interrupt");
            System.exit(2);
        }
    }


}
