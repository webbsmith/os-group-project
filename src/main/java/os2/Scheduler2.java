package os2;

import lombok.extern.slf4j.Slf4j;
import os.Decoder;
import os.Memory;
import os.Program;

import java.util.Deque;
import java.util.LinkedList;

@Slf4j
public enum Scheduler2 {
    INSTANCE;

    private final Memory memory = new Memory();

    private final ProgramQueues2 programQueues2 = ProgramQueues2.INSTANCE;
    private final Deque<Cpu2> cpuQueue = new LinkedList<>();

    {
        for (int i = 0; i < Main.CPU_COUNT; i++)
            cpuQueue.add(new Cpu2(new Decoder(), i));
    }

    public void schedule(Program currentProgram) {
        programQueues2.addToNew(currentProgram);
    }

    public void next() {
        if (Thread.currentThread().isInterrupted()) return;
        while (!cpuQueue.isEmpty()) {

            Cpu2 cpu = cpuQueue.poll();

            if (cpu.isActive()) {
                continue;
            }

            Program program = programQueues2.nextNew();
            if (program == null) {
                log.info("All programs executed");
                Thread.currentThread().interrupt();
                return;
            }

            program.setStatus("RUNNING");
            program.setCpuId(cpu.getCpuId());
            new Thread(() -> {
                cpu.compute(program);
                cpuQueue.add(cpu);
                program.setStatus("TERMINATED");
            }).start();

        }
    }

}
