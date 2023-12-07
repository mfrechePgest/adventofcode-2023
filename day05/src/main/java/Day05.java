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
        Stream<Long> stream = mySeeds.stream().parallel();
        for (AlmanacMap map : maps) {
            stream = stream
                    .map(map.function);
        }
        return stream.min(Long::compare).orElse(-1L);
    }

    public Long resultStep2() {
        Stream<Range> stream = Stream.empty();
        for (int i = 0; i < mySeeds.size(); i += 2) {
            stream = Stream.concat(
                    stream,
                    Stream.of(new Range(mySeeds.get(i), mySeeds.get(i) + mySeeds.get(i + 1)))
            );
        }
        for (AlmanacMap map : maps) {
            stream = stream
                    .peek(r -> System.err.println("Mapping range " + r.begin() + " " + r.end() + " " + map.label))
                    .flatMap(map.rangeFunction);
        }
        return stream//.parallel()
                .peek(r -> System.err.println("Range " + r.begin() + " " + r.end()))
                .map(Range::begin)
                .min(Long::compare)
                .orElse(-1L);
    }

    private List<Long> mySeeds = new ArrayList<>();
    private final List<AlmanacMap> maps = new ArrayList<>();


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
                    assert currentMap != null;
                    String[] split = line.split(" ");
                    Long destinationStart = Long.parseLong(split[0]);
                    Long sourceStart = Long.parseLong(split[1]);
                    Long range = Long.parseLong(split[2]);
                    Function<Long, Optional<Long>> innerFunc = i -> {
                        if (i >= sourceStart && i < sourceStart + range) {
                            return Optional.of(i + (destinationStart - sourceStart));
                        } else {
                            return Optional.empty();
                        }
                    };
                    Function<Long, Long> previousFunc = currentMap.function;
                    currentMap.function = i -> {
                        return innerFunc.apply(i).orElseGet(() -> previousFunc.apply(i));
                    };
                    Range source = new Range(sourceStart, sourceStart + range - 1);
                    Function<Range, Stream<Range>> previousRangeFunc = currentMap.rangeFunction;
                    currentMap.rangeFunction = r -> {
                        if (r.overlap(source)) {
                            return r.splitOverlaps(source)
                                    .map(r2 -> new Range(
                                                innerFunc.apply(r2.begin()).orElse(r2.begin()),
                                                innerFunc.apply(r2.end()).orElse(r2.end())
                                    ));
                        } else {
                            return previousRangeFunc.apply(r);
                        }
                    };
                }
                line = br.readLine();
            }
            maps.add(currentMap);
        }
    }


}
