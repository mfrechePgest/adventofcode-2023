public record Point(int x, int y) {
    public boolean isValid(int mapWidth, int mapHeight) {
        return x >= 0 && y >= 0 && x < mapWidth && y < mapHeight;
    }
}
