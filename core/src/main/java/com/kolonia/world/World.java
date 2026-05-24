package com.kolonia.world;

import com.badlogic.gdx.math.Vector2;
import com.kolonia.agents.Agent;
import com.kolonia.pathfinding.AStar;
import com.kolonia.pathfinding.GridGraph;
import com.kolonia.pathfinding.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class World {
    private Tile[][] tiles;
    private final List<Agent> agents = new ArrayList<>();
    private AStar aStar;
    private GridGraph graph;

    private final Set<String> occupied = new HashSet<>();


    public World() {
        loadFromFile("maps/map1.txt");

        graph = new GridGraph(this);
        aStar = new AStar(graph);

        for (int i = 0; i < 20; i++) {
            Vector2 pos = getRandomFreeGrassTile();
            agents.add(new Agent(pos.x, pos.y, getWidth(), getHeight()));
        }


    }

    public void update(float delta) {
        for  (Agent agent : agents) {
            agent.update(delta, agents);

            if (agent.isPathFinished()) {
                int tx = (int)(Math.random() * getWidth());
                int ty = (int)(Math.random() * getHeight());
                List<Node> newPath = aStar.findPath((int)agent.getX(), (int)agent.getY(), tx, ty);
                agent.setPath(newPath);
            }
        }
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }

    public int getWidth() { return tiles.length; }
    public int getHeight() { return tiles[0].length; }

    private void loadFromFile(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("assets/" + path));
            int height = lines.size();
            int width = lines.get(0).split(" ").length;

            tiles = new Tile[width][height];
            for (int y = 0; y < height; y++) {
                String[] parts = lines.get(y).split(" ");

                for (int x = 0; x < width; x++) {
                    int id = Integer.parseInt(parts[x]);
                    TileType type = TileType.fromId(id);
                    tiles[x][height - 1 - y] = new Tile(x, height - 1 - y, type);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Błąd wczytywania mapy: " + path, e);
        }
    }

    private Vector2 getRandomFreeGrassTile() {
        Random random = new Random();

        int height = tiles.length;          // liczba wierszy (Y)
        int width  = tiles[0].length;       // liczba kolumn (X)

        while (true) {
            int x = random.nextInt(width);   // 0 .. width-1
            int y = random.nextInt(height);  // 0 .. height-1

            if (!inBounds(x, y)) continue;
            if (tiles[x][y].getType() != TileType.GRASS) continue;

            String key = x + "_" + y;
            if (occupied.contains(key))
                continue;

            occupied.add(key);
            return new Vector2(x, y);
        }
    }



}
