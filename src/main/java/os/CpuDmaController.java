package os;

public class CpuDmaController {
    // todo - maybe defer to Memory class for addresses
    // placeholder
    private int[] memory = new int[1000];
    private static final int WORD_SIZE_IN_BYTES = 4;

    public void readStart(int memoryAddressOfPhysicalData, int memoryAddressOfBuffer) {

        while (notAtEndOfBlock(memoryAddressOfPhysicalData)) {
            read(memoryAddressOfPhysicalData, memoryAddressOfBuffer);
            memoryAddressOfBuffer += WORD_SIZE_IN_BYTES;
        }
    }

    public void writeStart(int memoryAddressOfPhysicalData, int memoryAddressOfBuffer) {

        while (notAtEndOfBlock(memoryAddressOfBuffer)) {
            write(memoryAddressOfPhysicalData, memoryAddressOfBuffer);
            memoryAddressOfBuffer += WORD_SIZE_IN_BYTES;
        }
    }

    private boolean notAtEndOfBlock(int address) {
        return memory[address] != -1;
    }

    public void read(int memoryAddressOfPhysicalData, int memoryAddressOfBuffer) {
        memory[memoryAddressOfBuffer] = memory[memoryAddressOfPhysicalData];
    }

    public void write(int memoryAddressOfPhysicalData, int memoryAddressOfBuffer) {
        memory[memoryAddressOfPhysicalData] = memory[memoryAddressOfBuffer];
    }


}
