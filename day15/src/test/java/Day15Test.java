import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day15Test extends AbstractMultiStepDayTest<Day15, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day15Test() {
        super(() -> new Day15(SAMPLE_FILE), 1320L, 145L);
    }

    @Test
    public void checkHashAlgorithm() {
        long result = Day15.computeHashAlgorithm("HASH");
        Assertions.assertEquals(52, result);
    }

}
