package os;

import java.util.Queue;

public class Scheduler implements Runnable {

    Queue<Program> programQueue;
    public Scheduler(Queue<Program> programQueue) {
        this.programQueue = programQueue;
    }

    public void run() {
        while (true) {
            int programRunningCount = 0;
            for (Program program : programQueue) {
                if (program.state() == Program.STATE_RUNNING) {
                    programRunningCount++;
                }
            }
            if (programRunningCount > 0) {

            }
        }
    }
}
