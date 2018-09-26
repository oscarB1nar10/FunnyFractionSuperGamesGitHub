package com.funnyfractions.game.archery_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.funnyfractions.game.archery_game.Actors.CrossBow;
import com.funnyfractions.game.archery_game.tools.Box2D_WorldCreator;
import com.funnyfractions.game.archery_game.tools.OperationImage;

import java.util.HashMap;
import java.util.Random;

public class PlayScreen implements Screen {
    private FirstScreen fs;

    //_-------------------------------CameraAndrProperties
    /*
        camera: Represent a camera pointing orthograpphicment to the map.
        viewport:Represent the camera dimentions.
     */
    private OrthographicCamera camera;
    private Viewport viewport;

    //------------------------MapAndProperties
    /*
        mapLoader: Represent a map that we will use in our world from a file.
        map: Is the map obtained through a specific file.
        tmr: alow us to render the map through OthogonalTiledMapRenderer.
     */

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private TiledMapRenderer tmr;

    //------------------------Box2D
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private Body fruit[]=new Body[3];
    private Body fb;

    //------------------------TextureAtlas
    private TextureAtlas crossbowAtlas;
    private TextureAtlas fruitsAtlas;

    //
    OperationImage oi;
    //
    private CrossBow crossBow;
    //-----------------------physicsShapeCache

    PhysicsShapeCache fruitBodies;
    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();



    public PlayScreen(FirstScreen firstScreen) {
        this.fs=firstScreen;

        crossbowAtlas=new TextureAtlas(Gdx.files.internal("crossbow.atlas"));
        fruitsAtlas=new TextureAtlas(Gdx.files.internal("sprite.txt"));

        camera=new OrthographicCamera();

        viewport=new StretchViewport(FirstScreen.V_WIDTH/FirstScreen.PPM,FirstScreen.V_WIDTH/FirstScreen.PPM,camera);
        //focus the camera to the specific point w/2, h/2.
        camera.setToOrtho(false,FirstScreen.V_WIDTH/FirstScreen.PPM,FirstScreen.V_WIDTH/FirstScreen.PPM);

        oi=new OperationImage();


        //---------------------------Map
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("data/ArcheryTileMap.tmx");
        tmr=new OrthogonalTiledMapRenderer(map);

        //------------------WorldAndProperties
        Box2D.init();
        addSprites();

        world=new World(new Vector2(0,-500),true);

        //Our ground (world basic structure)-{
        Box2D_WorldCreator box2D_worldCreator=new Box2D_WorldCreator(world,map);//}
        //Create a crossBow body
        crossBow=new CrossBow(world, this);
        //create a fruit bodies
        fruitBodies=new PhysicsShapeCache("fruits_physics.xml");

        generateFruits();



        box2DDebugRenderer=new Box2DDebugRenderer();



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //assing the camera to tmr

        tmr.setView(camera);
        tmr.render();

        box2DDebugRenderer.render(world,camera.combined);

        //here we specify the recpective  projectionMatriz to our batch.
        fs.batch.setProjectionMatrix(camera.combined);

        fs.batch.begin();
        crossBow.draw(fs.batch);//here we draw the crossbow sprite.

       for(int i=0; i<fruit.length; i++) {
           float degree = (float) Math.toDegrees(fruit[0].getAngle());
           drawSprite("banana", fruit[0].getPosition().x, fruit[0].getPosition().y, degree);
       }

        oi.draw(fs.batch);
        fs.batch.end();


    }

    private void update(float delta) {
        world.step(Gdx.graphics.getDeltaTime(),6,2);
        camera.update();
        crossBow.update(delta);//here we callback the rexpective method to update the  sprite position.
        //tmr.setView(camera);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        crossbowAtlas.dispose();
        fruitsAtlas.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();

    }
    private Body createBodies(String name, float x, float y, float degrees){

        fb=fruitBodies.createBody(name,world,1,1);
        fb.setTransform(x,y,degrees);

        return fb;
    }

    private void addSprites(){
        Array<TextureAtlas.AtlasRegion> regions=fruitsAtlas.getRegions();

        for (TextureAtlas.AtlasRegion region: regions) {
            Sprite sprite=fruitsAtlas.createSprite(region.name);

            float width=sprite.getWidth();
            float height=sprite.getHeight();

            sprite.setSize(width,height);
            sprite.setOrigin(0,0);
            sprite.setRotation(0);

            sprites.put(region.name,sprite);
        }
    }

    private void drawSprite( String  name, float x, float y,float degrees){
        Sprite sprite=sprites.get(name);
        sprite.setPosition(x,y);
        sprite.setRotation(degrees);
        sprite.setOrigin(0,0);

        sprite.draw(fs.batch);

    }

    private void generateFruits(){
        fruit[0]=createBodies("banana",100000/FirstScreen.PPM,100000/FirstScreen.PPM,0);
    }

    public TextureAtlas getAtlas(){
        return crossbowAtlas;
    }
}
