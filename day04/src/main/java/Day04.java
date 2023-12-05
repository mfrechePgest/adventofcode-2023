import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day04 extends AbstractMultiStepDay<Long, Long> {

    public Day04(String fileName) {
        super(fileName);
    }

    public Day04() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day04 day04 = new Day04();
        day04.fullRun();
    }

    public Long resultStep1() {
        return scoreStep1;
    }

    public Long resultStep2() {
        return scoreStep2;
    }


    private long scoreStep1 = 0;
    private long scoreStep2 = 0;

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            int idxLine = 1;
            Map<Integer, Integer> mapCardCount = new HashMap<>();
            while (line != null) {
                mapCardCount.merge(idxLine, 1, Integer::sum);
                Set<Integer> winningNumber = new HashSet<>();
                long scoreLine = 0;
                int winningCount = 0;
                String[] sLine = line.split("\\s+");
                boolean numbersIHave = false;
                for (int i = 2; i < sLine.length; i++) {
                    if (sLine[i].equals("|")) {
                        numbersIHave = true;
                        continue;
                    } else if (numbersIHave && !sLine[i].isEmpty()) {
                        // Numbers I have
                        if (winningNumber.contains(Integer.parseInt(sLine[i]))) {
                            // I won
                            winningCount++;
                            if (scoreLine == 0) {
                                scoreLine = 1;
                            } else {
                                scoreLine *= 2;
                            }
                        }
                    } else if (!sLine[i].isEmpty()) {
                        // Winning numbers
                        winningNumber.add(Integer.parseInt(sLine[i]));
                    }
                }
                scoreStep1 += scoreLine;
                for (int i = 1 ; i <= winningCount ; i++ ) {
                    mapCardCount.merge(idxLine + i,
                            mapCardCount.get(idxLine),
                            Integer::sum);
                }
                idxLine ++;
                line = br.readLine();
            }
            for (int i = 1 ; i < idxLine ; i++ ) {
                scoreStep2 += mapCardCount.get(i);
            }
        }
    }


}
