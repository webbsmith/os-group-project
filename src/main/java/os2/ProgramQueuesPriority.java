package os2;

import os.Program;

import java.util.*;

public enum ProgramQueuesPriority {
    INSTANCE;

    private final Set<Program> newQueue = new TreeSet<Program>(); // Priority
    private Iterator<Program> programIterator;
    private final Queue<Program> readyQueue = new ArrayDeque<Program>();
    private final Queue<Program> blockedQueue = new ArrayDeque<Program>();
    private final Queue<Program> suspendQueue = new ArrayDeque<Program>();

    public Program nextNew() {
        if (programIterator == null) {
            programIterator = newQueue.iterator();
        }
        if (programIterator.hasNext()) {
            return programIterator.next();
        }
        return null;
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
