import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day06 extends AbstractMultiStepDay<Long, Long> {

    public Day06(String fileName) {
        super(fileName);
    }

    public Day06() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day06 day06 = new Day06();
        day06.fullRun();
    }

    public Long resultStep1() {
        return races.stream()
                .mapToLong(this::waysToWin)
                .reduce((a, b) -> a * b)
                .orElse(-1L);
    }

    public Long resultStep2() {
        return waysToWin(races.stream()
                .reduce(new Race(0), (r1, r2) ->
                {
                    String time = r1.getTime() + "" + r2.getTime();
                    String dist = r1.getDistance() + "" + r2.getDistance();
                    Race result = new Race(Long.parseLong(time));
                    result.setDistance(Long.parseLong(dist));
                    return result;
                }));
    }

    private long waysToWin(Race race) {
        // (time - x) * x > distance
        // x*time - x² > distance
        // -x² + time*x - distance > 0
        // x = (-time +- sqrt(time² - 4 * distance)) / ( -2 )
        // x = time/2 +- (1/2 * sqrt(time² - 4distance))
        double upperBound = race.getTime() / 2d + ( 0.5d * Math.sqrt( race.getTime() * race.getTime() - 4 * race.getDistance() ));
        double lowerBound = race.getTime() / 2d - ( 0.5d * Math.sqrt( race.getTime() * race.getTime() - 4 * race.getDistance() ));
        System.out.println( lowerBound + " < x < " + upperBound );
        upperBound = upperBound % 1 == 0 ? upperBound - 1 : Math.floor(upperBound);
        lowerBound = lowerBound % 1 == 0 ? lowerBound + 1 : Math.ceil(lowerBound);
        System.out.println( lowerBound + " < x < " + upperBound );
        long waysToWin = (long) (upperBound - lowerBound + 1);
        System.out.println( "Ways to win = " +
                ConsoleColors.coloredString(String.valueOf(waysToWin), ConsoleColors.PURPLE) );
        return waysToWin;
    }

    private List<Race> races = new ArrayList<>();

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                String[] split = line.split("\\s+");
                for (int i = 1; i < split.length; i++) {
                    if (line.startsWith("Time:")) {
                        races.add(new Race(Integer.parseInt(split[i])));
                    } else {
                        races.get(i - 1).setDistance(Integer.parseInt(split[i]));
                    }
                }
                line = br.readLine();
            }
        }
    }


}
