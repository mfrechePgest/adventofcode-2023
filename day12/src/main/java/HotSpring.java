import java.util.ArrayList;
import java.util.List;

public record HotSpring(String input, List<Integer> rules) {
    public HotSpring unfold() {
        StringBuilder newInput = new StringBuilder();
        List<Integer> newRules = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i++ ) {
            if ( !newInput.isEmpty() ) {
                newInput.append("?");
            }
            newInput.append(input);
            newRules.addAll(rules);
        }
        return new HotSpring(newInput.toString(), newRules);
    }
}
