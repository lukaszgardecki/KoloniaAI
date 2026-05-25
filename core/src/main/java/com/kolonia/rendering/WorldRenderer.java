package com.kolonia.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kolonia.agents.Agent;
import com.kolonia.world.Tile;
import com.kolonia.world.World;

public class WorldRenderer {
    private OrthographicCamera camera;
    private final SpriteBatch batch;
    private final World world;

    private final Texture grass;
    private final Texture water;
    private final Texture rock;
    private final Texture forest;
    private final Texture wall;
    private final AgentRenderer agentRenderer;
    private final PathRenderer pathRenderer;

    public static final int TILE_SIZE = 16;

    public WorldRenderer(World world, OrthographicCamera camera) {
        this. world = world;
        this.camera = camera;
        this.batch = new SpriteBatch();

        grass = new Texture("tiles/grass2.png");
        water = new Texture("tiles/water.png");
        rock = new Texture("tiles/rock.png");
        forest = new Texture("tiles/forest.png");
        wall = new Texture("tiles/wall.png");

        agentRenderer = new AgentRenderer(batch);
        pathRenderer = new PathRenderer();
    }

    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                Tile tile = world.getTile(x, y);

                Texture tex = switch (tile.getType()) {
                    case GRASS -> grass;
                    case WATER -> water;
                    case ROCK -> rock;
                    case FOREST -> forest;
                    case WALL -> wall;
                };

                batch.draw(tex, x * TILE_SIZE, y * TILE_SIZE);
            }
        }

        for (Agent a : world.getAgents()) {
            agentRenderer.render(a);
        }

        batch.end();



        for (Agent a : world.getAgents()) {
            pathRenderer.render(a.getPath(), a.getPathIndex(), camera.combined);
        }
    }
}
