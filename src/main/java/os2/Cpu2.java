package os2;

import lombok.extern.slf4j.Slf4j;
import os.Disk;
import os.Program;
import os.ProgramQueues;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Slf4j
public class Cpu2 {
    private final ProgramQueues programQueues = new ProgramQueues();
    private final Disk disk = new Disk();

    private void load(String fileName) {
        log.info("Reading input file {}", fileName);
        File file = new File(getFileFromResourcesFolder(fileName));
        try (Scanner scan = getScanner(file)) {
            readEachLine(scan);
        }
    }

    private void readEachLine(Scanner scan) {
        Program currentProgram = new Program();
        while (scan.hasNextLine()) {
            String line = scan.nextLine().toUpperCase();
            if (line.contains("END")) {
                schedule(currentProgram);
                currentProgram = new Program();
                return;
            }
            if (line.contains("JOB")) {
                String[] lineSplit = line.split(" ");
                currentProgram.setId(lineSplit[2]);
                currentProgram.setInstructionCount(lineSplit[2]);
                currentProgram.setPriorityNumber(lineSplit[3]);
                currentProgram.setProgramCounter(disk.getNextAddress());
                return;
            }
            if (line.contains("DATA")) {
                String[] lineSplit = line.split(" ");
                currentProgram.setInputBufferSize(lineSplit[2]);
                currentProgram.setOutputBufferSize(lineSplit[3]);
                currentProgram.setTemporaryBufferSize(lineSplit[4]);
                return;
            }
            if (!line.startsWith("0x")) {
                throw new IllegalArgumentException("Invalid input: " + line);
            }
            if (currentProgram.)
                log.trace("{} >> sending to Disk", line);
                disk.newWord(line);
            }
        }

    private void schedule(Program currentProgram) {
        programQueues.addToNew(currentProgram);
    }

    private Scanner getScanner(File file) {
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to read file");
        }
        return scan;
    }

    private String getFileFromResourcesFolder(String fileName) {
        return getClass().getClassLoader().getResource(fileName).getFile();
    }
}
