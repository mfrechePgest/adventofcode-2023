import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day09 extends AbstractMultiStepDay<Long, Long> {

    public Day09(String fileName) {
        super(fileName);
    }

    public Day09() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day09 day09 = new Day09();
        day09.fullRun();
    }

    public Long resultStep1() {
        return sum1;
    }

    public Long resultStep2() {
        return sum2;
    }

    long sum1 = 0;
    long sum2 = 0;

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                List<Long> lineInput = Arrays.stream(line.split(" "))
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .toList();
                computeLineResult(lineInput);
                line = br.readLine();
            }
        }
    }

    private void computeLineResult(List<Long> lineInput) {
        List<Long> currentSequence = new ArrayList<>(lineInput);
        List<Long> firstValues = new ArrayList<>();
        List<Long> lastValues = new ArrayList<>();
        StringBuilder offset = new StringBuilder();

        while (!currentSequence.stream().allMatch(l -> l == 0)) {
            offset.append(" ");
            lastValues.add(currentSequence.getLast());
            firstValues.add(currentSequence.getFirst());
            List<Long> nextSeq = new ArrayList<>();
            for ( int i = 1 ; i < currentSequence.size() ; i++ ) {
                nextSeq.add(currentSequence.get(i) - currentSequence.get(i-1));
            }
            currentSequence = nextSeq;
        }

        long endPlaceHolder = 0;
        long beginPlaceHolder = 0;
        for (int i = lastValues.size() - 1; i >=0 ; i-- ) {
            endPlaceHolder += lastValues.get(i);
            beginPlaceHolder = firstValues.get(i) - beginPlaceHolder;
        }

        sum1 +=  endPlaceHolder;
        sum2 += beginPlaceHolder;
    }


}
