public class Day12Test extends AbstractMultiStepDayTest<Day12, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day12Test() {
        super(() -> new Day12(SAMPLE_FILE), 21L, 525152L);
    }


}
