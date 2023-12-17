import java.util.Optional;

public class Day17Test extends AbstractMultipleSampleDayTest<Day17, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day17Test() {
        super(Day17::new,
                new DayTestParam<>("sample.txt", Optional.of(102L), Optional.of(94L)),
                new DayTestParam<>("sample2.txt", Optional.empty(), Optional.of(71L))
        );
    }

}
