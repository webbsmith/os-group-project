package os;
import java.security.InvalidParameterException;
import java.util.Formatter;

//With support from the Memory module/method, this method fetches instructions or data from RAM
//depending on the content of the CPU’s program counter (PC). On instruction fetch, the PC value should
//point to the next instruction to be fetched. The Fetch method therefore calls the Effective-Address
//method to translate the logical address to the corresponding absolute address, using the base-register
//value and a ‘calculated’ offset/address displacement. The Fetch, therefore, also supports the Decode
//method of the CPU.


public class Fetcher
{
    private final Cpu cpu;
    private final Memory memory;
    private final Decoder decoder;

    public Fetcher(Cpu cpu, Memory memory, Decoder decoder)
    {
        this.cpu = cpu;
        this.memory = memory;
        this.decoder = decoder;
    }
    static int memory_pos = 0;
    static int ram_pos = 0;

    int ram = 1024;
    int disk = 4096;
    int position_num;


    public static String read(int logicalAddress)
    {
        int logicalNumber = getlogicalPosition(logicalAddress);
        int offset = getOffset(logicalAddress);

        return readFromAbsolute(getAbsolutePosition(logicalNumber), offset);
    }

    public static void write(int logicalAddress, String value)
    {
        int logicalNumber = getlogicalPosition(logicalAddress);
        int offset = getOffset(logicalAddress);

        writeToAbsolute(getAbsolutePosition(logicalNumber), offset, value);
    }

    public static void writeToAbsolute(int physicalPosition, int offset, String value)
    {
        RAM.writeRam(physicalPosition, offset, value);
    }

    public static String readFromAbsolute(int physicalPosition, int offset)
    {
        return RAM.readRam(physicalPosition, offset);
    }

    public static int getlogicalPosition(int logicalAddress)
    {
        return logicalAddress/RAM.getDecoder();
    }

    public static int getOffset(int logicalAddress)
    {
        return logicalAddress%RAM.getDecoder();
    }

    public static int getAbsolutePosition(int logicalPosition)
    {
        return RAM.getAbsolutePosition(logicalPosition);
    }

    public static void synchronizeAddress(fetcher job)
    {
        int startDiskAddress = job.getJobDiskAddress();
        int currentDiskAddress = startDiskAddress;
        int currentMemoryAddress = job.getJobMemoryAddress();

        for (int i = 0; currentDiskAddress < startDiskAddress + AddressSize; i++)
        {
            int virtualPosition = job.getAllocatedVirtualPositions().get(i / RAM.getDecoder());
            int virtualPositionTimesSize = virtualPosition * RAM.getDecoder();

            write(job.getAllocatedVirtualPositions().get(i / RAM.getDecoder()) * RAM.getDecoder() + i%RAM.getDecoder(), job.getAddress()[i]); //to set?
            Disk.writeDisk(job.getAddress()[i], currentDiskAddress);
            currentDiskAddress++;
            currentMemoryAddress++;
        }
    }


    String EffectiveAddress(String logicalAddress, String baseRegister)
    {
        String absoluteAddress = "";
        //directly
        if(baseRegister == null){absoluteAddress = baseRegister + offset;}
        //indirectly
        else{absoluteAddress = baseRegister + logicalAddress + offset;}
        return absoluteAddress;
    }

    public static String[] retrieveFromDisk(int numOfInstructions, int startPC_Counter)
    {
        //effectiveAdress formater
        return everything;
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

    public static String getFromRAM(int PC_Counter){
        return RAM.readRAM(PC_Counter);
    }

    public static String getFromDisk(int PC_Counter){
        return Disk.readDisk(PC_Counter);
    }
}
