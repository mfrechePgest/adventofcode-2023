import java.util.ArrayList;
import java.util.List;

public record Point(int x, int y) {
    public List<Point> getAdjacent(boolean withDiagonals) {
        List<Point> result = new ArrayList<>();
        for (int i = x - 1 ; i <= x + 1 ; i++ ) {
            for (int j = y - 1 ; j <= y + 1 ; j++ ) {
                if (withDiagonals || i == x || j == y ) {
                    result.add(new Point(i, j));
                }
            }
        }
        return result;
    }

    public Point left() {
        return new Point(x - 1, y);
    }

    public Point right() {
        return new Point(x + 1, y);
    }
}
