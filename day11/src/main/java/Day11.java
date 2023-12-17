import mf.map.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day11 extends AbstractMultiStepDay<Long, Long> {

    public Day11(String fileName) {
        super(fileName);
    }

    public Day11() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day11 day11 = new Day11();
        day11.fullRun();
    }

    public Long resultStep1() {
        return computeResult(2);
    }

    long computeResult(long delta) {
        Map<Point, Map<Point, Long>> mapDist = new HashMap<>();
        Map<Point, Galaxy> galaxies = expandUniverse(mapGalaxies, delta);
        return galaxies.values()
                .stream()
                .map(Galaxy::getPoint)
                .flatMap(p1 -> galaxies.values()
                        .stream()
                        .map(Galaxy::getPoint)
                        .filter(p2 -> !p2.equals(p1))
                        .filter(p2 -> !mapDist.containsKey(p2) || !mapDist.get(p2).containsKey(p1))
                        .map(p2 -> {
                            long dist = (long) p2.manhattanDist(p1);
                            mapDist.computeIfAbsent(p1, k -> new HashMap<>()).put(p2, dist);
                            mapDist.computeIfAbsent(p2, k -> new HashMap<>()).put(p1, dist);
                            return dist;
                        })
                ).mapToLong(d -> d)
                .sum();
    }

    public Long resultStep2() {
        return computeResult(1_000_000);
    }

    Map<Point, Galaxy> mapGalaxies = new HashMap<>();
    int galaxyHeight;
    int galaxyWidth;

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            int lineIndex = 0;
            galaxyWidth = line.length();
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == '#') {
                        Point key = Point.of(i,  lineIndex);
                        Galaxy galaxy = new Galaxy(key);
                        mapGalaxies.put(key, galaxy);
                    }
                }
                lineIndex++;
                line = br.readLine();
            }
            galaxyHeight = lineIndex;
        }
    }

    private Map<Point, Galaxy> expandUniverse(Map<Point, Galaxy> galaxies, long delta) {
        List<Long> expandedColumns = new ArrayList<>();
        for (long i = 0; i < galaxyWidth; i++) {
            long finalI = i;
            if (galaxies.keySet().stream().noneMatch(p -> p.x() == finalI)) {
                // empty column : expand
                expandedColumns.add(i);
            }
        }
        Map<Point, Galaxy> result = galaxies.values()
                .stream()
                .map(e -> {
                    double previousX = e.getPoint().x();
                    return new Galaxy(
                            Point.of(
                                    ((delta - 1) * expandedColumns.stream().filter(x -> x < previousX).count()) + previousX,
                                    e.getPoint().y()
                            )
                    );
                }).collect(Collectors.toMap(Galaxy::getPoint, e -> e));


        List<Long> expandedRows = new ArrayList<>();
        for (long i = 0; i < galaxyHeight; i++) {
            long finalI = i;
            if (galaxies.keySet().stream().noneMatch(p -> p.y() == finalI)) {
                // empty column : expand
                expandedRows.add(i);
            }
        }
        result = result.values()
                .stream()
                .map(e -> {
                    double previousY = e.getPoint().y();
                    return new Galaxy(
                            Point.of(
                                    e.getPoint().x()
                                    ,
                                    ((delta - 1) * expandedRows.stream().filter(y -> y < previousY).count() + previousY)
                            )
                    );
                }).collect(Collectors.toMap(Galaxy::getPoint, e -> e));


        return result;
    }


}
