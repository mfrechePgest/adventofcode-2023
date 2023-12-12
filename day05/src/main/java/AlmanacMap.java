import java.util.function.Function;
import java.util.stream.Stream;

public class AlmanacMap {
    String label;
    Function<Long, Long> function = Function.identity();
    Function<Range, Stream<Range>> rangeFunction = Stream::of;

    public AlmanacMap(String s) {
        label = s;
    }
}
