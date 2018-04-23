package os2;

import lombok.extern.slf4j.Slf4j;
import os.Program;
import os.ProgramQueues;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Slf4j
public class Cpu2 {
    private final ProgramQueues programQueues = new ProgramQueues();

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
                addProgramToMemory(currentProgram);
                currentProgram = new Program();
                return;
            }
            if (line.contains("JOB")) {
                String[] lineSplit = line.split(" ");
                currentProgram.setId(lineSplit[2]);
                currentProgram.setInstructionCount(lineSplit[2]);
                currentProgram.setPriorityNumber(lineSplit[3]);
                return;
            }
            if (line.contains("DATA")) {
                String[] lineSplit = line.split(" ");
                currentProgram.setInputBufferSize(lineSplit[2]);
                currentProgram.setOutputBufferSize(lineSplit[2]);
                currentProgram.setTemporaryBufferSize(lineSplit[3]);
            }


                String lineType = lineSplit[1];
                if ("JOB".equalsIgnoreCase(lineType)) {
                    //Line is a control card, info will be sent to the PCB along with the data card
                    log.debug("{} >> setting control card", line);
                    controlCard = line;
                } else if ("DATA".equalsIgnoreCase(lineType)) {
                    //Line is a data card, info is sent to the PCB along with control card info
                    log.debug("{} >> sending to PCB", line);
                    CCInfo = controlCard.split(" ", 5);
                    DataCard = line.split(" ", 5);
                    processControlBlock.newJob(CCInfo[2], CCInfo[3], CCInfo[4], DataCard[2], DataCard[3], DataCard[4]);
                } else {
                    log.trace("{} >> no action taken", line);
                }

            } else {
                //Line is not a control card, must be sent to disk object

                log.trace("{} >> sending to Disk", line);
                disk.newWord(line);
            }
        }
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
