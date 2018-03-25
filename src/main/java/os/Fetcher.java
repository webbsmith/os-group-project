package os;
import java.security.InvalidParameterException;
import java.util.Formatter;

//With support from the Memory module/method, this method fetches instructions or data from memory
//depending on the content of the CPU’s program counter (PC). On instruction fetch, the PC value should
//point to the next instruction to be fetched. The Fetch method therefore calls the Effective-Address
//method to translate the logical address to the corresponding absolute address, using the base-register
//value and a ‘calculated’ offset/address displacement. The Fetch, therefore, also supports the Decode
//method of the CPU.


public class Fetcher
{
    private final Memory memory;
    private final Decoder decoder;
    private final EffectiveAddress effectiveAddress;

    public Fetcher(Memory memory, Decoder decoder, EffectiveAddress effectiveAddress)
    {
        this.memory = memory;
        this.decoder = decoder;
        this.effectiveAddress = effectiveAddress;
    }
    int memory_pos = 0;
    int ram_pos = 0;

    int ram = 1024;
    int disk = 4096;
    int position_num;


    public String read(int logicalAddress)
    {
        int logicalNumber = getlogicalPosition(logicalAddress);
        int offset = getOffset(logicalAddress);

        return readFromAbsolute(getAbsolutePosition(logicalNumber), offset);
    }

    public void write(int logicalAddress, String value)
    {
        int logicalNumber = getlogicalPosition(logicalAddress);
        int offset = getOffset(logicalAddress);

        writeToAbsolute(getAbsolutePosition(logicalNumber), offset, value);
    }

    public void writeToAbsolute(int physicalPosition, int offset, String value)
    {
//        memory.writeRam(physicalPosition, offset, value);
    }

    public String readFromAbsolute(int physicalPosition, int offset)
    {
//        return memory.readRam(physicalPosition, offset);
        return null;
    }

    public int getlogicalPosition(int logicalAddress)
    {
//        return logicalAddress/memory.getDecoder();
        return -1;
    }

    public int getOffset(int logicalAddress)
    {
//        return logicalAddress%memory.getDecoder();
        return -1;
    }

    public int getAbsolutePosition(int logicalPosition)
    {
        return effectiveAddress.computeDirectAddress(logicalPosition);
    }

//    public void synchronizeAddress(fetcher job)
//    {
//        int startDiskAddress = job.getJobDiskAddress();
//        int currentDiskAddress = startDiskAddress;
//        int currentMemoryAddress = job.getJobMemoryAddress();
//
//        for (int i = 0; currentDiskAddress < startDiskAddress + AddressSize; i++)
//        {
//            int virtualPosition = job.getAllocatedVirtualPositions().get(i / memory.getDecoder());
//            int virtualPositionTimesSize = virtualPosition * memory.getDecoder();
//
//            write(job.getAllocatedVirtualPositions().get(i / memory.getDecoder()) * memory.getDecoder() + i%memory.getDecoder(), job.getAddress()[i]); //to set?
//            Disk.writeDisk(job.getAddress()[i], currentDiskAddress);
//            currentDiskAddress++;
//            currentMemoryAddress++;
//        }
//    }


//    String EffectiveAddress(String logicalAddress, String baseRegister)
//    {
//        String absoluteAddress = "";
//        //directly
//        if(baseRegister == null){absoluteAddress = baseRegister + offset;}
//        //indirectly
//        else{absoluteAddress = baseRegister + logicalAddress + offset;}
//        return absoluteAddress;
//    }

    public String[] retrieveFromDisk(int numOfInstructions, int startPC_Counter)
    {
        //effectiveAdress formater
//        return everything;
        return null;
    }
//    base Register values

    public String[] retrieveFromRam(int numofInstructions, int startPC_Counter)
    {
        String everything[] = new String[numofInstructions];
        int limit = (numofInstructions * 4);

        return everything;
    }

    public int getRamPC_Counter(){
        int i = 0;

        return i;
    }

    public int getDiskPC_Counter(){
        int i = 0;

        return i;
    }

    public String getFrommemory(int PC_Counter){
//        return memory.readmemory(PC_Counter);
        return null;
    }

    public String getFromDisk(int PC_Counter){
//        return Disk.readDisk(PC_Counter);
        return null;
    }

}
