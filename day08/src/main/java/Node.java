import java.util.Map;
import java.util.Objects;

public final class Node {
    private final String label;
    private final String left;
    private final String right;

    public Node(String label, String left, String right) {
        this.label = label;
        this.left = left;
        this.right = right;
    }

    private Node leftNode = null;
    private Node rightNode = null;

    public Node getLeftNode(Map<String, Node> nodeMap) {
        if (leftNode == null) {
            leftNode = nodeMap.get(left);
        }
        return leftNode;
    }

    public Node getRightNode(Map<String, Node> nodeMap) {
        if (rightNode == null) {
            rightNode = nodeMap.get(right);
        }
        return rightNode;
    }

    public String label() {
        return label;
    }

    public String left() {
        return left;
    }

    public String right() {
        return right;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Node) obj;
        return Objects.equals(this.label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return "Node[" +
                "label=" + label + ", " +
                "left=" + left + ", " +
                "right=" + right + ']';
    }

    public Node getNeighbour(Direction dir, Map<String,Node> nodeMap) {
        return switch (dir) {
            case LEFT -> getLeftNode(nodeMap);
            case RIGHT -> getRightNode(nodeMap);
        };
    }
}
