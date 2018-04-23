package os2;

import os.Program;

import java.util.ArrayDeque;
import java.util.Queue;

public enum ProgramQueuesFifo {
    INSTANCE;

    private final Queue<Program> newQueue = new ArrayDeque<Program>();
    private final Queue<Program> readyQueue = new ArrayDeque<Program>();
    private final Queue<Program> blockedQueue = new ArrayDeque<Program>();
    private final Queue<Program> suspendQueue = new ArrayDeque<Program>();

    public Program nextNew() {
        return newQueue.poll();
    }

    public Program nextReady() {
        return readyQueue.poll();
    }

    public Program nextBlocked() {
        return blockedQueue.poll();
    }

    public Program nextSuspended() {
        return suspendQueue.poll();
    }

    public void addToNew(Program program) {
        newQueue.add(program);
    }

    public void addToReady(Program program) {
        readyQueue.add(program);
    }

    public void addToBlocked(Program program) {
        blockedQueue.add(program);
    }

    public void addToSuspend(Program program) {
        suspendQueue.add(program);
    }
}
