import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends AbstractMultiStepDay<Long, Long> {

    public static final int FULLRUN_LIMIT = 1000000000;

    public Day14(String fileName) {
        super(fileName);
    }

    public Day14() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day14 day14 = new Day14();
        day14.fullRun();
    }

    public Long resultStep1() {
        long load = 0;
        HashMap<Point, RoundedRock> copyRockMap = new HashMap<>(rockMap);
        load = tiltPlatform(copyRockMap, Direction.NORTH);
        return load;
    }

    private long tiltPlatform(Map<Point, RoundedRock> copyRockMap, Direction direction) {
        long load = 0;
        switch (direction) {
            case NORTH -> {
                for (int y = 0; y < mapHeight; y++) {
                    for (int x = 0; x < mapWidth; x++) {
                        RoundedRock rock = copyRockMap.get(new Point(x, y));
                        load = rollRock(copyRockMap, direction, rock, load);
                    }
                }
            }
            case SOUTH -> {
                for (int y = mapHeight - 1; y >= 0; y--) {
                    for (int x = 0; x < mapWidth; x++) {
                        RoundedRock rock = copyRockMap.get(new Point(x, y));
                        load = rollRock(copyRockMap, direction, rock, load);
                    }
                }
            }
            case EAST -> {
                for (int x = mapWidth - 1; x >= 0; x--) {
                    for (int y = 0; y < mapHeight; y++) {
                        RoundedRock rock = copyRockMap.get(new Point(x, y));
                        load = rollRock(copyRockMap, direction, rock, load);
                    }
                }
            }
            case WEST -> {
                for (int x = 0; x < mapWidth; x++) {
                    for (int y = 0; y < mapHeight; y++) {
                        RoundedRock rock = copyRockMap.get(new Point(x, y));
                        load = rollRock(copyRockMap, direction, rock, load);
                    }
                }
            }
        }

        return load;
    }

    private long rollRock(Map<Point, RoundedRock> copyRockMap, Direction direction, RoundedRock rock, long load) {
        if (rock != null) {
            RoundedRock rockRolled = rock.roll(direction, copyRockMap, obstacles,
                    mapWidth, mapHeight);
            load += mapHeight - rockRolled.point().y();
        }
        return load;
    }

    public Long resultStep2() {
        long load = 0;
        Map<Point, RoundedRock> copyRockMap = new HashMap<>(rockMap);
        List<Map<Point, RoundedRock>> previousRockMaps = new ArrayList<>();
        boolean cycleReached = false;
        debug(copyRockMap, "INITIAL");
        for (long i = 0; i < FULLRUN_LIMIT; i++) {
            previousRockMaps.add(new HashMap<>(copyRockMap));
            System.err.print("Running ... " + i + " / 1000000000 NORTH\r");
            load = tiltPlatform(copyRockMap, Direction.NORTH);
            System.err.print("Running ... " + i + " / 1000000000 WEST\r");
            load = tiltPlatform(copyRockMap, Direction.WEST);
            System.err.print("Running ... " + i + " / 1000000000 SOUTH\r");
            load = tiltPlatform(copyRockMap, Direction.SOUTH);
            System.err.print("Running ... " + i + " / 1000000000 EAST\r");
            load = tiltPlatform(copyRockMap, Direction.EAST);
            if (!cycleReached && previousRockMaps.contains(copyRockMap)) {
                long cycleLength = i - (previousRockMaps.indexOf(copyRockMap)) + 1;
                System.out.println("Got a cycle ! Reached in step " + i  + " Cycle length =  " + cycleLength);
                i = FULLRUN_LIMIT - ((FULLRUN_LIMIT - i) % cycleLength);
                cycleReached = true;
            }
        }
        debug(copyRockMap, "END");
        return load;
    }

    private void debug(Map<Point, RoundedRock> rockMap, String label) {
        System.out.println("=======" + label + "=======");
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                String c;
                Point point = new Point(x, y);
                if (rockMap.containsKey(point)) {
                    c = ConsoleColors.coloredString("O", ConsoleColors.CYAN);
                } else if (obstacles.containsKey(point)) {
                    c = ConsoleColors.coloredString("#", ConsoleColors.RED);
                } else {
                    c = ".";
                }
                System.out.print(c);
            }
            System.out.println();
        }
    }

    Map<Point, RoundedRock> rockMap = new HashMap<>();
    Map<Point, SquaredRock> obstacles = new HashMap<>();
    int mapHeight;
    int mapWidth;

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            int idxLine = 0;
            mapWidth = line.length();
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    Point p = new Point(i, idxLine);
                    switch (line.charAt(i)) {
                        case '#' -> obstacles.put(p, new SquaredRock(p));
                        case 'O' -> rockMap.put(p, new RoundedRock(p));
                    }
                }
                idxLine++;
                line = br.readLine();
            }
            mapHeight = idxLine;
        }
    }


}
