import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Day08Test3 {

    public static final String SAMPLE_FILE = "sample3.txt";

    @Test
    public void testSample3() throws IOException {
        Day08 day08 = new Day08(SAMPLE_FILE);
        day08.readFile();
        Assertions.assertEquals(6L, day08.resultStep2());
    }

}
