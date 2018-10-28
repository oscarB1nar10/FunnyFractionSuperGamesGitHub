package com.funnyfractions.game.archery_game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.funnyfractions.game.archery_game.FirstScreen;

public class Dart extends Sprite {

    Body body;
    public  Dart(Texture texture, Body body){
        super(texture);
        this.body = body;

        setBounds(60000/ FirstScreen.PPM, 85000/FirstScreen.PPM, 12800/FirstScreen.PPM, 12800/FirstScreen.PPM);

    }

    public  void updatePosition(float dt){
        setPosition(body.getPosition().x, body.getPosition().y);
        setRotation( (float) Math.toDegrees(body.getAngle()));
        setOrigin(0,0);
    }

    public Vector2 getPosition(){
       return new Vector2( body.getPosition().x , body.getPosition().y);
    }

    public Body getDart(){
        return body;
    }


}
