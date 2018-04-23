package os2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static final int CPU_COUNT = 2;

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        Driver2 driver = new Driver2();
        driver.run();
        log.info("Finished all programs in {} ms", (System.currentTimeMillis() - time));
    }
}
