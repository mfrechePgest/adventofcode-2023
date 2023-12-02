import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day02 extends AbstractMultiStepDay<Long, Long> {

    public Day02(String fileName) {
        super(fileName);
    }

    public Day02() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day02 day02 = new Day02();
        day02.fullRun();
    }

    public Long resultStep1() {
        return result1;
    }

    public Long resultStep2() {
        return result2;
    }

    long result1;
    long result2;

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                
                computeStep1(line);
                computeStep2(line);

                line = br.readLine();
            }
        }
    }

    static final int MAX_GREEN = 13;
    static final int MAX_RED = 12;
    static final int MAX_BLUE = 14;

    private void computeStep1(String line) {
        String[] splitted = line.split(" ");
        int gameId = Integer.parseInt(splitted[1].replace(":", ""));
        for (int i = 2; i < splitted.length ; i+=2) {
            int count = Integer.parseInt(splitted[i]);
            Color color = Color.valueOf(splitted[i+1]
                    .replace(",","")
                    .replace(";","")
                    .toUpperCase());
            switch (color) {
                case RED -> {
                    if (count > MAX_RED) {
                        return;
                    }
                }
                case BLUE -> {
                    if (count > MAX_BLUE) {
                        return;
                    }
                }
                case GREEN -> {
                    if (count > MAX_GREEN) {
                        return;
                    }
                }
            }
        }
        result1 += gameId;
    }

    private void computeStep2(String line) {
        String[] splitted = line.split(" ");
        int gameId = Integer.parseInt(splitted[1].replace(":", ""));
        Map<Color, Integer> map = new HashMap<>();
        for (int i = 2; i < splitted.length ; i+=2) {
            int count = Integer.parseInt(splitted[i]);
            Color color = Color.valueOf(splitted[i+1]
                    .replace(",","")
                    .replace(";","")
                    .toUpperCase());
            map.merge(color, count, Integer::max);
        }
        long lineResult = (long) map.get(Color.RED) * map.get(Color.BLUE) * map.get(Color.GREEN);
        result2 += lineResult;
    }



}
