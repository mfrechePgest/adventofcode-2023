import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day05 extends AbstractMultiStepDay<Long, Long> {

    public Day05(String fileName) {
        super(fileName);
    }

    public Day05() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day05 day05 = new Day05();
        day05.fullRun();
    }

    public Long resultStep1() {
        Stream<Long> stream = mySeeds.stream();
        for (AlmanacMap map : maps) {
            stream = stream
                    .map(map.function);
        }
        return stream.min(Long::compare).orElse(-1L);
    }

    public Long resultStep2() {
        Stream<Long> stream = Stream.empty();
        for (int i = 0; i < mySeeds.size(); i += 2) {
            stream = Stream.concat(
                    stream,
                    LongStream.range(mySeeds.get(i), mySeeds.get(i) + mySeeds.get(i + 1)).boxed()
            );
        }
        for (AlmanacMap map : maps) {
            stream = stream
                    .map(map.function);
        }
        return stream.min(Long::compare).orElse(-1L);
    }

    private List<Long> mySeeds = new ArrayList<>();
    private List<AlmanacMap> maps = new ArrayList<>();


    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            AlmanacMap currentMap = null;
            while (line != null) {
                if (line.startsWith("seeds:")) {
                    mySeeds = Arrays.stream(line.split(" "))
                            .skip(1)
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
                } else if (line.isEmpty() || line.isBlank()) {
                    if (currentMap != null) {
                        maps.add(currentMap);
                    }
                } else if (!Character.isDigit(line.charAt(0))) {
                    currentMap = new AlmanacMap(line.split(" ")[0]);
                } else {
                    String[] splitted = line.split(" ");
                    Long destinationStart = Long.parseLong(splitted[0]);
                    Long sourceStart = Long.parseLong(splitted[1]);
                    Long range = Long.parseLong(splitted[2]);
                    Function<Long, Long> previousFunc = currentMap.function;
                    Function<Long, Long> newFunc = i -> {
                        if (i >= sourceStart && i < sourceStart + range) {
                            return i + (destinationStart - sourceStart);
                        } else {
                            return previousFunc.apply(i);
                        }
                    };
                    currentMap.function = newFunc;
                }
                line = br.readLine();
            }
            maps.add(currentMap);
        }
    }


}
