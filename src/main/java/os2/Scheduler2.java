package os2;

import lombok.extern.slf4j.Slf4j;
import os.Decoder;
import os.Memory;
import os.Program;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public enum Scheduler2 {
    INSTANCE;

    private final Memory memory = new Memory();

//    private final ProgramQueuesFifo programQueues = ProgramQueuesFifo.INSTANCE; // Swap for ProgramQueuesFifo for FIFO scheduling
    private final ProgramQueuesTreeSet programQueues = ProgramQueuesTreeSet.INSTANCE; // Swap for ProgramQueuesFifo for FIFO scheduling
    private final Deque<Cpu2> cpuQueue = new ConcurrentLinkedDeque<>();

    private volatile boolean stillWorking = true;
    private volatile boolean allProgramsStarted = false;

    private volatile AtomicInteger activePrograms = new AtomicInteger(0);
    {
        for (int i = 0; i < Main.CPU_COUNT; i++)
            cpuQueue.add(new Cpu2(new Decoder(), i));
    }

    public void schedule(Program currentProgram) {
        programQueues.addToNew(currentProgram);
    }

    public boolean dispatch() {
        if (Thread.currentThread().isInterrupted()) return false;
        if (activePrograms.get() == 0 && allProgramsStarted) return false;
        while (!cpuQueue.isEmpty() && !allProgramsStarted) {
            Cpu2 cpu = cpuQueue.poll();

            if (cpu == null || cpu.isActive()) {
                continue;
            }

            Program program = programQueues.nextNew();
            if (program == null) {
                log.info("All programs started");
                allProgramsStarted = true;
                continue;
            }
            activePrograms.incrementAndGet();

            program.setStatus("RUNNING");
            program.setCpuId(cpu.getCpuId());
            new Thread(() -> {
                try {
                    cpu.compute(program);
                } finally {
                    cpuQueue.add(cpu);
                    program.setStatus("TERMINATED");
                    log.info("Program stats: {}", program.statsCsv());
                    activePrograms.decrementAndGet();
                }
            }).start();

        }
        return true;
    }

}
