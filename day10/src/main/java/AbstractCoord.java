public abstract class AbstractCoord {

    private final Point coord;

    protected AbstractCoord(Point coord) {
        this.coord = coord;
    }

    public Point getCoord() {
        return coord;
    }
}
