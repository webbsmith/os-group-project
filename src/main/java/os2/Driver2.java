package os2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import os.*;

public class Driver2 {

    private static final Logger log = LoggerFactory.getLogger(Driver2.class);

    private final Loader2 loader;
    private final Scheduler2 scheduler;
    private final Dispatcher dispatcher;
    private final Cpu cpu;

    public Driver2() {
        Memory memory = new Memory();
        ProgramQueues programQueues = new ProgramQueues();
        Fetcher fetcher = new Fetcher(memory, new Decoder(), new EffectiveAddress(0));
        ProcessControlBlock pcb = new ProcessControlBlock();
        this.cpu = new Cpu(new CpuDmaController(), programQueues, new NewFetcher(new EffectiveAddress(0), new Memory()), new Decoder(), memory, pcb);
        Disk disk = new Disk();
        this.loader = new Loader2();
        this.dispatcher = new Dispatcher(pcb, cpu);
        this.scheduler = Scheduler2.INSTANCE;
    }

    public void run() {
        log.info("Running loader");
        loader.load("program-input.txt");
        while (true) {
            log.info("Running scheduler");
            scheduler.next();
            log.info("Running dispatcher");
//            dispatcher.run();
            log.info("Running CPU");
//            cpu.run();
            waitForInterrupt();
        }
    }

    private void waitForInterrupt() {
        try {
            Thread.sleep(10_000); // 10 seconds
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
