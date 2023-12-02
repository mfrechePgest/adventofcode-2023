public class Day02Test extends AbstractMultiStepDayTest<Day02, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day02Test() {
        super(() -> new Day02(SAMPLE_FILE), 8L, 2286L);
    }

}
