package os;

/* This class simulates the process control block.
   For any given job (Job 1, Job 2, etc), the data about that
   job's length would be stored at (Job # - 1) position in the jobLength array.
   ex. Job 6 length would be stored at jobLength[5]
   Similarly, information about the job's priority number is stored at (Job # - 1)
   ex. Job 12's priority would be stored at jobPriority[11]

   public void newJob(int jobNum, String length, String priority, String inputb, String outputb, String tempb)
   A new job's length, priority value, input buffer, output buffer, and temporary buffer
   will be stored at the (job # - 1) position in each respective array.

   public int getPriority(int x)
   A getter function to return a job's priority value for Job #x (array location jobPriority[x - 1]

   public void setPriority(int x, int priority)
   Sets the priority value for Job #x (at jobPriority [x - 1])

   Similar setter and getter methods for the other
*/
public class ProcessControlBlock{
    public int jobNumber;
    public int[] jobLength = new int[100];
    public int[] jobPriority = new int[100];
    public int[] jobInputBuffer = new int[100];
    public int[] jobOutputBuffer = new int[100];
    public int[] jobTemporaryBuffer = new int[100];

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
}