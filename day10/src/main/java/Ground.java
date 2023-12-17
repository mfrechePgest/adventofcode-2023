import mf.map.Direction;
import mf.map.Point;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class Ground extends AbstractCoord {

    private Boolean insideLoop;
    private boolean fake;

    public Ground(Point p) {
        super(p);
        insideLoop = null;
        fake = false;
    }

    public Ground(Point p, boolean fake) {
        this(p);
        this.fake = fake;
    }

    public Boolean isInsideLoop() {
        return insideLoop;
    }

    public Boolean computeInsideLoop(Map<Point, Ground> ground,
                                     Map<Point, Pipe> pipeMap,
                                     int maxX, int maxY) {
        Map<Point, Ground> groundMapCopy = new HashMap<>(ground);
        if (insideLoop == null) {
            setInsideLoopWithNeighbours(groundMapCopy, pipeMap, maxX, maxY);
        }
        return isInsideLoop();
    }

    private void setInsideLoopWithNeighbours(Map<Point, Ground> ground,
                                             Map<Point, Pipe> pipeMap,
                                             int maxX, int maxY) {
        Queue<Node<Ground>> queue = new LinkedList<>();
        Set<Node<Ground>> alreadySeen = new HashSet<>();
        queue.add(new Node<>(null, this, 0));
        Node<Ground> current;
        Boolean initialIsInLoop = computeInsideLoop(maxX, maxY);
        AtomicBoolean insideLoop = new AtomicBoolean(initialIsInLoop == null || initialIsInLoop);
        while (!queue.isEmpty()) {
            current = queue.poll();
            if (current != null) {
                boolean shouldProcess = alreadySeen.add(current);
                if (shouldProcess) {
                    Node<Ground> finalCurrent = current;
//                    System.out.print("Processing... " + alreadySeen.size() + " seen , " + queue.size() + " in queue\r");
                    current.content()
                            .streamNeighbours(ground, pipeMap, maxX, maxY)
                            .filter(Objects::nonNull)
                            .map(pipe -> new Node<>(finalCurrent, pipe, 0))
                            .filter(node -> !alreadySeen.contains(node))
                            .peek(node -> {
                                if (node.content().computeInsideLoop(maxX, maxY) != null) {
                                    insideLoop.set(node.content().computeInsideLoop(maxX, maxY));
                                }
                            })
                            .forEach(queue::add);
                }
            }
        }
        alreadySeen.forEach(g -> g.content().insideLoop = insideLoop.get());
        System.out.println();
    }

    private Stream<Ground> streamNeighbours(Map<Point, Ground> ground, Map<Point, Pipe> pipeMap,
                                            int maxX, int maxY) {
        Stream<Ground> stream = Arrays.stream(Direction.values())
                .flatMap(d -> { // getting all pipes neighbours...
                    return getFakeGroundFuite(ground, pipeMap, d);
                })
                .filter(g -> !ground.containsKey(g.getCoord()))
                .filter(g -> Math.abs(g.getCoord().x() - this.getCoord().x()) + Math.abs(g.getCoord().y() - this.getCoord().y()) <= 1.5d)
                .filter(g -> g.getCoord().x() >= 0 && g.getCoord().y() >= 0 && Math.ceil(g.getCoord().x()) < maxX && Math.ceil(g.getCoord().y()) < maxY)
                .peek(g -> ground.put(g.getCoord(), g));
        if (insideLoop == null) {
            stream = Stream.concat(
                    Arrays.stream(Direction.values())
                            .map(d -> d.move(getCoord(), 0.5d).floor())
                            .map(ground::get)
                    ,
                    stream
            );
        }
        return stream
                .filter(Objects::nonNull)
                .filter(g -> g.insideLoop == null);
    }

    private Stream<Ground> getFakeGroundFuite(Map<Point, Ground> ground, Map<Point, Pipe> pipeMap, Direction d) {
        Point dest = d.move(getCoord(), 0.5d).floor();
        Pipe pipe = pipeMap.get(dest);
        if (pipe != null && pipe.getConnectedTo().size() < 4 &&
                (pipe.getConnectedTo().contains(d) ||
                        pipe.getConnectedTo().contains(d.reversed()))
                && !ground.containsKey(pipe.getCoord())) {
            List<Direction> connections = pipe.getConnectedTo();
            if (connections.contains(d.left())) {
                return Stream.of(
                        switch (d) {
                            case NORTH -> new Ground(Point.of(pipe.getCoord().x() + 0.5d, pipe.getCoord().y()), true);
                            case SOUTH -> new Ground(Point.of(pipe.getCoord().x() - 0.5d, pipe.getCoord().y()), true);
                            case EAST -> new Ground(Point.of(pipe.getCoord().x(), pipe.getCoord().y() + 0.5d), true);
                            case WEST -> new Ground(Point.of(pipe.getCoord().x(), pipe.getCoord().y() - 0.5d), true);
                        });
            } else if (connections.contains(d.right())) {
                return Stream.of(
                        switch (d) {
                            case NORTH -> new Ground(Point.of(pipe.getCoord().x() - 0.5d, pipe.getCoord().y()), true);
                            case SOUTH -> new Ground(Point.of(pipe.getCoord().x() + 0.5d, pipe.getCoord().y()), true);
                            case EAST -> new Ground(Point.of(pipe.getCoord().x(), pipe.getCoord().y() - 0.5d), true);
                            case WEST -> new Ground(Point.of(pipe.getCoord().x(), pipe.getCoord().y() + 0.5d), true);
                        });
            } else {
                return switch (d) {
                    case NORTH, SOUTH ->
                            Stream.of(new Ground(Point.of(pipe.getCoord().x() + 0.5d, pipe.getCoord().y()), true),
                                    new Ground(Point.of(pipe.getCoord().x() - 0.5d, pipe.getCoord().y()), true));
                    case EAST, WEST ->
                            Stream.of(new Ground(Point.of(pipe.getCoord().x(), pipe.getCoord().y() + 0.5d), true)
                                    , new Ground(Point.of(pipe.getCoord().x(), pipe.getCoord().y() - 0.5d), true));
                };
            }
        } else {
            return Stream.empty();
        }
    }

    private Boolean computeInsideLoop(int maxX, int maxY) {
        Boolean result;
        if (getCoord().x() == 0 || getCoord().y() == 0
                || getCoord().x() == maxX - 1
                || getCoord().y() == maxY - 1) {
            result = false;
        } else {
            // On ne sait pas encore
            result = null;
        }
        return result;
    }

    public String toString() {
        return (fake ? "FAKE" : "") + "GROUND : " + getCoord();
    }

}
