import java.util.Optional;

public record DayTestParam<STEP1, STEP2>(String filename, Optional<STEP1> expected1, Optional<STEP2> expected2) {
}
