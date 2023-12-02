public class Day01SecondTest extends AbstractMultiStepDayTest<Day01, Long, Long> {

    public static final String SAMPLE_FILE = "secondsample.txt";

    public Day01SecondTest() {
        super(() -> new Day01(SAMPLE_FILE), 198L, 281L);
    }



}
