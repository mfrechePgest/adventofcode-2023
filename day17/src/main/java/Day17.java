import mf.map.Direction;
import mf.map.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day17 extends AbstractMultiStepDay<Long, Long> {

    public Day17(String fileName) {
        super(fileName);
    }

    public Day17() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day17 day17 = new Day17();
        day17.fullRun();
    }

    public Long resultStep1() {
        return computeResult(false);
    }

    private long computeResult(boolean step2) {
        Point exit = Point.of(mapWidth - 1, mapHeight - 1);
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator
                .comparingDouble((Node node) -> node.cost() + node.point().manhattanDist(exit))
                .thenComparing(Objects::hashCode));
        System.out.println("Going to " + exit);
        queue.add(new Node(lavaLossMap.get(Point.of(0, 0)), Direction.EAST, 0, null));
        queue.add(new Node(lavaLossMap.get(Point.of(0, 0)), Direction.SOUTH, 0, null));
        Map<Node, Long> alreadyVisited = new HashMap<>();
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (currentNode.neverSeenOrBetterThanPrevious(alreadyVisited)) {
                if (currentNode.point().equals(exit) &&
                        (!step2 || currentNode.consecutiveSameDirection() >= 4)) {
                    System.out.println("Found exit in " + alreadyVisited.size() + " steps");
                    return currentNode.cost();
                }
                alreadyVisited.put(currentNode, currentNode.cost());
//                System.out.print("Running " + currentNode.point() + " - cost = " + currentNode.cost() + " - alreadyVisited = " + alreadyVisited.size() + " queue - " + queue.size() + "  \r");
                currentNode.neighbours(mapWidth, mapHeight, lavaLossMap, step2)
                        .filter(n -> n.neverSeenOrBetterThanPrevious(alreadyVisited))
                        .forEach(queue::add);
            }
        }

        throw new IllegalArgumentException("Can't found exit");
    }

    public Long resultStep2() {
        return computeResult(true);
    }

    private final Map<Point, CityBlock> lavaLossMap = new HashMap<>();
    private int mapWidth;
    private int mapHeight;

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            int currentIdx = 0;
            mapWidth = line.length();
            while (line != null) {
                for (int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    lavaLossMap.put(Point.of(x, currentIdx), new CityBlock(x, currentIdx,Character.getNumericValue(c)));
                }
                currentIdx++;
                line = br.readLine();
            }
            mapHeight = currentIdx;
        }
    }


}
