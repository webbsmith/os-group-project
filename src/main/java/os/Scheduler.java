package os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scheduler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    private final Disk disk;
    private int jobNumber;
    private final Memory memory;
    private final ProcessControlBlock PCB;

    public Scheduler(Disk disk, int JobNumber, Memory memory, ProcessControlBlock PCB){
        this.disk = disk;
        this.jobNumber = JobNumber;
        this.memory = memory;
        this.PCB = PCB;
    }
    /* This scheduler function recieves a disk object, a job number to schedule, a memory object, and a PCB object.
       It loads the job onto the memory from the disk (overwriting the earliest programs if memory is full [this is handled automatically by the Memory class]),
       and notes in the PCB where the job starts and ends, and where the input buffer and output buffer start.
    */
    public void run() {
        log.info("Running scheduler");
        int diskLocation = 0;
        int x = 1;

        //This while loop calculates the index where a job starts on Disk by summing the lengths
        //of the previous jobs and their buffers. (if job 1 is length 5, and has a value of 5 for each buffer,
        //it will take up indexes 0 - 19, and job 2 will start on index 20 (which is 5 + 5 + 5 + 5), etc.)
        while(x < jobNumber){
            log.debug("index x is less than job number {}", jobNumber);
            diskLocation += PCB.getLength(x);
            diskLocation += PCB.getInputBuffer(x);
            diskLocation += PCB.getOutputBuffer(x);
            diskLocation += PCB.getTempBuffer(x);
            x++;
        }

        //Puts the job's starting index in memory into the PCB
        log.info("Writing job {}'s starting index {} into PCB", jobNumber, memory.getCounter());
        PCB.setJobStart(jobNumber, memory.getCounter());

        //Writes the job's commands to memory
        log.info("Writing job {} commands from disk location {}  to memory", jobNumber, diskLocation);
        for(int i = 0; i < PCB.getLength(jobNumber); i++){
            memory.newWord(disk.getWord(diskLocation));
            diskLocation++;
        }

        //Puts the job's input buffer starting index in memory into the PCB
        log.info("Putting the input buffer and starting index into the PCB");
        PCB.setInputBufferLocation(jobNumber, memory.getCounter());

        //Writes the job's input buffer to memory
        log.info("Writing the input buffer from disk location {} to memory", diskLocation);
        for(int i = 0; i < PCB.getInputBuffer(jobNumber); i++){
            memory.newWord(disk.getWord(diskLocation));
            diskLocation++;
        }

        //Puts the job's output buffer starting index in memory into the PCB
        log.info("Setting the output buffer location into the PCB");
        PCB.setOutputBufferLocation(jobNumber, memory.getCounter());

        //Writes the job's output buffer to memory
        log.info("Writing the output buffer to memory");
        for(int i = 0; i < PCB.getOutputBuffer(jobNumber); i++){
            memory.newWord(disk.getWord(diskLocation));
            diskLocation++;
        }

        //Writes the job's temp buffer to memory (temp buffer starting index is not needed, according to project specifications)
        log.info("Writing the temp buffer to memory");
        for(int i = 0; i < PCB.getTempBuffer(jobNumber); i++){
            memory.newWord(disk.getWord(diskLocation));
            diskLocation++;
        }

        //Puts the location of the job's last index in memory into the PCB.
        log.info("Storing location of the job's last index {} into the PCB", memory.getCounter() - 1);
        PCB.setJobEnd(jobNumber, (memory.getCounter() - 1));
    }
}
