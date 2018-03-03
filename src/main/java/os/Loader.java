package os;
/* public void Loader(Disk disk)
   Precondition: The Disk object should be created first in the main method,
   and then passed to this function.
   Postcondition: The 'Program-File.txt' file will be parsed and the commands
   will be placed into disk memory. The control cards will be sent to the PCB object.
*/
import java.io.*;
import java.util.Scanner;

public class Loader {
    public void run(Disk disk) {
        String Line = new String;
        String controlCard = new String;
        String[] CCInfo = new String[5];
        String[] DataCard = new String[5];

        try {
            File file = new File("Project-File.txt");
            Scanner scan = new Scanner(file);

            //Scan each line. If the line is a control card, send info to PCB,
            //else load line into Disk object.
            while (scan.hasNextLine()) {
                Line = scan.NextLine();

                if (Line.startsWith("// JOB")) {
                    //Line is a control card, info will be sent to the PCB along with the data card
                    controlCard = Line;
                } else if (Line.startsWith("// DATA")) {
                    //Line is a data card, info is sent to the PCB along with control card info
                    CCInfo = controlCard.split(" ", 5);
                    DataCard = Line.split(" ", 5);
                    PCB.newJob(CCInfo[2], CCInfo[3], CCInfo[4], DataCard[2], DataCard[3], DataCard[4]);
                } else {
                    //Line is not a control card, must be sent to disk object
                    disk.newWord(Line);
                }
            }
            scan.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}