import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToLongBiFunction;

public class Day03 extends AbstractMultiStepDay<Long, Long> {

    public Day03(String fileName) {
        super(fileName);
    }

    public Day03() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day03 day03 = new Day03();
        day03.fullRun();
    }

    public Long resultStep1() {
        return computeResult(symbols, i -> true,
                0,
                (symbolSum, i1) -> {
                    symbolSum += i1;
                    return symbolSum;
                });
    }

    public Long resultStep2() {
        return computeResult(gears, adjacentNumbers1 -> adjacentNumbers1.size() >= 2,
                1,
                (gearRatio1, i1) -> {
                    gearRatio1 *= i1;
                    return gearRatio1;
                });
    }

    private Integer getAndRemoveNumber(Point adjacent, HashMap<Integer, Map<Integer, Integer>> mapCopy) {
        StringBuilder number = new StringBuilder("" + mapCopy.get(adjacent.x()).remove(adjacent.y()));
        Point currentPoint = adjacent.left();
        while (true) {
            Integer i = getDigitFromMap(currentPoint, mapCopy);
            if (i == null) break;
            number.insert(0, i);
            mapCopy.get(currentPoint.x()).remove(currentPoint.y());
            currentPoint = currentPoint.left();
        }
        currentPoint = adjacent.right();
        while (true) {
            Integer i = getDigitFromMap(currentPoint, mapCopy);
            if (i == null) break;
            number.append(i);
            mapCopy.get(currentPoint.x()).remove(currentPoint.y());
            currentPoint = currentPoint.right();
        }
        return Integer.parseInt(number.toString());
    }

    private HashMap<Integer, Map<Integer, Integer>> copyMap() {
        HashMap<Integer, Map<Integer, Integer>>
                mapCopy = new HashMap<>(map);
        mapCopy.keySet()
                .forEach(k -> mapCopy.put(k, new HashMap<>(mapCopy.get(k))));
        return mapCopy;
    }

    private Integer getDigitFromMap(Point adjacent, HashMap<Integer, Map<Integer, Integer>> mapCopy) {
        Map<Integer, Integer> column = mapCopy.get(adjacent.x());
        Integer digit;
        if (column != null) {
            digit = column.get(adjacent.y());
        } else {
            digit = null;
        }
        return digit;
    }

    /**
     * @param symbols symbol list which is valid on this step ( all symbols on step 1, only gears on step 2 )
     * @param adjacentCondition condition where symbol is valid, depending on adjacent numbers ( every symbol is valid on step 1, only with 2 adjacents on step 2 )
     * @param initialSymbolValue symbol score initial value ( step 1 is a sum so initial value is 0, step 2 is a product so initial value is 1 )
     * @param accumulator symbol score accumulation operator ( step 1 is a sum, step 2 is a product )
     */
    private long computeResult(List<Point> symbols, Predicate<List<Integer>> adjacentCondition,
                               long initialSymbolValue, ToLongBiFunction<Long, Integer> accumulator) {
        HashMap<Integer, Map<Integer, Integer>> mapCopy = copyMap();
        long sum = 0;
        for (Point gear : symbols) {
            long gearRatio = initialSymbolValue;
            List<Point> adjacents = gear.getAdjacent(true);
            List<Integer> adjacentNumbers = adjacents.stream()
                    .filter(p -> getDigitFromMap(p, mapCopy) != null)
                    .map(p -> getAndRemoveNumber(p, mapCopy))
                    .toList();
            if (adjacentCondition.test(adjacentNumbers)) {
                for (Integer i : adjacentNumbers) {
                    gearRatio = accumulator.applyAsLong(gearRatio, i);
                }
                sum += gearRatio;
            }
        }
        return sum;
    }

    Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
    List<Point> symbols = new ArrayList<>();
    List<Point> gears = new ArrayList<>();

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            int lineIndex = 0;
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c != '.') {
                        if (Character.isDigit(c)) {
                            map.computeIfAbsent(i, k -> new HashMap<>())
                                    .put(lineIndex, Character.getNumericValue(c));
                        } else {
                            symbols.add(new Point(i, lineIndex));
                            if (c == '*') {
                                gears.add(new Point(i, lineIndex));
                            }
                        }
                    }
                }
                line = br.readLine();
                lineIndex++;
            }
        }
    }


}
