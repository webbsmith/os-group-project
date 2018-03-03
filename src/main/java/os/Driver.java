package os;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class Driver {
    private final Loader loader;
    private final Scheduler scheduler;
    private final Dispatcher dispatcher;
    private final Cpu cpu;
    private final Disk disk;
    private final ProcessControlBlock processControlBlock;

    private final Queue<Program> programQueue = new ArrayDeque<Program>();

    public Driver() {
        this.scheduler = new Scheduler(programQueue);
        this.dispatcher = new Dispatcher(programQueue);
        this.cpu = new Cpu(programQueue);
        this.disk = new Disk();
        this.processControlBlock = new ProcessControlBlock();
        this.loader = new Loader(disk, processControlBlock);
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
