public enum Direction {
    NORTH('^') {
        @Override
        public Point move(Point point) {
            return Point.of(point.x(), point.y() - 1);
        }

        @Override
        public Direction reversed() {
            return SOUTH;
        }
    },
    SOUTH('v') {
        @Override
        public Point move(Point point) {
            return Point.of(point.x(), point.y() + 1);
        }

        @Override
        public Direction reversed() {
            return NORTH;
        }
    },
    EAST('>') {
        @Override
        public Point move(Point point) {
            return Point.of(point.x() + 1, point.y());
        }

        @Override
        public Direction reversed() {
            return WEST;
        }
    },
    WEST('<') {
        @Override
        public Point move(Point point) {
            return Point.of(point.x() - 1, point.y());
        }

        @Override
        public Direction reversed() {
            return EAST;
        }
    };

    private final char c;

    Direction(char c) {
        this.c = c;
    }

    public abstract Point move(Point point);

    public char toChar() {
        return c;
    }

    public abstract Direction reversed();
}
