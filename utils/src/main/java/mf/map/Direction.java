package mf.map;

public enum Direction {
    NORTH('^') {
        @Override
        public Point move(Point point, double step) {
            return Point.of(point.x(), Math.floor(point.y() - step));
        }

        @Override
        public Direction reversed() {
            return SOUTH;
        }
        @Override
        public Direction left() {
            return WEST;
        }

        @Override
        public Direction right() {
            return EAST;
        }
    },
    SOUTH('v') {
        @Override
        public Point move(Point point, double step) {
            return Point.of(point.x(), Math.ceil(point.y() + step));
        }

        @Override
        public Direction reversed() {
            return NORTH;
        }

        @Override
        public Direction left() {
            return EAST;
        }

        @Override
        public Direction right() {
            return WEST;
        }
    },
    EAST('>') {
        @Override
        public Point move(Point point, double step) {
            return Point.of(Math.ceil(point.x() + step), point.y());
        }

        @Override
        public Direction reversed() {
            return WEST;
        }

        @Override
        public Direction left() {
            return NORTH;
        }

        @Override
        public Direction right() {
            return SOUTH;
        }
    },
    WEST('<') {
        @Override
        public Point move(Point point, double step) {
            return Point.of(Math.floor(point.x() - step), point.y());
        }

        @Override
        public Direction reversed() {
            return EAST;
        }

        @Override
        public Direction left() {
            return SOUTH;
        }

        @Override
        public Direction right() {
            return NORTH;
        }
    };

    private final char c;

    Direction(char c) {
        this.c = c;
    }

    public abstract Point move(Point point, double step);

    public char toChar() {
        return c;
    }

    public abstract Direction reversed();

    public abstract Direction left();
    public abstract Direction right();
}
