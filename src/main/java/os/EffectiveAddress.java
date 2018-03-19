package os;

public class EffectiveAddress {

    private static final int DISPLACEMENT = 16;

    private final int baseAddress;

    public EffectiveAddress(int baseAddress) {
        this.baseAddress = baseAddress;
    }

    public int computeDirectAddress(int logicalAddress) {
        return baseAddress + logicalAddress + DISPLACEMENT;
    }

    public int computeInderectAddress(int logicalAddress, int indexRegister) {
        return computeDirectAddress(logicalAddress) + indexRegister;
    }
}
