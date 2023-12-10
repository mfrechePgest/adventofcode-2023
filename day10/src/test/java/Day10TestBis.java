public class Day10TestBis extends AbstractMultiStepDayTest<Day10, Long, Long> {

    public static final String SAMPLE_FILE = "sample1bis.txt";

    public Day10TestBis() {
        super(() -> new Day10(SAMPLE_FILE), 4L, 1L);
    }

}
