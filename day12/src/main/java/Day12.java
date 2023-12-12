import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day12 extends AbstractMultiStepDay<Long, Long> {

    public Day12(String fileName) {
        super(fileName);
    }

    public Day12() {
        super("input.txt");
    }

    List<HotSpring> hotSprings = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Day12 day12 = new Day12();
        day12.fullRun();
    }

    public Long resultStep1() {
        return hotSprings.stream()
                .parallel()
                .mapToLong(this::countArrangements)
                .sum();
    }

    public Long resultStep2() {
        return hotSprings.stream()
                .parallel()
                .map(HotSpring::unfold)
                .mapToLong(this::countArrangements)
                .sum();
    }


    long countArrangements(HotSpring hotSpring) {
        List<Integer> jokerIndices = IntStream.range(0, hotSpring.input().length())
                .filter(i -> hotSpring.input().charAt(i) == '?')
                .boxed()
                .toList();
        return LongStream.range(0, (long) Math.pow(2, jokerIndices.size()))
                .parallel()
                .mapToObj(l -> new long[]{l})
                .map(BitSet::valueOf)
                .map(bs -> {
                    StringBuilder inner = new StringBuilder(hotSpring.input());
                    bs.stream().forEach(i -> inner.setCharAt(jokerIndices.get(i) ,'#'));
                    return inner;
                })
                .map(StringBuilder::toString)
                .filter(hs -> isValid(hs, hotSpring.rules()))
                .count();
    }

    private boolean isValid(String hs, List<Integer> rules) {
        String stringRepresentation =
                hs.replaceAll("\\?", ".") // at this point all ? become .
                        .replaceAll("\\.+", ".");
        List<String> split = Arrays.stream(stringRepresentation.split("\\."))
                .filter(s -> !s.isEmpty())
                .toList();
        if (split.size() == rules.size()) {
            for (int i = 0 ; i < split.size() ; i++ ) {
                if (split.get(i).length() != rules.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                hotSprings.add(readLine(line));
                line = br.readLine();
            }
        }
    }

    HotSpring readLine(String line) {
        String[] split = line.split(" ");
        String s = split[0];
        List<Integer> rules = Arrays.stream(split[1].split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return new HotSpring(s, rules);
    }


}
