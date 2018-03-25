package os;
/* public void Loader(Disk disk)
   Precondition: The Disk object should be created first in the main method,
   and then passed to this function.
   Postcondition: The 'Program-File.txt' file will be parsed and the commands
   will be placed into disk memory. The control cards will be sent to the PCB object.
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

public class Loader {

    private static final Logger log = LoggerFactory.getLogger(Loader.class);

    public Loader(Disk disk, ProcessControlBlock processControlBlock) {
        this.disk = disk;
        this.processControlBlock = processControlBlock;
    }

    private final Disk disk;
    private final ProcessControlBlock processControlBlock;

    public void run(String fileName) {
        log.info("Reading input file {}", fileName);

        String line;
        String controlCard = "";
        String[] CCInfo;
        String[] DataCard;

        try {
            File file = new File(getFileFromResourcesFolder(fileName));
            Scanner scan = new Scanner(file);

            //Scan each line. If the line is a control card, send info to PCB,
            //else load line into Disk object.
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                if (line.startsWith("//")) {
                    String lineType = line.split(" ")[1];
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
                        log.debug("{} >> no action taken", line);
                    }

                } else {
                    //Line is not a control card, must be sent to disk object

                    log.debug("{} >> sending to Disk", line);
                    disk.newWord(line);
                }
            }
            scan.close();
        } catch (Exception ex) {
            log.error("Issue reading input file", ex);
        }
    }

    private String getFileFromResourcesFolder(String fileName) {
        return getClass().getClassLoader().getResource(fileName).getFile();
    }
}