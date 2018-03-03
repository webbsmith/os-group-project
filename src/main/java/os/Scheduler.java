package os;

import java.util.Queue;

public class Scheduler implements Runnable {

    private final Queue<Program> programQueue;
    private final Disk disk;
    private final Memory memory;

    public Scheduler(Queue<Program> programQueue, Disk disk, Memory memory) {
        this.programQueue = programQueue;
        this.disk = disk;
        this.memory = memory;
    }

    public void run() {
        // Load program from disk into RAM
        disk.
        // Note in the PCB the start/finish RAM address

        // Store start address in the base-register (or program counter) of the job

        // Use the IO buffer size info (from the control cards) for allocating spaces in RAM

        // Store the start addresses of the input and output buffer spaces n  RAM

        // The job's prgram counter is different than the cirtual XCPU's prograM COUNTER

        // lOAD ONE OR MULTIPLE APROGRAMS AT A TIME

        // WORKS CLOSELY WITH MEMORY MANAGER AND EFFECTIVE ADDRESS METHOD TO LOAD JOBS INTO RAM

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
