import mf.map.Direction;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Mirror {
    BACKSLASH('\\') {
        @Override
        public Stream<Direction> reflect(Direction dir) {
            return Stream.of(
                    switch (dir) {
                        case NORTH -> Direction.WEST;
                        case SOUTH -> Direction.EAST;
                        case EAST -> Direction.SOUTH;
                        case WEST -> Direction.NORTH;
                    }
            );
        }
    },
    SLASH('/') {
        @Override
        public Stream<Direction> reflect(Direction dir) {
            return Stream.of(switch (dir) {
                case NORTH -> Direction.EAST;
                case SOUTH -> Direction.WEST;
                case EAST -> Direction.NORTH;
                case WEST -> Direction.SOUTH;
            });
        }
    },
    PIPE('|') {
        @Override
        public Stream<Direction> reflect(Direction dir) {
            return switch (dir) {
                case NORTH -> Stream.of(Direction.NORTH);
                case SOUTH -> Stream.of(Direction.SOUTH);
                case EAST, WEST -> Stream.of(Direction.NORTH, Direction.SOUTH);
            };
        }
    },
    DASH('-') {
        @Override
        public Stream<Direction> reflect(Direction dir) {
            return switch (dir) {
                case NORTH, SOUTH -> Stream.of(Direction.WEST, Direction.EAST);
                case EAST -> Stream.of(Direction.EAST);
                case WEST -> Stream.of(Direction.WEST);
            };
        }
    };

    private final char c;

    Mirror(char c) {
        this.c = c;
    }

    public static Mirror fromChar(char c) {
        return Arrays.stream(values())
                .filter(m -> m.c == c)
                .findFirst()
                .orElse(null);
    }

    public abstract Stream<Direction> reflect(Direction dir);

    public char getChar() {
        return c;
    }
}
