package com.kolonia.world;

public enum TileType {

    GRASS(true),
    WATER(false),
    ROCK(false),
    FOREST(false),
    WALL(false);

    private final boolean walkable;

    TileType(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isWalkable() { return walkable; }

    public static TileType fromId(int id) {
        return switch (id) {
            case 1 -> WATER;
            case 2 -> ROCK;
            case 3 -> FOREST;
            case 4 -> WALL;
            default -> GRASS;
        };
    }
}
