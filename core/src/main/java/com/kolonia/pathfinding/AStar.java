package com.kolonia.pathfinding;

import java.util.*;

public class AStar {
    private final GridGraph graph;

    public AStar(GridGraph graph) {
        this.graph = graph;
    }

    public List<Node> findPath(int startX, int startY, int endX, int endY) {
        PriorityQueue<Node> open = new PriorityQueue<>();
        Map<String, Node> allNodes = new HashMap<>();

        Node start = getNode(allNodes, startX, startY);
        Node end = getNode(allNodes, endX, endY);

        start.setGCost(0);
        start.setHCost(heuristic(start, end));

        open.add(start);

        Set<String> closed = new HashSet<>();

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.getX() == end.getX() && current.getY() == end.getY()) {
                return buildPath(current);
            }

            closed.add(key(current));

            for (Node neighbor : graph.getNeighbors(current)) {
                if (closed.contains(key(neighbor))) continue;

                Node realNeighbor = getNode(allNodes, neighbor.getX(), neighbor.getY());
                float tentativeG = current.getgCost() + 1;

                if (tentativeG < realNeighbor.getgCost() || realNeighbor.getgCost() == 0) {
                    realNeighbor.setParent(current);
                    realNeighbor.setGCost(tentativeG);
                    realNeighbor.setHCost(heuristic(realNeighbor, end));

                    if (!open.contains(realNeighbor)) {
                        open.add(realNeighbor);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private float heuristic(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private List<Node> buildPath(Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;

        while (current != null) {
            path.add(current);
            current = current.getParent();
        }

        Collections.reverse(path);
        return path;
    }

    private Node getNode(Map<String, Node> map, int x, int y) {
        String k = x + "," + y;
        return map.computeIfAbsent(k, key -> new Node(x, y));
    }

    private String key(Node node) {
        return node.getX() + "," + node.getY();
    }
}
