import java.io.BufferedReader;
import java.io.IOException;

public class Day01 extends AbstractMultiStepDay<Long, Long> {

    public Day01(String fileName) {
        super(fileName);
    }

    public Day01() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day01 day01 = new Day01();
        day01.fullRun();
    }

    public Long resultStep1() {
        return sumStep1;
    }

    public Long resultStep2() {
        return sumStep2;
    }

    long sumStep1 = 0;
    long sumStep2 = 0;

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                sumStep1 += computeLineScore(line);
                sumStep2 += computeLineScore(replaceDigitWords(line));


                line = br.readLine();
            }
        }
    }

    private String replaceDigitWords(String line) {
        return line
                .replace("one", "o1e")
                .replace("two", "t2o")
                .replace("three", "t3e")
                .replace("four", "f4r")
                .replace("five", "f5e")
                .replace("six", "s6x")
                .replace("seven", "s7n")
                .replace("eight", "e8t")
                .replace("nine", "n9e");
    }

    private static long computeLineScore(String line) {
        int firstDigit = -1;
        int lastDigit = -1;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                if (firstDigit == -1) {
                    firstDigit = Character.getNumericValue(c);
                }
                lastDigit = Character.getNumericValue(c);
            }
        }
        return firstDigit * 10L + lastDigit;
    }


}
