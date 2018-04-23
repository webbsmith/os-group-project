package os2;

import os.Program;

import java.util.*;

// Used for priority scheduling and shortest-job-first scheduling
public enum ProgramQueuesTreeSet {
    INSTANCE;

    private final Set<Program> newQueue = new TreeSet<Program>();
    private Iterator<Program> newIterator;
    private final Set<Program> readyQueue = new TreeSet<Program>();
    private Iterator<Program> readyIterator;
    private final Set<Program> blockedQueue = new TreeSet<Program>();
    private Iterator<Program> blockedIterator;
    private final Set<Program> suspendQueue = new TreeSet<Program>();
    private Iterator<Program> suspendIterator;

    public Program nextNew() {
        if (newIterator == null) {
            newIterator = newQueue.iterator();
        }
        if (newIterator.hasNext()) {
            return newIterator.next();
        }
        return null;
    }

    public Program nextReady() {
        if (readyIterator == null) {
            readyIterator = readyQueue.iterator();
        }
        if (readyIterator.hasNext()) {
            return readyIterator.next();
        }
        return null;
    }

    public Program nextBlocked() {
        if (blockedIterator == null) {
            blockedIterator = blockedQueue.iterator();
        }
        if (blockedIterator.hasNext()) {
            return blockedIterator.next();
        }
        return null;
    }

    public Program nextSuspended() {
        if (suspendIterator == null) {
            suspendIterator = suspendQueue.iterator();
        }
        if (suspendIterator.hasNext()) {
            return suspendIterator.next();
        }
        return null;
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
