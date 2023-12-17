import mf.map.Point;

public class CityBlock {

    private final Point point;
    private final int heatLoss;

    public CityBlock(int x, int y, int heatLoss) {
        point = Point.of(x, y);
        this.heatLoss = heatLoss;
    }

    public Point getPoint() {
        return point;
    }

    public int getHeatLoss() {
        return heatLoss;
    }

    public String toString() {
        return "[" + point + "," + heatLoss + "]";
    }
}
