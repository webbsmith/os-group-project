package os;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class LoaderTest {

    @Test
    public void run() {
        Disk disk = mock(Disk.class);
        ProcessControlBlock processControlBlock = mock(ProcessControlBlock.class);

        Loader loader = new Loader(disk, processControlBlock);

        loader.run("program-input.txt");
    }
}