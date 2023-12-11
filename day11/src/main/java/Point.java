public record Point(long x, long y) {
    public long manhanttanDist(Point p1) {
        return Math.abs(p1.x() - this.x) + Math.abs(p1.y() - this.y);
    }
}
