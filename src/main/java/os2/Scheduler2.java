package os2;

import lombok.extern.slf4j.Slf4j;
import os.Decoder;
import os.Memory;
import os.Program;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public enum Scheduler2 {
    INSTANCE;

    private final Memory memory = new Memory();

    private final ProgramQueues2 programQueues = ProgramQueues2.INSTANCE; // Swap for ProgramQueues2 for FIFO scheduling
    private final Deque<Cpu2> cpuQueue = new LinkedList<>();

    private volatile AtomicInteger activePrograms = new AtomicInteger(0);
    {
        for (int i = 0; i < Main.CPU_COUNT; i++)
            cpuQueue.add(new Cpu2(new Decoder(), i));
    }

    public void schedule(Program currentProgram) {
        programQueues.addToNew(currentProgram);
    }

    public void next() {
        if (Thread.currentThread().isInterrupted()) return;
        while (!cpuQueue.isEmpty()) {

            Cpu2 cpu = cpuQueue.poll();

            if (cpu.isActive()) {
                continue;
            }

            Program program = programQueues.nextNew();
            if (program == null) {
                log.info("All programs started");
                if (activePrograms.get() == 0) {
                    log.info("All programs finished");
                    Thread.currentThread().interrupt();
                    return;
                }
                continue;
            }
            activePrograms.incrementAndGet();

            program.setStatus("RUNNING");
            program.setCpuId(cpu.getCpuId());
            new Thread(() -> {
                cpu.compute(program);
                cpuQueue.add(cpu);
                program.setStatus("TERMINATED");
                activePrograms.decrementAndGet();
            }).start();

        }
    }

}
