import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day08 extends AbstractMultiStepDay<Long, Long> {

    public Day08(String fileName) {
        super(fileName);
    }

    public Day08() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day08 day08 = new Day08();
        day08.fullRun();
    }

    public Long resultStep1() {
        return computeResult(firstNode, Collections.singleton(lastNode));
    }

    private long computeResult(Node start, Set<Node> end) {
        int idx = 0;
        long stepCount = 0;
        Node currentNode = start;
        do {
            currentNode = switch (directions.get(idx)) {
                case LEFT -> currentNode.getLeftNode(nodeMap);
                case RIGHT -> currentNode.getRightNode(nodeMap);
            };
            idx++;
            if (idx == directions.size()) {
                idx = 0;
            }
            stepCount++;
        } while (!end.contains(currentNode));
        return stepCount;
    }

    public Long resultStep2() {
        int idx = 0;
        long stepCount = 0;
        return startingNodes.stream()
                .mapToLong(i -> computeResult(i, endingNodes))
                .reduce(Day08::lcm)
                .orElse(-1);
    }



    List<Direction> directions = new ArrayList<>();
    Map<String, Node> nodeMap = new HashMap<>();
    Node firstNode = null;
    Node lastNode = null;

    List<Node> startingNodes = new ArrayList<>();
    Set<Node> endingNodes = new HashSet<>();


    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            directions = line.chars().mapToObj(c -> c == 'L' ? Direction.LEFT : Direction.RIGHT).toList();
            line = br.readLine();
            line = br.readLine();
            while (line != null) {
                String[] split = line.split(" = ");
                String label = split[0];
                split = split[1].split(", |\\(|\\)");
                String left = split[1];
                String right = split[2];
                Node node = new Node(label, left, right);
                nodeMap.put(label, node);
                if (label.equals("AAA")) {
                    firstNode = node;
                } else if (label.equals("ZZZ")) {
                    lastNode = node;
                }
                if (label.endsWith("A")) {
                    startingNodes.add(node);
                } else if (label.endsWith("Z")) {
                    endingNodes.add(node);
                }
                line = br.readLine();
            }
        }
    }

    /**
     * shouldn't be working out of the box...<br/>
     * works because :<br/>
     *   - there actually ARE cycles<br/>
     *   - input **A is actually the perfect beginning of a cycle, each time<br/>
     * @see <a href="https://www.reddit.com/r/adventofcode/comments/18e6vdf/2023_day_8_part_2_an_explanation_for_why_the/?utm_source=share&utm_medium=web2x&context=3">Explanation "why it works here"</a>
     * @see <a href="https://www.baeldung.com/java-least-common-multiple">Baeldung copypasta</a>
     */
    public static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }


}
