public class Day10TestSample4 extends AbstractMultiStepDayTest<Day10, Long, Long> {

    public static final String SAMPLE_FILE = "sample4.txt";

    public Day10TestSample4() {
        super(() -> new Day10(SAMPLE_FILE), 70L, 8L);
    }

}
