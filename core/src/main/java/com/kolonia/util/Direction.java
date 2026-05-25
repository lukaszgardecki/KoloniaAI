package com.kolonia.util;

public enum Direction {
    // Kierunki ortogonalne (koszt 1.0)
    NORTH(0, 1, 1.0f),
    SOUTH(0, -1, 1.0f),
    EAST(1, 0, 1.0f),
    WEST(-1, 0, 1.0f),

    // Kierunki diagonalne (koszt ~1.414)
    NORTH_EAST(1, 1, 1.414f),
    NORTH_WEST(-1, 1, 1.414f),
    SOUTH_EAST(1, -1, 1.414f),
    SOUTH_WEST(-1, -1, 1.414f);

    private final int dx;
    private final int dy;
    private final float cost;

    Direction(int dx, int dy, float cost) {
        this.dx = dx;
        this.dy = dy;
        this.cost = cost;
    }

    public int getDx() { return dx; }
    public int getDy() { return dy; }
    public float getCost() { return cost; }
    public boolean isDiagonal() { return dx != 0 && dy != 0; }
    public boolean isOrthogonal() { return dx == 0 || dy == 0; }
}
