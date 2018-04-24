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

    private volatile boolean stillWorking = true;

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
        while (!interrupted() && isStillWorking()) {
            log.info("Still working" + stillWorking);
            stillWorking = scheduler.next();
        }
    }

    private boolean interrupted() {
        if (Thread.interrupted()) {
            log.info("Thread interrupted");
            return true;
        }
        return false;
    }


    private synchronized boolean isStillWorking() {
        return stillWorking;
    }
}
