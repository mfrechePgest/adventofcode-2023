import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day07 extends AbstractMultiStepDay<Long, Long> {

    public Day07(String fileName) {
        super(fileName);
    }

    public Day07() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day07 day07 = new Day07();
        day07.fullRun();
    }

    public Long resultStep1() {
        Iterator<Hand> itHands = hands.iterator();
        return computeResult(itHands);
    }

    private long computeResult(Iterator<Hand> itHands) {
        long result = 0L;
        int idx = 1;
        while (itHands.hasNext()) {
            Hand next = itHands.next();
//            System.out.println("Hand " + next.getValue() +
//                    " of type " + next.getType() +
//                    " finised " + idx +
//                    " with a bid of " + next.getBid());
            result += next.getBid() * idx;
            idx++;
        }
        return result;
    }

    public Long resultStep2() {
        SortedSet<Hand> handsWithJokers = new TreeSet<>(new HandComparator(true));
        handsWithJokers.addAll(hands);
        Iterator<Hand> itHands = handsWithJokers.iterator();
        return computeResult(itHands);
    }

    SortedSet<Hand> hands = new TreeSet<>(new HandComparator(false));

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                hands.add(new Hand(line));
                line = br.readLine();
            }
        }
    }


}
