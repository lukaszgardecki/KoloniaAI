package com.kolonia.pathfinding;

public class Node implements Comparable<Node> {
    private final int x;
    private final int y;

    private float gCost;
    private float hCost;
    private Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getFCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Node node) {
        return Float.compare(this.getFCost(), node.getFCost());
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public float getgCost() { return gCost; }
    public void setGCost(float gCost) { this.gCost = gCost; }

    public float gethCost() { return hCost; }
    public void setHCost(float hCost) { this.hCost = hCost; }

    public Node getParent() { return parent; }
    public void setParent(Node parent) { this.parent = parent; }
}
