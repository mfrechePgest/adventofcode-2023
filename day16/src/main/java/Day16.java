import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 extends AbstractMultiStepDay<Long, Long> {

    public Day16(String fileName) {
        super(fileName);
    }

    public Day16() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day16 day16 = new Day16();
        day16.fullRun();
    }

    public Long resultStep1() {
        Point pos = new Point(0, 0);
        Direction dir = Direction.EAST;
        Step initialStep = new Step(pos, dir);
        return countEnergizedPoints(initialStep);
    }

    private long countEnergizedPoints(Step initialStep) {
        Set<Step> setSteps = new HashSet<>();
        Queue<Step> steps = new LinkedList<>();
        steps.add(initialStep);
        while (!steps.isEmpty()) {
            Step s = steps.poll();
            setSteps.add(s);
//            debug(setSteps, s.toString());
            Stream<Direction> dirs = mirrorMap.containsKey(s.pos()) ? mirrorMap.get(s.pos()).reflect(s.dir()) : Stream.of(s.dir());
            dirs.map(d -> new Step(d.move(s.pos()), d))
                    .filter(newStep -> newStep.pos().isValid(mapWidth, mapHeight))
                    .filter(newStep -> !setSteps.contains(newStep))
                    .forEach(steps::add);
        }

        return setSteps.stream()
                .map(Step::pos)
                .distinct()
                .count();
    }

    public Long resultStep2() {
        List<Step> initialSteps = new ArrayList<>();
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if ((y != 0 || x != 0) && (y != mapWidth - 1 || x != mapWidth - 1)) {
                    Direction dir = null;
                    if (x == 0) {
                        dir = Direction.EAST;
                    } else if (y == 0) {
                        dir = Direction.SOUTH;
                    } else if (y == mapWidth - 1) {
                        dir = Direction.NORTH;
                    } else {
                        dir = Direction.WEST;
                    }
                    Step s = new Step(new Point(x, y), dir);
                    initialSteps.add(s);
                }
            }
        }

        return initialSteps.stream()
                .mapToLong(this::countEnergizedPoints)
                .max()
                .orElse(0);
    }

    private void debug(Set<Step> stepSet, String label) {
        System.out.println("=======" + label + "=======");
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                String c;
                Point point = new Point(x, y);
                if (mirrorMap.containsKey(point)) {
                    c = ConsoleColors.coloredString(mirrorMap.get(point).getChar(), ConsoleColors.CYAN);
                } else {
                    List<Step> steps = stepSet.stream()
                            .filter(s -> s.pos().equals(point))
                            .toList();
                    if (steps.size() > 1) {
                        c = ConsoleColors.coloredString("#", ConsoleColors.RED);
                    } else if (steps.size() == 1) {
                        c = ConsoleColors.coloredString(steps.get(0).dir().toChar(), ConsoleColors.RED);
                    } else {
                        c = ".";
                    }
                }
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private final Map<Point, Mirror> mirrorMap = new HashMap<>();
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
                    if (c != '.') {
                        mirrorMap.put(new Point(x, currentIdx), Mirror.fromChar(c));
                    }
                }

                currentIdx++;
                line = br.readLine();
            }
            mapHeight = currentIdx;
        }
    }


}
