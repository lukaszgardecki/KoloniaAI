package com.kolonia.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kolonia.agents.Agent;

import java.util.HashMap;
import java.util.Map;

public class AgentRenderer {
    private final SpriteBatch batch;
//    private final Texture agentTexture;
    private final Map<Integer, Texture> textureCache;
    public static final int SIZE = 16;

    public static final int TEXTURE_SIZE = 24;
    public static final int TILE_SIZE = SIZE;

    public AgentRenderer(SpriteBatch batch) {
        this.batch = batch;
        this.textureCache = new HashMap<>();
    }

//    public AgentRenderer(SpriteBatch batch) {
//        this.batch = batch;
//
//        Pixmap pixmap = new Pixmap(SIZE, SIZE, Pixmap.Format.RGBA8888);
//        pixmap.setColor(Color.RED);
//        pixmap.fill();
//
//        this.agentTexture = new Texture(pixmap);
//
////        this.agentTexture = new Texture("agent.png");
//    }

//    public void render(Agent agent) {
//        batch.draw(agentTexture, agent.getX() * SIZE, agent.getY() * SIZE);
//    }

    private Texture getOrCreateTexture(int radiusInPixels) {
        if (textureCache.containsKey(radiusInPixels)) {
            return textureCache.get(radiusInPixels);
        }

        int textureSize = (radiusInPixels * 2) + 2;
        int center = textureSize / 2;

        Pixmap pixmap = new Pixmap(textureSize, textureSize, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.BLUE);
        pixmap.fillCircle(center, center, radiusInPixels);

        int squareSize = 12;
        int squareOffset = center - (squareSize / 2);

        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(squareOffset, squareOffset, squareSize, squareSize);

        Texture newTexture = new Texture("agent.png");
//        Texture newTexture = new Texture(pixmap);
        pixmap.dispose();

        textureCache.put(radiusInPixels, newTexture);
        return newTexture;
    }

    public void render(Agent agent) {
        int radiusInPixels = (int) agent.getSeparationRadius() * TILE_SIZE;

        Texture texture = getOrCreateTexture(radiusInPixels);

        int textureSize = texture.getWidth();
        float offset = (textureSize - TILE_SIZE) / 2f;

        float posX = (agent.getX() * TILE_SIZE) - offset;
        float posY = (agent.getY() * TILE_SIZE) - offset;

        batch.draw(texture, posX, posY);
    }
}
