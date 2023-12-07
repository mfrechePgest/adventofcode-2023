import java.util.Map;
import java.util.stream.Collectors;

public enum HandType {

    HIGH_CARD, ONE_PAIR, TWO_PAIRS, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND;

    public static HandType fromHand(String handValue) {
        Map<Character, Long> countingMap = handValue.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        if (countingMap.containsValue(5L)) {
            return FIVE_OF_A_KIND;
        } else if (countingMap.containsValue(4L)) {
            return FOUR_OF_A_KIND;
        } else if (countingMap.containsValue(3L) && countingMap.containsValue(2L)) {
            return FULL_HOUSE;
        } else if (countingMap.containsValue(3L)) {
            return THREE_OF_A_KIND;
        } else if (countingMap.values().stream().filter(v -> v == 2L).count() == 2) {
            return TWO_PAIRS;
        } else if (countingMap.containsValue(2L)) {
            return ONE_PAIR;
        } else {
            return HIGH_CARD;
        }
    }

    public static HandType fromHandWithJokers(String handValue) {
        Map<Character, Long> countingMap = handValue.chars()
                .mapToObj(i -> (char) i)
                .filter(c -> c != 'J')
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        long jokerCount = handValue.chars().filter(c -> c == 'J').count();
        long maxCharCount = countingMap.values().stream().mapToLong(Long::valueOf).max().orElse(1);
        if (countingMap.containsValue(5L) ||
                jokerCount + maxCharCount >= 5) {
            return FIVE_OF_A_KIND;
        } else if (countingMap.containsValue(4L) ||
                jokerCount + maxCharCount >= 4) {
            return FOUR_OF_A_KIND;
        } else if (countingMap.containsValue(3L) && countingMap.containsValue(2L) ||
                countingMap.values().stream().filter(v -> v == 2L).count() == 2 && jokerCount >= 1) { // Only full house with joker is 2 pairs + 1 joker
            return FULL_HOUSE;
        } else if (countingMap.containsValue(3L) ||
                jokerCount + maxCharCount >= 3) {
            return THREE_OF_A_KIND;
        } else if (countingMap.values().stream().filter(v -> v == 2L).count() == 2 ||
                countingMap.containsValue(2L) && jokerCount >= 1 || // Two pairs happens with one pair & joker
                jokerCount >= 2) {  // or if there's still 2 jokers
            return TWO_PAIRS;
        } else if (countingMap.containsValue(2L) ||
                jokerCount + maxCharCount >= 2) {
            return ONE_PAIR;
        } else {
            return HIGH_CARD;
        }
    }
}
