package os;

public class NewFetcher {
    private final EffectiveAddress effectiveAddress;
    private final Memory memory;

    public NewFetcher(EffectiveAddress effectiveAddress, Memory memory) {
        this.effectiveAddress = effectiveAddress;
        this.memory = memory;
    }

    public String fetchInstruction(int programCounter) {
        int memoryAddress = effectiveAddress.computeDirectAddress(programCounter);
        return memory.getWord(memoryAddress);
    }
}
