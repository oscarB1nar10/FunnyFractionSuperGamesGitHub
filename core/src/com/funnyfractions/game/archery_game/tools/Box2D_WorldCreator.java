package com.funnyfractions.game.archery_game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2D_WorldCreator {

    public Box2D_WorldCreator(World world, TiledMap map){



        BodyDef bodyDef=new BodyDef();
        PolygonShape polygon=new PolygonShape();
        FixtureDef fixture=new FixtureDef();
        Body body;


        /*
            here, we obtain all diferents objects belong to the four layer whit rectangle shape.
         */

        Box2D.init();

        for(MapObject mapObject: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect=((RectangleMapObject)mapObject).getRectangle();

            // body will be Static type
            bodyDef.type=BodyDef.BodyType.StaticBody;
            //its position is in the center
            bodyDef.position.set((rect.getX()+rect.width/2),(rect.getY()+rect.height/2));
            body=world.createBody(bodyDef);
            //the shape of our polygon
            polygon.setAsBox((rect.getWidth()/2),(rect.getHeight()/2));//?
            fixture.shape=polygon;
            body.createFixture(fixture);


        }

        polygon.dispose();
    }
}
