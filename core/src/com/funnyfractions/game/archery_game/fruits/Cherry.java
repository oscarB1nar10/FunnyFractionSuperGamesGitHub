package com.funnyfractions.game.archery_game.fruits;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.codeandweb.physicseditor.PhysicsShapeCache;

public class Cherry extends Fruit {

    @Override
    public Body createBody(String fruitName, float xPosition, float yPosition, int degrees, PhysicsShapeCache fruitBodies, World world) {

        Body fb;
        fb=fruitBodies.createBody(fruitName,world,1,1);
        fb.setTransform(xPosition,yPosition,degrees);

        Array<Fixture> fixturesArray = fb.getFixtureList();

        for(Fixture fixture: fixturesArray){
            fixture.setUserData("cherry");

        }
        return fb;
    }
}
