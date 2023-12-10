public class Day10TestSample3 extends AbstractMultiStepDayTest<Day10, Long, Long> {

    public static final String SAMPLE_FILE = "sample3.txt";

    public Day10TestSample3() {
        super(() -> new Day10(SAMPLE_FILE), 23L, 4L);
    }

}
