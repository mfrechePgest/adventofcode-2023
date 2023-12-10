public class Day10TestSample2 extends AbstractMultiStepDayTest<Day10, Long, Long> {

    public static final String SAMPLE_FILE = "sample2.txt";

    public Day10TestSample2() {
        super(() -> new Day10(SAMPLE_FILE), 8L, 1L);
    }

}
