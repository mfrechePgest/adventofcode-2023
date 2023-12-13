import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class Day13 extends AbstractMultiStepDay<Long, Long> {

    public Day13(String fileName) {
        super(fileName);
    }

    public Day13() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day13 day13 = new Day13();
        day13.fullRun();
    }

    public Long resultStep1() {
        return mirrors.stream()
                .mapToLong(mirrorMap ->
                        mirrorMap.findHorizontalSymmetry()
                                .stream().map(i -> 100 * i)
                                .findFirst()
                                .orElseGet(() ->
                                        mirrorMap.findVerticalSymmetry()
                                                .orElseThrow(() -> new NoSuchElementException("Can't find symmetry in : \n" + mirrorMap)))
                )
                .sum();
    }

    public Long resultStep2() {
        return mirrors.stream()
                .mapToLong(mirrorMap ->
                        mirrorMap.findHorizontalSmudgeNewSymmetry()
                                .stream().map(i -> 100 * i)
                                .findFirst()
                                .orElseGet(() ->
                                        mirrorMap.findVerticalSmudgeNewSymmetry()
                                                .orElseThrow(() -> new NoSuchElementException("Can't find symmetry in : \n" + mirrorMap)))
                )
                .sum();
    }

    List<MirrorMap> mirrors = new ArrayList<>();

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            MirrorMap currentMap = new MirrorMap(new ArrayList<>());
            mirrors.add(currentMap);
            String line = br.readLine();
            while (line != null) {
                if (line.isBlank()) {
                    currentMap = new MirrorMap(new ArrayList<>());
                    mirrors.add(currentMap);
                } else {
                    currentMap.lines().add(line);
                }
                line = br.readLine();
            }
        }
    }


}
