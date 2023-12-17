import mf.map.Direction;
import mf.map.Point;

import java.util.*;
import java.util.stream.Stream;

public class Pipe extends AbstractCoord {

    private final List<Direction> connectedTo;
    private final String debugChar;

    public Pipe(Point p, char c) {
        super(p);
        connectedTo = Day10.connectedDirectionsFromChar(c);
        debugChar = Day10.inputCharToDebugChar(c);
    }

    Stream<Pipe> getNeighbours(Map<Point, Pipe> map) {
        return getNeighbours(map, true);
    }

    Stream<Pipe> getNeighbours(Map<Point, Pipe> map, boolean checkReverseConnection) {
        return connectedTo.stream()
                .map(d -> d.move(this.getCoord(), 1d))
                .map(map::get)
                .filter(Objects::nonNull)
                .filter(p -> !checkReverseConnection || p.getNeighbours(map, false)
                        .map(Pipe::getCoord)
                        .anyMatch(p2 -> p2.equals(this.getCoord()))
                );
    }

    public long findFarthestInLoop(Map<Point, Pipe> map, Map<Point, Ground> groundMap) {
        Queue<Node<Pipe>> queue = new PriorityQueue<>(Comparator.comparingLong(Node::dist));
        Set<Node<Pipe>> alreadySeen = new HashSet<>();
        queue.add(new Node<>(null, this, 0));
        Node<Pipe> current = null;
        while (!queue.isEmpty()) {
            current = queue.poll();
            alreadySeen.add(current);
            long currentDist = current.dist();
            Node<Pipe> finalCurrent = current;
            current.content()
                    .getNeighbours(map)
                    .filter(Objects::nonNull)
                    .map(pipe -> new Node<>(finalCurrent, pipe, currentDist + 1))
                    .filter(node -> !alreadySeen.contains(node))
                    .forEach(queue::add);
        }

        // Counting junk pipes as ground (useful for step2)
        Set<Point> loop = new HashSet<>();
        assert current != null;
        loop.add(current.content().getCoord());
        List<Node<Pipe>> listBranches = current.content()
                .getNeighbours(map)
                .map(p -> alreadySeen.stream().filter(n -> n.content().getCoord().equals(p.getCoord())).findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        for (Node<Pipe> branch : listBranches) {
            Node<Pipe> branch2 = branch;
            while (branch2 != null) {
                loop.add(branch2.content().getCoord());
                branch2 = branch2.parent();
            }
        }

        Iterator<Point> itPoints = map.keySet().iterator();
        while (itPoints.hasNext()) {
            Point pipePoint = itPoints.next();
            if (!loop.contains(pipePoint)) {
                itPoints.remove();
                groundMap.put(pipePoint, new Ground(pipePoint));
            }
        }

        return current.dist();
    }

    public String toString() {
        return getCoord().toString() + " connexions = " + connectedTo;
    }

    public List<Direction> getConnectedTo() {
        return connectedTo;
    }

    public String toDebugChar() {
        return debugChar;
    }
}
