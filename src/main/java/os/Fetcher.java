package os;

public class Fetcher {
    private final Cpu cpu;
    private final Memory memory;
    private final Decoder decoder;

    public Fetcher(Cpu cpu, Memory memory, Decoder decoder) {
        this.cpu = cpu;
        this.memory = memory;
        this.decoder = decoder;
    }

    public void run() {

    }
}
