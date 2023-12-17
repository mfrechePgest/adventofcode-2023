import java.util.*;
import java.util.stream.Stream;

public final class Point {
    private final int x;
    private final int y;

    private static final Map<Integer, Map<Integer,Point>> pointCache = new HashMap<>();

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        if (!pointCache.containsKey(x)) {
            Point p = new Point(x,y);
            Map<Integer, Point> col = new HashMap<>();
            col.put(y, p);
            pointCache.put(x, col);
            return p;
        } else {
            return pointCache.get(x).computeIfAbsent(y, col -> new Point(x, col));
        }
    }

    public boolean isValid(int mapWidth, int mapHeight) {
        return x >= 0 && y >= 0 && x < mapWidth && y < mapHeight;
    }

    public Stream<Step> neighbours(int mapWidth, int mapHeight) {
        return Stream.of(
                new Step(Point.of(x - 1, y), Direction.WEST),
                new Step(Point.of(x + 1, y), Direction.EAST),
                new Step(Point.of(x, y - 1), Direction.NORTH),
                new Step(Point.of(x, y + 1), Direction.SOUTH)
        ).filter(s -> s.pos().isValid(mapWidth, mapHeight));
    }

    public long manhattanDist(Point p1) {
        return Math.abs(p1.x() - this.x) + Math.abs(p1.y() - this.y);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Point) obj;
        return this.x == that.x &&
                this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }

}
