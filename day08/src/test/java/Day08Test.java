public class Day08Test extends AbstractMultiStepDayTest<Day08, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day08Test() {
        super(() -> new Day08(SAMPLE_FILE), 2L, 2L);
    }

}
