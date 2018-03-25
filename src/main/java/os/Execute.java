package os;

//This method is essentially a switch-loop of the CPU. One of its key functions is to increment the PC
//value on ‘successful’ execution of the current instruction. Note also that if an I/O operation is done via
//an interrupt, or due to any other preemptive instruction, the job is suspended until the DMA-Channel
//method completes the read/write operation, or the interrupt is serviced


public class Execute
{
    public String[] array;
    public int nextSlot = 0;
    int size = 0;
    int job;

    public int getExecuteSize()
    {
        return size;
    }

    public String getcompleted_Position(int index)
    {
        return array[index];
    }

    public int getJob()
    {
        return job;
    }

    public void writeToExecute(int offset, String completed_Position)
    {
        array[offset]=completed_Position;
    }

    Execute(int exe_completed, int jobNo)
    {
        array= new String[exe_completed];
        size=exe_completed;
        job=jobNo;
    }

    Execute(int exe_completed)
    {
        array= new String[exe_completed];
        size=exe_completed;
    }

    public boolean isFull()
    {
        if(nextSlot>=size)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void addToExecute(String completed_Position)
    {
        if(!isFull())
        {
            array[nextSlot]=completed_Position;
            nextSlot++;
        }
    }

}