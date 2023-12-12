import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        return solve(hotSpring);
    }

    private long solve(String input, List<Integer> rules, Map<HotSpring, Long> cache) {
        HotSpring hotSpring = new HotSpring(input, rules);
        if (cache.containsKey(hotSpring)) {
            return cache.get(hotSpring);
        }

        long result = 0;
        if (input.isBlank()) {
            if (rules.isEmpty()) {
                // reach the end, every rule fits
                result = 1;
            }
        } else {
            result = switch (input.charAt(0)) {
                case '.' -> solve(input.substring(1), rules, cache);
                case '?' ->
                        solve("." + input.substring(1), rules, cache) + solve("#" + input.substring(1), rules, cache);
                default -> solveDamagedSpring(input, rules, cache);
            };
        }

        cache.put(hotSpring, result);
        return result;
    }

    public long solveDamagedSpring(String input, List<Integer> rules, Map<HotSpring, Long> cache) {
        if (rules.isEmpty()) {
            return 0;
        } else {
            Integer damagedGroupRule = rules.getFirst();
            if (damagedGroupRule <= input.length() &&
                    input.chars().limit(damagedGroupRule).allMatch(c -> c == '#' || c =='?')) {
                if (input.length() == damagedGroupRule) {
                    // end of input : ok if it was the last group
                    return rules.size() == 1 ? 1 : 0;
                } else if (input.charAt(damagedGroupRule) == '#') {
                    // can't have another # after a group
                    return 0;
                } else {
                    return solve(input.substring(damagedGroupRule + 1), rules.subList(1, rules.size()), cache);
                }
            } else {
                return 0;
            }
        }
    }


    private long solve(HotSpring hotSpring) {
        return solve(hotSpring.input(), hotSpring.rules(), new HashMap<>());
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
