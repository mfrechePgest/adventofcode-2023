import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record MirrorMap(List<String> lines) {

    char getCharAt(int x, int y) {
        return lines.get(y).charAt(x);
    }

    public String toString() {
        return String.join("\n", lines);
    }

    public OptionalInt findVerticalSymmetry() {
        return IntStream.range(1, this.lines().get(0).length())
                .filter(x -> this.lines()
                        .stream()
                        .allMatch(l -> this.isLineSymmetrical(l, x)))
                .findFirst();
    }

    public OptionalInt findHorizontalSymmetry() {
        return IntStream.range(1, this.lines().size())
                .filter(y -> IntStream.range(0, this.lines().get(y).length())
                        .allMatch(l -> this.isColumnSymmetrical(l, y)))
                .findFirst();
    }

    private boolean isLineSymmetrical(String l, int x) {
        String s1 = new StringBuilder(l.substring(0, x)).reverse().toString();
        String s2 = l.substring(x);
        String longest = s1.length() > s2.length() ? s1 : s2;
        String shortest = longest == s1 ? s2 : s1;
        return longest.startsWith(shortest);
    }

    private boolean isColumnSymmetrical(int columnIdx, int ySymmetry) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < lines.size() ; i++ ) {
            sb.append(getCharAt(columnIdx, i));
        }
        return isLineSymmetrical(sb.toString(), ySymmetry);
    }
}
