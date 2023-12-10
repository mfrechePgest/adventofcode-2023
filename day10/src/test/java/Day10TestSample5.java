public class Day10TestSample5 extends AbstractMultiStepDayTest<Day10, Long, Long> {

    public static final String SAMPLE_FILE = "sample5.txt";

    public Day10TestSample5() {
        super(() -> new Day10(SAMPLE_FILE), 80L, 10L);
    }

}
