public class Day04Test extends AbstractMultiStepDayTest<Day04, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day04Test() {
        super(() -> new Day04(SAMPLE_FILE), 13L, 30L);
    }

}
