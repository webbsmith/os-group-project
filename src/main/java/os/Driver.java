package os;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class Driver {
    private final Loader loader;
    private final Scheduler scheduler;
    private final Dispatcher dispatcher;
    private final Cpu cpu;
    private final Queue<Program> programQueue = new ArrayDeque<Program>();

    public Driver() {
        this.loader = new Loader(programQueue);
        this.scheduler = new Scheduler(programQueue);
        this.dispatcher = new Dispatcher(programQueue);
        this.cpu = new Cpu(programQueue);
    }

    public run() {
        loader.run(cpu);
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
