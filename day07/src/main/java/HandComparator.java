import java.util.Comparator;

public class HandComparator implements Comparator<Hand> {

    private final boolean withJokers;

    public HandComparator(boolean withJokers) {
        this.withJokers = withJokers;
    }

    @Override
    public int compare(Hand o1, Hand o2) {
        return Comparator.comparingInt((Hand h) -> {
                    if (withJokers) {
                        return h.getTypeWithJoker().ordinal();
                    } else {
                        return h.getType().ordinal();
                    }
                })
                .thenComparing(h -> getCardPower(h.getValue().charAt(0)))
                .thenComparing(h -> getCardPower(h.getValue().charAt(1)))
                .thenComparing(h -> getCardPower(h.getValue().charAt(2)))
                .thenComparing(h -> getCardPower(h.getValue().charAt(3)))
                .thenComparing(h -> getCardPower(h.getValue().charAt(4)))
                .compare(o1, o2);
    }

    public int getCardPower(char card) {
        if (Character.isDigit(card)) {
            return Character.getNumericValue(card);
        } else {
            CardFaces face = CardFaces.valueOf("" + card);
            if (withJokers && face == CardFaces.J) {
                return 0;
            }
            return 10 + face.ordinal();
        }
    }
}
