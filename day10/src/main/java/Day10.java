import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day10 extends AbstractMultiStepDay<Long, Long> {

    public Day10(String fileName) {
        super(fileName);
    }

    public Day10() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day10 day10 = new Day10();
        day10.fullRun();
    }

    public Long resultStep1() {
        return start.findFarthestInLoop(map, ground);
    }

    public Long resultStep2() {
        start.findFarthestInLoop(map, ground);
        for (Ground g1 : ground.values()) {
            if (g1.isInsideLoop() == null) {
                g1.computeInsideLoop(ground, map, maxX, maxY);
            }
        }
        long result = ground.values().stream()
                .filter(g -> g.computeInsideLoop(ground, map, maxX, maxY))
                .count();
        debug(map, ground, maxX, maxY, null);
        return result;
    }

    public static void debug(Map<Point, Pipe> pipeMap,
                             Map<Point, Ground> groundMap,
                             int width, int height, Point enlighten) {
        clearConsole();
        StringBuilder s = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = new Point(x, y);
                String debug;
                String color = ConsoleColors.WHITE;
                if (pipeMap.get(p) != null) debug = pipeMap.get(p).toDebugChar();
                else {
                    debug = "0";
                    color = groundMap.get(p).isInsideLoop() == null ? ConsoleColors.WHITE :
                            groundMap.get(p).isInsideLoop() ? ConsoleColors.CYAN : ConsoleColors.RED;
                }
                if (enlighten != null &&
                        (p.equals(enlighten) || p.equals(enlighten.ceil()) || p.equals(enlighten.floor()))) {
                    color = ConsoleColors.YELLOW;
                }
                debug = ConsoleColors.coloredString(debug,
                        color
                );
                s.append(debug);
            }
            s.append("\n");
        }
        System.out.println(s);
    }

    Map<Point, Pipe> map = new HashMap<>();
    Pipe start = null;
    Map<Point, Ground> ground = new HashMap<>();
    int maxX, maxY;

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            maxX = line.length();
            int lineIndex = 0;
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    Point p = new Point(i, lineIndex);
                    char c = line.charAt(i);
                    if (c == '.') {
                        ground.put(p, new Ground(p));
                        continue;
                    } else {
                        Pipe pipe = new Pipe(p, c);
                        if (c == 'S') {
                            start = pipe;
                        }
                        map.put(p, pipe);
                    }
                }
                line = br.readLine();
                lineIndex++;
            }
            maxY = lineIndex;
        }
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}
