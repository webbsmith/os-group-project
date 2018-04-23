package os2;

import lombok.extern.slf4j.Slf4j;
import os.Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Slf4j
public class Loader2 {
    private final Scheduler2 scheduler2 = Scheduler2.INSTANCE;
    private final Disk2 disk = Disk2.INSTANCE;

    public void load(String fileName) {
        log.info("Reading input file {}", fileName);
        File file = new File(getFileFromResourcesFolder(fileName));
        try (Scanner scan = getScanner(file)) {
            readEachLine(scan);
        }
    }

    private void readEachLine(Scanner scan) {
        Program currentProgram = new Program();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.startsWith("//")) {
                line = line.toUpperCase();
            }
            if (line.contains("END")) {
                schedule(currentProgram);
                currentProgram = new Program();
                continue;
            }
            if (line.contains("JOB")) {
                setJobAttributes(currentProgram, line);
                continue;
            }
            if (line.contains("DATA")) {
                setDataAttributes(currentProgram, line);
                continue;
            }
            if (!line.startsWith("0x")) {
                throw new IllegalArgumentException("Invalid input: " + line);
            }
            log.trace("{} >> sending to Disk", line);
            disk.newWord(line);
        }
    }

    private void setDataAttributes(Program currentProgram, String line) {
        String[] lineSplit = line.split(" ");
        currentProgram.setInputBufferSize(lineSplit[2]);
        currentProgram.setOutputBufferSize(lineSplit[3]);
        currentProgram.setTemporaryBufferSize(lineSplit[4]);
        currentProgram.setInputBufferCounter(disk.getNextAddress());
    }

    private void setJobAttributes(Program currentProgram, String line) {
        String[] lineSplit = line.split(" ");
        currentProgram.setId(lineSplit[2]);
        currentProgram.setInstructionCount(lineSplit[3]);
        currentProgram.setPriorityNumber(lineSplit[4]);
        currentProgram.setProgramCounter(disk.getNextAddress());
    }

    private void schedule(Program currentProgram) {
        log.info("sending program to scheduler: {}", currentProgram);
        scheduler2.schedule(currentProgram);
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
