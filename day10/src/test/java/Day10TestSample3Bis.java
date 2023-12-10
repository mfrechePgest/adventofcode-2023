public class Day10TestSample3Bis extends AbstractMultiStepDayTest<Day10, Long, Long> {

    public static final String SAMPLE_FILE = "sample3bis.txt";

    public Day10TestSample3Bis() {
        super(() -> new Day10(SAMPLE_FILE), 22L, 4L);
    }

}
