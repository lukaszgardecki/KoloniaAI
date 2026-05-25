package com.kolonia.agents;

import com.badlogic.gdx.math.Vector2;
import com.kolonia.pathfinding.GridGraph;
import com.kolonia.pathfinding.Node;
//import com.kolonia.pathfinding.Node;

import java.util.ArrayList;
import java.util.List;

public class Agent {
    private float x;
    private float y;

    private float targetX;
    private float targetY;

    private Vector2 velocity = new Vector2();
    private float speed = 3f;
    private float turnRate = 20f;
    private List<Node> path = new ArrayList<>();
    private int pathIndex = 0;
    private boolean extensionGenerated = false;
    private int pathSize = 0;
    private float separationRadius = 1.5f;   // zasięg „czucia” innych
    private float separationForce = 10.0f;   // siła odpychania


    public Agent(float startX, float startY, int maxX, int maxY) {
        this.x = startX;
        this.y = startY;
        setRandomTarget(maxX, maxY);
    }

    public void update(float delta, List<Agent> allAgents, GridGraph graph) {
        if (path == null || pathIndex >= path.size()) return;

        Node targetNode = path.get(pathIndex);
        Vector2 target = new Vector2(targetNode.getX(), targetNode.getY());

        Vector2 desired = target.sub(x, y);
        float dist = desired.len();

        if (dist < 0.2f) {
            if (!path.isEmpty()) {
                path.remove(0);
            }
            return;
        }

        desired.nor().scl(speed);
        Vector2 steering = desired.sub(velocity).scl(turnRate * delta);

        Vector2 sep = computeSeparation(allAgents, graph);
        steering.add(sep);

        velocity.add(steering);

        x += velocity.x * delta;
        y += velocity.y * delta;

        if (velocity.len() > speed) {
            velocity.nor().scl(speed);
        }
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getTargetX() {
        return targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public float getSpeed() {
        return speed;
    }

    public List<Node> getPath() {
        return path;
    }

    public float getSeparationRadius() {
        return separationRadius;
    }

    public boolean hasNoPath() {
        return path.isEmpty();
    }

    public boolean isInHalfPath() {
        return path.size() == pathSize / 2;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public void setExtensionGenerated(boolean state) {
        this.extensionGenerated = state;
    }

    public int getPathSize() {
        return path != null ? path.size() : 0;
    }

    public boolean isExtensionGenerated() {
        return extensionGenerated;
    }

    public void setTarget(float targetX, float targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setPath(List<Node> path) {
        this.path = path;
        this.extensionGenerated = false;
        this.pathSize = path.size();
    }

    public void setPathIndex(int pathIndex) {
        this.pathIndex = pathIndex;
    }

    public void setRandomTarget(int maxX, int maxY) {
        this.targetX = (int)(Math.random() * maxX);
        this.targetY = (int)(Math.random() * maxY);
    }

    public boolean isPathFinished() {
        return path == null || pathIndex >= path.size();
    }

    public void appendPath(List<Node> additionalPath) {
        if (additionalPath == null || additionalPath.isEmpty()) return;
        this.path.addAll(additionalPath);
        this.extensionGenerated = true;
        this.pathSize = path.size();
    }

    private Vector2 computeSeparation(List<Agent> allAgents) {
        Vector2 steering = new Vector2();
        int count = 0;

        for (Agent other : allAgents) {
            if (other == this) continue;

            float dx = this.x - other.x;
            float dy = this.y - other.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            if (dist < separationRadius && dist > 0) {
                Vector2 pushVec = new Vector2(dx, dy);
                pushVec.nor();

                float forceFactor = 1.0f - (dist / separationRadius);
                pushVec.scl(forceFactor);

                steering.add(pushVec);
                count++;
            }
        }

        if (count > 0) {
            steering.scl(1.0f / count);
            steering.nor().scl(separationForce);
        }

        return steering;
    }


    private Vector2 computeSeparation(List<Agent> allAgents, GridGraph graph) {
        Vector2 steering = new Vector2();
        int count = 0;

        for (Agent other : allAgents) {
            if (other == this) continue;

            float dx = this.x - other.x;
            float dy = this.y - other.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            if (dist < separationRadius && dist > 0) {
                Vector2 pushVec = new Vector2(dx, dy);
                pushVec.nor();

                float forceFactor = 1.0f - (dist / separationRadius);
                pushVec.scl(forceFactor);

                float targetX = this.x + pushVec.x;
                float targetY = this.y + pushVec.y;

                if (!graph.isWalkable((int)targetX, (int)this.y)) {
                    pushVec.x = 0;
                }
                if (!graph.isWalkable((int)this.x, (int)targetY)) {
                    pushVec.y = 0;
                }

                steering.add(pushVec);
                count++;
            }
        }

        if (count > 0) {
            steering.scl(1.0f / count);
            steering.nor().scl(separationForce);
        }

        return steering;
    }
}
