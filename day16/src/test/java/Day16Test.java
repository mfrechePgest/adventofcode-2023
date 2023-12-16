public class Day16Test extends AbstractMultiStepDayTest<Day16, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day16Test() {
        super(() -> new Day16(SAMPLE_FILE), 46L, 51L);
    }

}
