public class Day01Test extends AbstractMultiStepDayTest<Day01, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day01Test() {
        super(() -> new Day01(SAMPLE_FILE), 142L, 142L);
    }



}
