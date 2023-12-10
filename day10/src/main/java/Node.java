import java.util.Objects;
import java.util.stream.Stream;

public record Node<U extends AbstractCoord>(Node<U> parent, U content, long dist) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<U> node = (Node<U>) o;
        return Objects.equals(content.getCoord(), node.content.getCoord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content.getCoord());
    }

    public Stream<Node<U>> recurseToStart() {
        if( parent != null ) {
            return Stream.concat(Stream.of(this), parent.recurseToStart());
        } else {
            return Stream.of(this);
        }
    }

    public String toString() {
        return content.toString() + " " + "dist = " + dist;
    }
}
