package os;
/* This class simulates the process control block.
   For any given job (Job 1, Job 2, etc), the data about that
   job's length would be stored at (Job # - 1) position in the jobLength array.
   ex. Job 6 length would be stored at jobLength[5]
   Similarly, information about the job's priority number is stored at (Job # - 1)
   ex. Job 12's priority would be stored at jobPriority[11]

   Information will also be stored regarding the job's location in the simulated RAM (which is an array of strings).
   jobMemoryStart is the location of the job's very first word in memory
   jobMemoryEnd is the location of the job's very last word in memory (right before the next job starts)
   jobInputBufferLocation is the location of the start of the job's input buffer
   jobOutputBufferLocation is the location of the start of the job's output buffer
   eg. if jobMemoryStart[4] = 136, it means that job 5 (index 4 in the jobMemoryStart array)
       begins at index 136 in the memory array.
   eg. if you need to know where job 8's input buffer begins in the memory, you can call
       public int getInputBufferLocation(8);

   public void newJob(int jobNum, String length, String priority, String inputb, String outputb, String tempb)
   A new job's length, priority value, input buffer, output buffer, and temporary buffer
   will be stored at the (job # - 1) position in each respective array.

   public int getPriority(int x)
   A getter function to return a job's priority value for Job #x (array location jobPriority[x - 1]

   public void setPriority(int x, int priority)
   Sets the priority value for Job #x (at jobPriority [x - 1])

   Similar setter and getter methods for the others.
*/
public class ProcessControlBlock{
    public int jobNumber;
    public int[] jobLength = new int[100];
    public int[] jobPriority = new int[100];
    public int[] jobInputBuffer = new int[100];
    public int[] jobOutputBuffer = new int[100];
    public int[] jobTemporaryBuffer = new int[100];
    public int[] jobMemoryStart = new int[100];
    public int[] jobMemoryEnd = new int[100];
    public int[] jobInputBufferLocation = new int[100];
    public int[] jobOutputBufferLocation = new int[100];

    public void newJob(String jobNum, String length, String priority, String inputb, String outputb, String tempb){
        jobNumber = Integer.parseInt(jobNum, 16) - 1;
        jobLength[jobNumber] = Integer.parseInt(length, 16);
        jobPriority[jobNumber] = Integer.parseInt(priority, 16);
        jobInputBuffer[jobNumber] = Integer.parseInt(inputb, 16);
        jobOutputBuffer[jobNumber] = Integer.parseInt(outputb, 16);
        jobTemporaryBuffer[jobNumber] = Integer.parseInt(tempb, 16);
    }

    public int getPriority(int x){
        return jobPriority[x - 1];
    }

    public void setPriority(int x, int temp){
        jobPriority[x - 1] = temp;
    }

    public int getLength(int x){
        return jobLength[x - 1];
    }

    public void setLength(int x, int temp){
        jobLength[x - 1] = temp;
    }

    public int getInputBuffer(int x){
        return jobInputBuffer[x - 1];
    }

    public void setInputBuffer(int x, int temp){
        jobInputBuffer[x - 1] = temp;
    }

    public int getOutputBuffer(int x){
        return jobOutputBuffer[x - 1];
    }

    public void setOutputBuffer(int x, int temp){
        jobOutputBuffer[x - 1] = temp;
    }

    public int getTempBuffer(int x){
        return jobTemporaryBuffer[x - 1];
    }

    public void setTempBuffer(int x, int temp){
        jobTemporaryBuffer[x - 1] = temp;
    }

    public int getJobStart(int x){
        return jobMemoryStart[x - 1];
    }

    public void setJobStart(int x, int temp){
        jobMemoryStart[x - 1] = temp;
    }

    public int getJobEnd(int x){
        return jobMemoryEnd[x - 1];
    }

    public void setJobEnd(int x, int temp){
        jobMemoryEnd[x - 1] = temp;
    }

    public int getInputBufferLocation(int x){
        return jobInputBufferLocation[x - 1];
    }

    public void setInputBufferLocation(int x, int temp){
        jobInputBufferLocation[x - 1] = temp;
    }

    public int getOutputBufferLocation(int x){
        return jobOutputBufferLocation[x - 1];
    }

    public void setOutputBufferLocation(int x, int temp){
        jobOutputBufferLocation[x - 1] = temp;
    }
}