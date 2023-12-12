import java.util.Optional;

public class Day10Test extends AbstractMultipleSampleDayTest<Day10, Long, Long> {

    public Day10Test() {
        super(Day10::new,
                new DayTestParam<>("sample.txt", Optional.of(4L), Optional.of(1L)),
                new DayTestParam<>("sample1bis.txt", Optional.of(4L), Optional.of(1L)),
                new DayTestParam<>("sample2.txt", Optional.of(8L), Optional.of(1L)),
                new DayTestParam<>("sample3.txt", Optional.of(23L), Optional.of(4L)),
                new DayTestParam<>("sample3bis.txt", Optional.of(22L), Optional.of(4L)),
                new DayTestParam<>("sample4.txt", Optional.of(70L), Optional.of(8L)),
                new DayTestParam<>("sample5.txt", Optional.of(80L), Optional.of(10L))
        );
    }

}
