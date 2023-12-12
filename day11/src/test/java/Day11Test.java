import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Day11Test extends AbstractMultiStepDayTest<Day11, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day11Test() {
        super(() -> new Day11(SAMPLE_FILE), 374L, 82000210L);
    }

    @Test
    public void testWith10() throws IOException {
        Day11 day = new Day11(SAMPLE_FILE);
        day.readFile();
        long result = day.computeResult(10);
        Assertions.assertEquals(1030L, result);
    }

    @Test
    public void testWith100() throws IOException {
        Day11 day = new Day11(SAMPLE_FILE);
        day.readFile();
        long result = day.computeResult(100);
        Assertions.assertEquals(8410L, result);
    }

}
