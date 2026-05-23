package com.kolonia.world;

public class Tile {
    private final int x;
    private final int y;
    private final TileType type;

    Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileType getType() {
        return type;
    }
}
