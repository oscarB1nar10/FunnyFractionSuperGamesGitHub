package com.funnyfractions.game.archery_game.fruits;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.codeandweb.physicseditor.PhysicsShapeCache;

public abstract class Fruit {


    abstract Body createBody(String fruitName, float xPosition, float yPosition, int degrees, PhysicsShapeCache fruitBodies, World world);

}
