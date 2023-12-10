import java.util.Arrays;
import java.util.List;

public enum Direction {

    NORTH {
        @Override
        public Point move(Point from) {
            return new Point(from.x(), Math.floor(from.y() - 0.5d));
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
    }, SOUTH {
        @Override
        public Point move(Point from) {
            return new Point(from.x(), Math.ceil(from.y() + 0.5d));
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
    }, EAST {
        @Override
        public Point move(Point from) {
            return new Point(Math.ceil(from.x() + 0.5d), from.y());
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
    }, WEST {
        @Override
        public Point move(Point from) {
            return new Point(Math.floor(from.x() - 0.5d), from.y());
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

    public static List<Direction> connectedDirectionsFromChar(char c) {
        return switch (c) {
            case '-' -> Arrays.asList(EAST, WEST);
            case '|' -> Arrays.asList(NORTH, SOUTH);
            case 'L' -> Arrays.asList(NORTH, EAST);
            case 'J' -> Arrays.asList(NORTH, WEST);
            case '7' -> Arrays.asList(WEST, SOUTH);
            case 'F' -> Arrays.asList(SOUTH, EAST);
            case '.' -> List.of();
            case 'S' -> Arrays.asList(values());
            default -> throw new IllegalArgumentException();
        };
    }

    public static String inputCharToDebugChar(char c) {
        return switch (c) {
            case '-' -> "═";
            case '|' -> "║";
            case 'F' -> "╔";
            case 'L' -> "╚";
            case '7' -> "╗";
            case 'J' -> "╝";
            case 'S' -> "¤";
            default -> throw new IllegalArgumentException();
        };
    }

    public abstract Point move(Point from);

    public abstract Direction reversed();
    public abstract Direction left();
    public abstract Direction right();
}
