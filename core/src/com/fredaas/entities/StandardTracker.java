package com.fredaas.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.fredaas.states.PlayState;

public class StandardTracker extends SpaceObject {
    
    public StandardTracker(float x, float y) {
        this.x = x;
        this.y = y;
        init();
    }
    
    @Override
    public void init() {
        xSpeed = MathUtils.random(0.5f, 1);
        ySpeed = MathUtils.random(0.5f, 1);
        radius = 10;
        numPoints = 3;
        radOffset = 2 * PI / numPoints;
        posx = new float[numPoints];
        posy = new float[numPoints];
        
        for (int i = 0; i < numPoints; i++) {
            posx[i] = x + MathUtils.cos(rad) * radius;
            posy[i] = y + MathUtils.sin(rad) * radius;
            rad += radOffset;
        }
    }
    
    private void setDirection() {
        for (int i = 0; i < numPoints; i++) {
            posx[i] = x + MathUtils.cos(rad) * radius;
            posy[i] = y + MathUtils.sin(rad) * radius;
            rad += radOffset;
        }
    }
    
    private void follow(float x, float y) {
        setAngle(x, y);
        
        dx = MathUtils.cos(rad) * (distance + 50) * xSpeed;
        dy = MathUtils.sin(rad) * (distance + 50) * ySpeed;
    }
    
    @Override
    public void update(float dt) {
        Player player = PlayState.player;
        ArrayList<Asteroid> asteroids = PlayState.asteroids;
        
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid a = asteroids.get(i);
            setGravity(a.getX(), a.getY(), 5, Force.REPEL, dt);
        }
        
        follow(player.getX(), player.getY());
        setDirection();
        
        x += dx * dt;
        y += dy * dt;
    }

    @Override
    public void draw(ShapeRenderer sr) {
        
        sr.begin(ShapeType.Line);
        sr.setColor(0, 1, 0, 1);
            for (int i = 0, j = numPoints - 1; i < numPoints; j = i++) {
                sr.line(posx[i], posy[i], posx[j], posy[j]);
            }
        sr.end();
    }
    
}
