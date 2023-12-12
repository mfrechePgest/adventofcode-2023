import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Day12Test extends AbstractMultiStepDayTest<Day12, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day12Test() {
        super(() -> new Day12(SAMPLE_FILE), 21L, 525152L);
    }

    @Test
    public void testLine1() {
        Day12 day = new Day12(SAMPLE_FILE);
        HotSpring hotspring = day.readLine("???.### 1,1,3");
        long result = day.countArrangements(hotspring);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void unfoldTest() {
        HotSpring hotSpring = new HotSpring("???.###", Arrays.asList(1,1,3));
        hotSpring = hotSpring.unfold();
        Assertions.assertEquals("???.###????.###????.###????.###????.###", hotSpring.input());
        Assertions.assertEquals(Arrays.asList(1,1,3,1,1,3,1,1,3,1,1,3,1,1,3), hotSpring.rules());
    }

    @Test
    public void testLine1Step2() {
        Day12 day = new Day12(SAMPLE_FILE);
        HotSpring hotspring = day.readLine("???.### 1,1,3").unfold();
        long result = day.countArrangements(hotspring);
        Assertions.assertEquals(1, result);
    }

}
