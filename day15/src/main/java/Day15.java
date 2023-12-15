import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day15 extends AbstractMultiStepDay<Long, Long> {

    public Day15(String fileName) {
        super(fileName);
    }

    public Day15() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day15 day15 = new Day15();
        day15.fullRun();
    }

    public Long resultStep1() {
        return inputList.stream()
                .mapToLong(Day15::computeHashAlgorithm)
                .sum();
    }

    public static long computeHashAlgorithm(String s) {
        long result = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            result += c;
            result *= 17;
            result %= 256;
        }
        return result;
    }

    public Long resultStep2() {
        Map<Long, List<String>> boxes = new HashMap<>();
        for (String input : inputList) {
            String label = input.split("[=\\-]")[0];
            long boxNumber = Day15.computeHashAlgorithm(label);
            if (input.contains("=")) {
                List<String> list = new ArrayList<>();
                list.add(input);
                boxes.merge(boxNumber, list,
                        (oldList, newList) -> {
                            for (int i = 0; i < oldList.size(); i++) {
                                if (oldList.get(i).startsWith(label)) {
                                    oldList.set(i, newList.getFirst());
                                    return oldList;
                                }
                            }
                            oldList.add(newList.getFirst());
                            return oldList;
                        }
                );
            } else {
                if (boxes.containsKey(boxNumber)) {
                    boxes.get(boxNumber).removeIf(s -> s.startsWith(label));
                    if (boxes.get(boxNumber).isEmpty()) {
                        boxes.remove(boxNumber);
                    }
                }
            }
        }
        return boxes.keySet().stream()
                .flatMapToLong(box -> IntStream.range(0, boxes.get(box).size())
                        .mapToLong(idx -> (box + 1) * (idx + 1) * Long.parseLong(boxes.get(box).get(idx).split("=")[1]))
                ).sum();
    }

    List<String> inputList = new ArrayList<>();

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            if (line != null) {
                inputList = Arrays.asList(line.split(","));
            }
        }
    }


}
