public class Day05Test extends AbstractMultiStepDayTest<Day05, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day05Test() {
        super(() -> new Day05(SAMPLE_FILE), 35L, 46L);
    }

}
