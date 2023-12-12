public class Day08Test2 extends AbstractMultiStepDayTest<Day08, Long, Long> {

    public static final String SAMPLE_FILE = "sample2.txt";

    public Day08Test2() {
        super(() -> new Day08(SAMPLE_FILE), 6L, 6L);
    }

}
