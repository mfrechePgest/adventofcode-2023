public record Point(double x, double y) {
    public Point avg(Point coord) {
        return new Point((x + coord.x) / 2d,
                (y + coord.y) / 2d);
    }

    public Point floor() {
        return new Point(Math.floor(x), Math.floor(y));
    }
    public Point ceil() { return new Point(Math.ceil(x), Math.ceil(y));}
}
