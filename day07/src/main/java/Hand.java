public class Hand {

    private final String value;
    private final long bid;

    private final HandType type;
    private final HandType typeWithJokers;

    public Hand(String line) {
        String[] split = line.split(" ");
        value = split[0];
        bid = Long.parseLong(split[1]);
        type = HandType.fromHand(value);
        typeWithJokers = HandType.fromHandWithJokers(value);
    }

    public long getBid() {
        return bid;
    }

    public String getValue() {
        return value;
    }

    public HandType getType() {
        return type;
    }

    public HandType getTypeWithJoker() {
        return typeWithJokers;
    }
}
