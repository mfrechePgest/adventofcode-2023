import java.util.function.Function;

public class AlmanacMap {
    String label;
    Function<Long, Long> function = Function.identity();

    public AlmanacMap(String s) {
        label = s;
    }
}
