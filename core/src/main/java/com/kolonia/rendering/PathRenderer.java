package com.kolonia.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.kolonia.pathfinding.Node;

import java.util.List;

import static com.kolonia.rendering.WorldRenderer.TILE_SIZE;

public class PathRenderer {
    private final ShapeRenderer shapeRenderer;

    public PathRenderer() {
        this.shapeRenderer = new ShapeRenderer();
    }

    public void render(List<Node> path, int pathIndex, Matrix4 projectionMatrix) {
        // Jeśli agent nie ma ścieżki lub już ją przeszedł, nie rysujemy nic
        if (path == null || path.isEmpty() || pathIndex >= path.size()) return;

        shapeRenderer.setProjectionMatrix(projectionMatrix);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK); // Kolor ścieżki

        // Rysujemy linie łączące kolejne węzły w ścieżce
        // Zaczynamy od 'pathIndex', czyli od kafelka, do którego agent AKTUALNIE idzie
        for (int i = pathIndex; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);

            // Przeliczamy pozycję kafelkową na piksele (środek kafelka)
            float x1 = current.getX() * TILE_SIZE + (TILE_SIZE / 2f);
            float y1 = current.getY() * TILE_SIZE + (TILE_SIZE / 2f);
            float x2 = next.getX() * TILE_SIZE + (TILE_SIZE / 2f);
            float y2 = next.getY() * TILE_SIZE + (TILE_SIZE / 2f);

            shapeRenderer.line(x1, y1, x2, y2);
        }

        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
