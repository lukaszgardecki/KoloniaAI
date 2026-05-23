package com.kolonia.agents;

import com.badlogic.gdx.math.Vector2;
import com.kolonia.pathfinding.Node;

import java.util.ArrayList;
import java.util.List;

public class Agent {
    private float x;
    private float y;

    private float targetX;
    private float targetY;

    private Vector2 velocity = new Vector2();
    private float speed = 5f;
    private float turnRate = 8f;
    private List<Node> path = new ArrayList<>();
    private int pathIndex = 0;

    private float separationRadius = 1.0f;   // zasięg „czucia” innych
    private float separationForce = 10.0f;   // siła odpychania


    public Agent(float startX, float startY, int maxX, int maxY) {
        this.x = startX;
        this.y = startY;
        setRandomTarget(maxX, maxY);
    }

//    public void update(float delta, List<Agent> allAgents) {
//        if (isPathFinished()) return;
//
//        Node targetNode = path.get(pathIndex);
//        Vector2 target = new Vector2(targetNode.getX(), targetNode.getY());
//
//        Vector2 desired = target.cpy().sub(x, y);
//        float dist = desired.len();
//
//        if (dist < 0.2f) {
//            pathIndex++;
//            return;
//        }
//
//        desired.nor().scl(speed);
//
//        Vector2 steering = desired.sub(velocity).scl(turnRate * delta);
//        velocity.add(steering);
//
//        x += velocity.x * delta;
//        y += velocity.y * delta;
//    }

    public void update(float delta, List<Agent> allAgents) {
        if (path == null || pathIndex >= path.size()) return;

        Node targetNode = path.get(pathIndex);
        Vector2 target = new Vector2(targetNode.getX(), targetNode.getY());

        Vector2 desired = target.sub(x, y);
        float dist = desired.len();

        if (dist < 0.2f) {
            pathIndex++;
            return;
        }

        desired.nor().scl(speed);

        // steering do celu
        Vector2 steering = desired.sub(velocity).scl(turnRate * delta);

        // 🔥 dodajemy separację od innych agentów
        Vector2 sep = computeSeparation(allAgents);

        steering.add(sep);
        velocity.add(steering);

        x += velocity.x * delta;
        y += velocity.y * delta;
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

    public void setTarget(float targetX, float targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setPath(List<Node> path) {
        this.path = path;
        this.pathIndex = 0;
    }

    public void setRandomTarget(int maxX, int maxY) {
        this.targetX = (int)(Math.random() * maxX);
        this.targetY = (int)(Math.random() * maxY);
    }

    public boolean isPathFinished() {
        return path == null || pathIndex >= path.size();
    }

//    private Vector2 computeSeparation(List<Agent> allAgents) {
//        Vector2 force = new Vector2();
//        int count = 0;
//
//        for (Agent other : allAgents) {
//            if (other == this) continue;
//
//            float dx = other.x - this.x;
//            float dy = other.y - this.y;
//            float dist2 = dx*dx + dy*dy;
//
//            if (dist2 < separationRadius * separationRadius && dist2 > 0.0001f) {
//                float dist = (float)Math.sqrt(dist2);
//                // wektor OD drugiego agenta
//                force.x += (this.x - other.x) / dist;
//                force.y += (this.y - other.y) / dist;
//                count++;
//            }
//        }
//
//        if (count > 0) {
//            force.scl(1f / count);      // uśrednienie
//            force.nor().scl(separationForce);
//        }
//
//        return force;
//    }

    private Vector2 computeSeparation(List<Agent> allAgents) {
        Vector2 force = new Vector2();

        for (Agent other : allAgents) {
            if (other == this) continue;

            float dx = this.x - other.x;
            float dy = this.y - other.y;

            float dist2 = dx*dx + dy*dy;

            // jeśli są bardzo blisko → odpychaj MOCNO
            if (dist2 < 1f) {
                float dist = (float)Math.sqrt(dist2);
                if (dist < 0.0001f) dist = 0.0001f;

                force.x += dx / dist;
                force.y += dy / dist;
            }
        }

        return force.scl(20f); // SIŁA ODPYCHANIA
    }

}
