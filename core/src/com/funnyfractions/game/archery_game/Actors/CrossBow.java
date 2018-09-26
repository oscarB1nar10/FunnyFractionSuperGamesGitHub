package com.funnyfractions.game.archery_game.Actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.funnyfractions.game.archery_game.FirstScreen;
import com.funnyfractions.game.archery_game.PlayScreen;

public class CrossBow extends Sprite {

    private World world;
    private Body body;
    public TextureRegion arcStand;


        public CrossBow(World world, PlayScreen playScreen){
            // we callback to one sprite constructor an pass as parameter Atlas.region.nameRegion
            super(playScreen.getAtlas().findRegion("arc2"));
            //the world where Crossbow belong
            this.world=world;
            defineCrossBow();


            //Here we difine the respective TextureRegion for the last sprite.getTexture. The (x and y) coordinate change respectively to the Atlas.
            arcStand=new TextureRegion(this.getTexture(),128,0,128,128);
            //Here  we set the bounds to the sprite.
            setBounds(60000/FirstScreen.PPM, 85000/FirstScreen.PPM, 12800/FirstScreen.PPM, 12800/FirstScreen.PPM);
            //and here we specify the recpective region from the texture.
            setRegion(arcStand);
        }

        /*
        This method allow us to set the sprite position.
         */
        public void update(float dt){
            setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);

        }

        /*
            Body Structure from our Crossbow.
            position, type, shape
         */
    private void defineCrossBow() {
        BodyDef bodyDef=new BodyDef();
        bodyDef.position.set(60000/FirstScreen.PPM,85000/FirstScreen.PPM);
        bodyDef.type=BodyDef.BodyType.DynamicBody;

        body=world.createBody(bodyDef);

        FixtureDef fdef=new FixtureDef();
        CircleShape circle=new CircleShape();
        circle.setRadius(12800/FirstScreen.PPM);

        fdef.shape=circle;
        body.createFixture(fdef);

        circle.dispose();
    }
}
