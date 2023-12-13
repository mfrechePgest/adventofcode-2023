import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public record MirrorMap(List<String> lines) {

    public MirrorMap(MirrorMap copy) {
        this(new ArrayList<>(copy.lines));
    }

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
                        .allMatch(l -> this.isLineSymmetrical(l, x, false)))
                .findFirst();
    }

    public OptionalInt findVerticalSmudgeNewSymmetry() {
        return IntStream.range(1, this.lines().get(0).length())
                .filter(x -> this.lines()
                        .stream()
                        .filter(l -> !this.isLineSymmetrical(l, x, false))
                        .count() == 1
                ) // we got symmetry vertical lines where only a single line is wrong
                .filter(x -> this.lines()
                        .stream()
                        .anyMatch(l -> this.isLineSymmetrical(l, x, true)))
                .findFirst();
    }


    public OptionalInt findHorizontalSymmetry() {
        return IntStream.range(1, this.lines().size())
                .filter(y -> IntStream.range(0, this.lines().get(y).length())
                        .allMatch(l -> this.isColumnSymmetrical(l, y, false)))
                .findFirst();
    }

    public OptionalInt findHorizontalSmudgeNewSymmetry() {
        return IntStream.range(1, this.lines().size())
                .filter(y -> IntStream.range(0, this.lines().get(y).length())
                        .filter(l -> !this.isColumnSymmetrical(l, y, false))
                        .count() == 1
                ) // we got symmetry vertical lines where only a single line is wrong
                .filter(y -> IntStream.range(0, this.lines().get(y).length())
                        .anyMatch(l -> this.isColumnSymmetrical(l, y, true)))
                .findFirst();
    }

    private boolean isLineSymmetrical(String l, int x, boolean permutations) {
        String s1 = new StringBuilder(l.substring(0, x)).reverse().toString();
        String s2 = l.substring(x);
        String longest = s1.length() > s2.length() ? s1 : s2;
        String shortest = longest == s1 ? s2 : s1;
        if ( permutations ) {
            for (int i = 0 ; i < shortest.length() ; i++ ) {
                String replaced = swapCharAtIndex(shortest, i);
                if ( longest.startsWith(replaced))  {
                    return true;
                }
                replaced = swapCharAtIndex(longest, i);
                if (replaced.startsWith(shortest)) {
                    return true;
                }
            }
            return false;
        } else {
            return longest.startsWith(shortest);
        }
    }



    private String swapCharAtIndex(String s, int idx) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < s.length() ; i++ ) {
            if (i == idx) {
                sb.append(s.charAt(i) == '#' ? '.' : '#');
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    private boolean isColumnSymmetrical(int columnIdx, int ySymmetry, boolean permutations) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < lines.size() ; i++ ) {
            sb.append(getCharAt(columnIdx, i));
        }
        return isLineSymmetrical(sb.toString(), ySymmetry, permutations);
    }

}
