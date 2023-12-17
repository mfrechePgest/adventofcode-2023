import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public final class Node {
    private final CityBlock cityBlock;
    private final Direction direction;
    private final Node parent;
    private final int consecutiveSameDirection;

    public Node(CityBlock point, Direction direction, int consecutiveSameDirection, Node parent) {
        this.cityBlock = point;
        this.direction = direction;
        this.consecutiveSameDirection = consecutiveSameDirection;
        this.parent = parent;
    }

    public Stream<Node> neighbours(int mapWidth, int mapHeight, Map<Point, CityBlock> mapHeatLoss, boolean step2) {
        Stream<Node> stream = cityBlock.getPoint().neighbours(mapWidth, mapHeight)
                .filter(s -> s.dir() != direction.reversed()) // can't move backward
                .map(s -> new Node(mapHeatLoss.get(s.pos()),
                        s.dir(),
                        s.dir() == direction ? consecutiveSameDirection + 1 : 1,
                        this)
                );
        if (!step2) {
            // Step 1 : forced to turn if moved during 3 steps forward
            return stream
                    .filter(n -> n.consecutiveSameDirection() <= 3);
        } else {
            // Step 2 : forced to turn if moved during 10 steps forward
            // and can't turn if haven't moved for 4 steps
            return stream
                    .filter(n -> n.consecutiveSameDirection() <= 10)
                    .filter(n -> n.direction() == direction || this.consecutiveSameDirection() >= 4);
        }
    }



    public boolean neverSeenOrBetterThanPrevious(Map<Node, Long> alreadyVisited) {
        if (alreadyVisited.containsKey(this)) {
            return alreadyVisited.get(this) > cost();
        } else {
            return true;
        }
    }

    public Point point() {
        return cityBlock.getPoint();
    }

    public Direction direction() {
        return direction;
    }

    private Long costCache = null;

    public long cost() {
        if( costCache == null ) {
            costCache = parentChain().mapToLong(n -> n.cityBlock.getHeatLoss()).sum();
        }
        return costCache;
    }

    private Stream<Node> parentChain() {
        if (parent != null) {
            return Stream.concat(Stream.of(this), parent.parentChain());
        } else {
            return Stream.empty();
        }
    }

    public int consecutiveSameDirection() {
        return consecutiveSameDirection;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Node) obj;
        return Objects.equals(this.cityBlock, that.cityBlock) &&
                Objects.equals(this.direction, that.direction) &&
                this.consecutiveSameDirection == that.consecutiveSameDirection;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityBlock, direction, consecutiveSameDirection);
    }

    @Override
    public String toString() {
        return "Node[" +
                "cityBlock=" + cityBlock + ", " +
                "direction=" + direction + ", " +
                "consecutiveSameDirection=" + consecutiveSameDirection + ']';
    }

}
