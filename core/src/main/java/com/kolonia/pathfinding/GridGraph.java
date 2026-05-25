package com.kolonia.pathfinding;

import com.kolonia.world.Tile;
import com.kolonia.world.World;

import java.util.ArrayList;
import java.util.List;

public class GridGraph {
    private final World world;
    private final Node[][] nodes;

    public GridGraph(World world) {
        this.world = world;

        nodes = new Node[world.getWidth()][world.getHeight()];

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                nodes[x][y] = new Node(x, y);
            }
        }
    }

    public Node nodeAt(int x, int y) {
        if (!world.inBounds(x, y)) return null;
        return nodes[x][y];
    }

    public boolean isWalkable(int x, int y) {
        return world.inBounds(x, y) && world.getTile(x, y).isWalkable();
    }

    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();

        int x = node.getX();
        int y = node.getY();

        int[][] dirs = {
            { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 },   // ortho
            { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 }  // diagonal
        };

        for (int[] d : dirs) {
            int nx = x + d[0];
            int ny = y + d[1];

            if (!isWalkable(nx, ny)) continue;

            Node n = nodeAt(nx, ny);
            if (n == null) continue;

            // koszt ruchu
            float cost = (d[0] != 0 && d[1] != 0) ? 1.414f : 1f;
            n.setGCost(cost);

            neighbors.add(n);
        }

        return neighbors;
    }
}
