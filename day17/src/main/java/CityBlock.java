public class CityBlock {

    private Point point;
    private int heatLoss;

    public CityBlock(int x, int y, int heatLoss) {
        point = Point.of(x, y);
        this.heatLoss = heatLoss;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getHeatLoss() {
        return heatLoss;
    }

    public void setHeatLoss(int heatLoss) {
        this.heatLoss = heatLoss;
    }

    public String toString() {
        return "[" + point + "," + heatLoss + "]";
    }
}
