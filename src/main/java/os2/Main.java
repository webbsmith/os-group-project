package os2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static final int CPU_COUNT = 1;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Driver2 driver = new Driver2();
        driver.run();
        long timeToComplete = System.currentTimeMillis() - startTime;
        log.info("Finished all programs in {} ms", timeToComplete);
    }
}
