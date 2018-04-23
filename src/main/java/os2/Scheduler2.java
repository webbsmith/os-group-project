package os2;

import os.Program;

public enum Scheduler2 {
    INSTANCE;

    ProgramQueues2 programQueues2 = ProgramQueues2.INSTANCE;

    public void schedule(Program currentProgram) {
        programQueues2.addToNew(currentProgram);
    }
}
