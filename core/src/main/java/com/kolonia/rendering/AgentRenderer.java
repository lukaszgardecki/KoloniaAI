package com.kolonia.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kolonia.agents.Agent;

class AgentRenderer {
    private final SpriteBatch batch;
    private final Texture agentTexture;
    public static final int SIZE = 16;

    public AgentRenderer(SpriteBatch batch) {
        this.batch = batch;
        agentTexture = new Texture("agent.png");
    }

    public void render(Agent agent) {
        batch.draw(agentTexture, agent.getX() * SIZE, agent.getY() * SIZE);
    }

}
