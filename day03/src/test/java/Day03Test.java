public class Day03Test extends AbstractMultiStepDayTest<Day03, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day03Test() {
        super(() -> new Day03(SAMPLE_FILE), 4361L, 467835L);
    }

}
