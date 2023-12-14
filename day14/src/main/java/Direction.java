public enum Direction {
    NORTH {
        @Override
        public Point move(Point point) {
            return new Point(point.x(), point.y() - 1);
        }
    },
    SOUTH {
        @Override
        public Point move(Point point) {
            return new Point(point.x(), point.y() + 1);
        }
    },
    EAST {
        @Override
        public Point move(Point point) {
            return new Point(point.x() + 1, point.y());
        }
    },
    WEST {
        @Override
        public Point move(Point point) {
            return new Point(point.x() - 1, point.y());
        }
    };

    public abstract Point move(Point point);
}
