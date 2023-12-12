import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Day12SingleLigneTest {

    public static final String SAMPLE_FILE = "sample.txt";

    @Test
    public void testLine1Step1() {
        singleLineTest("???.### 1,1,3", 1, false);
    }

    @Test
    public void testLine1Step2() {
        singleLineTest("???.### 1,1,3", 1, true);
    }

    @Test
    public void testLine2Step1() {
        singleLineTest(".??..??...?##. 1,1,3", 4, false);
    }

    @Test
    public void testLine2Step2() {
        singleLineTest(".??..??...?##. 1,1,3", 16384, true);
    }

    @Test
    public void testLine3Step1() {
        singleLineTest("?#?#?#?#?#?#?#? 1,3,1,6", 1, false);
    }

    @Test
    public void testLine3Step2() {
        singleLineTest("?#?#?#?#?#?#?#? 1,3,1,6", 1, true);
    }

    @Test
    public void testLine4Step1() {
        singleLineTest("????.#...#... 4,1,1", 1, false);
    }

    @Test
    public void testLine4Step2() {
        singleLineTest("????.#...#... 4,1,1", 16, true);
    }

    @Test
    public void testLine5Step1() {
        singleLineTest("????.######..#####. 1,6,5", 4, false);
    }

    @Test
    public void testLine5Step2() {
        singleLineTest("????.######..#####. 1,6,5", 2500, true);
    }

    @Test
    public void testLine6Step1() {
        singleLineTest("?###???????? 3,2,1", 10, false);
    }

    @Test
    public void testLine6Step2() {
        singleLineTest("?###???????? 3,2,1", 506250, true);
    }

    @Test
    public void unfoldTest() {
        HotSpring hotSpring = new HotSpring("???.###", Arrays.asList(1,1,3));
        hotSpring = hotSpring.unfold();
        Assertions.assertEquals("???.###????.###????.###????.###????.###", hotSpring.input());
        Assertions.assertEquals(Arrays.asList(1,1,3,1,1,3,1,1,3,1,1,3,1,1,3), hotSpring.rules());
    }

    private static void singleLineTest(String line, long expected, boolean unfold) {
        Day12 day = new Day12(SAMPLE_FILE);
        HotSpring hotspring = day.readLine(line);
        if( unfold ) {
            hotspring = hotspring.unfold();
        }
        long result = day.countArrangements(hotspring);
        Assertions.assertEquals(expected, result);
    }

}
