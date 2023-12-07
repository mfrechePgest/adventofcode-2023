public class Day07Test extends AbstractMultiStepDayTest<Day07, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day07Test() {
        super(() -> new Day07(SAMPLE_FILE), 6440L, 5905L);
    }

}
