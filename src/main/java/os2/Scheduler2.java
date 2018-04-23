package os2;

import os.Decoder;
import os.Memory;
import os.Program;

import java.util.ArrayList;
import java.util.List;

public enum Scheduler2 {
    INSTANCE;

    private final Memory memory = new Memory();


    private final ProgramQueues2 programQueues2 = ProgramQueues2.INSTANCE;
    private final List<Cpu2> cpus = new ArrayList<>();

    {
        cpus.add(new Cpu2(new Decoder()));
    }

    public void schedule(Program currentProgram) {
        programQueues2.addToNew(currentProgram);
    }

    public void next() {
        for (Cpu2 cpu : cpus) {
            if (cpu.isActive()) continue;

            Program program = programQueues2.nextNew();
            cpu.compute(program);
        }
    }
}
