package os;

public class Driver {
    private final Loader loader;
    private final Scheduler scheduler;
    private final Dispatcher dispatcher;
    private final Cpu cpu;

    public Driver() {
        Memory memory = new Memory();
        ProgramQueues programQueues = new ProgramQueues();
        Fetcher fetcher = new Fetcher(memory, new Decoder());
        this.cpu = new Cpu(programQueues, new CpuDmaController(), fetcher);
        Disk disk = new Disk();
        ProcessControlBlock pcb = new ProcessControlBlock();
        this.loader = new Loader(disk, pcb);
        this.dispatcher = new Dispatcher(pcb, cpu);
        this.scheduler = new Scheduler(disk, 0, memory, pcb);
    }

    public void run() {
        loader.run("program-input.txt");
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
