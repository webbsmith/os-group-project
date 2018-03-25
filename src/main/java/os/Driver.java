package os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {

    private static final Logger log = LoggerFactory.getLogger(Driver.class);

    private final Loader loader;
    private final Scheduler scheduler;
    private final Dispatcher dispatcher;
    private final Cpu cpu;

    public Driver() {
        Memory memory = new Memory();
        ProgramQueues programQueues = new ProgramQueues();
        Fetcher fetcher = new Fetcher(memory, new Decoder(), new EffectiveAddress(0));
        this.cpu = new Cpu(programQueues, new CpuDmaController(), fetcher);
        Disk disk = new Disk();
        ProcessControlBlock pcb = new ProcessControlBlock();
        this.loader = new Loader(disk, pcb);
        this.dispatcher = new Dispatcher(pcb, cpu);
        this.scheduler = new Scheduler(disk, 0, memory, pcb);
    }

    public void run() {
        log.info("Running loader");
        loader.run("program-input.txt");
        while (true) {
            log.info("Running scheduler");
            scheduler.run();
            log.info("Running dispatcher");
            dispatcher.run();
            log.info("Running CPU");
            cpu.run();
            waitForInterrupt();
        }
    }

    private void waitForInterrupt() {
        try {
            Thread.sleep(60000); // 10 seconds
        } catch (InterruptedException e) {
            System.out.println("Received interrupt");
            System.exit(2);
        }
        if (Thread.interrupted()) {
            System.out.println("Received interrupt");
            System.exit(2);
        }
    }


}
