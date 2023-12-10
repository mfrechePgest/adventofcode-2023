import java.util.Optional;

public class Day10TestSample3 extends AbstractMultipleSampleDayTest<Day10, Long, Long> {

    public static final String SAMPLE_FILE = "sample3.txt";

    public Day10TestSample3() {
        super(Day10::new,
                new DayTestParam<>("sample3.txt", Optional.of(23L), Optional.of(4L)),
                new DayTestParam<>("sample3bis.txt", Optional.of(22L), Optional.of(4L))
        );
    }

}
