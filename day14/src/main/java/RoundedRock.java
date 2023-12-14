import java.util.Map;

public record RoundedRock(Point point) {

    public RoundedRock roll(Direction direction,
                            Map<Point, RoundedRock> rocks,
                            Map<Point, SquaredRock> obstacles,
                            int mapWidth, int mapHeight) {
        Point futurePos = direction.move(point);
        Point previousPoint = point;
        while (!rocks.containsKey(futurePos)
                && !obstacles.containsKey(futurePos)
                && futurePos.isValid(mapWidth, mapHeight)) {
            previousPoint = futurePos;
            futurePos = direction.move(futurePos);
        }
        rocks.remove(point);
        RoundedRock value = new RoundedRock(previousPoint);
        rocks.put(previousPoint, value);
        return value;
    }

}
