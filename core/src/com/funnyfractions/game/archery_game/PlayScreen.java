package com.funnyfractions.game.archery_game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.funnyfractions.game.archery_game.Actors.CrossBow;
import com.funnyfractions.game.archery_game.Actors.Dart;
import com.funnyfractions.game.archery_game.fruits.Bannana;
import com.funnyfractions.game.archery_game.fruits.Cherry;
import com.funnyfractions.game.archery_game.fruits.Orange;
import com.funnyfractions.game.archery_game.tools.Box2D_WorldCreator;
import com.funnyfractions.game.archery_game.tools.InputProcessorsV2;


import java.util.ArrayList;
import java.util.HashMap;


public class PlayScreen  extends InputProcessorsV2 implements Screen , ApplicationListener {
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
    private Body fruit[] = new Body[3];
    private Body arcBody;

    //------------------------TextureAtlas
    private TextureAtlas crossbowAtlas;
    private TextureAtlas fruitsAtlas;

    //
    Sprite operationImage ;
    //
    private CrossBow crossBow;
    //-----------------------physicsShapeCache

    PhysicsShapeCache fruitBodies;
    PhysicsShapeCache dartBody;
    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    //----------FruitsName
    String[] fName = {"banana", "cherries", "orange"};

    //Timer
    long elapsedTime;
    long beginTime = System.nanoTime();
    boolean changeSprites = true;
    // response options
    ArrayList <Texture> questons;
    ArrayList <Texture> answers;
    Sprite[] resOp = new Sprite[3];
    //input processor
    boolean arcTouch = false;
     boolean shootinDart = false;
    //dart
    Dart dart;
    Body dartToDelete ;
    boolean isDartCollideWF = false;

    //shooting angle
    float xValue, yValue , xAxisY , yAxisY , xFinal , yFinal , angle=0, higerH=0;
    int actualTexture=0;
    boolean stretched=true;

    //Interface to use when we want to go Android
    private ActionResolver actionResolver;

    //decide is posible shot
    private boolean isPosibleShot = true;
    //sounds
    private Music mp3Sound ;
    private Sound archery_sound, collition;


    public PlayScreen(FirstScreen firstScreen, ActionResolver actionResolver) {
        this.fs = firstScreen;
        this.actionResolver = actionResolver;

        crossbowAtlas = new TextureAtlas(Gdx.files.internal("crossbow.atlas"));
        fruitsAtlas = new TextureAtlas(Gdx.files.internal("sprite.txt"));

        camera = new OrthographicCamera();

        viewport = new StretchViewport(FirstScreen.V_WIDTH / FirstScreen.PPM, FirstScreen.V_WIDTH / FirstScreen.PPM, camera);
        //focus the camera to the specific point w/2, h/2.
        camera.setToOrtho(false, FirstScreen.V_WIDTH / FirstScreen.PPM, FirstScreen.V_WIDTH / FirstScreen.PPM);

        //oi = new OperationImage();


        //---------------------------Map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("data/ArcheryTileMap.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        //---------------------------Sounds
        mp3Sound = Gdx.audio.newMusic(Gdx.files.internal("sounds/adventure_island.mp3"));
        mp3Sound.setVolume(0.2f);
        mp3Sound.setLooping(true);
        mp3Sound.play();

        archery_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/archery_sound.mp3"));
        collition = Gdx.audio.newSound(Gdx.files.internal("sounds/collition.mp3"));
        //----------------------Texture' array lists
        fillAnswers();
        fillQuestions();
        //----------------------Select a question and three posible answers
        questionAndThreeAnswers();
        //------------------WorldAndProperties
        Box2D.init();
        addSprites();

        world = new World(new Vector2(0, -60), true);
        captureContacts();
        //Our ground (world basic structure)-{
        Box2D_WorldCreator box2D_worldCreator = new Box2D_WorldCreator(world, map);//}
        //Create a crossBow body
        crossBow = new CrossBow(world, this);
        //the central arc point{
        //xValue=(float)((crossBow.getArcPosition().x*Gdx.graphics.getWidth())/FirstScreen.V_WIDTH)*100;
        xValue = ((CrossBow.CROSS_BOW_POSITION_X*Gdx.graphics.getWidth())/ FirstScreen.V_WIDTH)*100;
        yValue = (Gdx.graphics.getHeight()/3);
        xAxisY=xValue;
        //}

        //create a fruit bodies
        fruitBodies = new PhysicsShapeCache("fruits_physics.xml");
        //create a dart body
        dartBody = new PhysicsShapeCache("dart_physics.xml");

        generateFruits();
        //createResponseSprites();

        box2DDebugRenderer = new Box2DDebugRenderer();

        Gdx.input.setInputProcessor(this);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //assing the camera to tmr

        tmr.setView(camera);
        tmr.render();

        box2DDebugRenderer.render(world, camera.combined);
        //here we specify the recpective  projectionMatriz to our batch.
        fs.batch.setProjectionMatrix(camera.combined);


        fs.batch.begin();
        crossBow.draw(fs.batch);//here we draw the crossbow sprite.
        //here we check if  the dart exists
        if(dart != null) {

            if (dart.getPosition().x >= 1280) {
                isPosibleShot = true;
                dartToDelete = dart.getDart();
            }
        }
        //here we draw the dart sprite if it's posible to shot
        if(shootinDart) {
            dart.draw(fs.batch);
        }

        elapsedTime = (System.nanoTime() - beginTime) / 100000000;
        System.out.println("t: " + elapsedTime);

        if (changeSprites) {

            for (int i = 0; i < fruit.length; i++) {
                float degree = (float) Math.toDegrees(fruit[i].getAngle());
                drawSprite(fName[i], fruit[i].getPosition().x, fruit[i].getPosition().y, degree);

            }
        }


        if (elapsedTime >= 70 && elapsedTime <= 170) {

            System.out.println("inside to this place after x seconds");
            for (int i=0; i<resOp.length ; i++) {
                drawSprite(resOp[i], fruit[i].getPosition().x, fruit[i].getPosition().y , 0);
            }

            changeSprites = false;

        } else {
            changeSprites = true;
        }




        operationImage.draw(fs.batch);
        fs.batch.end();

        update(delta);
    }

    private void update(float delta) {
        world.step(1/100f, 6, 2);
        camera.update();
        crossBow.update(delta);//here we callback the rexpective method to update the  sprite position.
        if(isDartCollideWF) {
            world.destroyBody(dartToDelete);
            shootinDart = false;
            isDartCollideWF = false;

        }
        if(shootinDart) {
            dart.updatePosition(delta);
        }

    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

    }

    @Override
    public void render() {

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        System.out.println("Entre a este lugar");

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


    private void addSprites() {
        Array<TextureAtlas.AtlasRegion> regions = fruitsAtlas.getRegions();

        for (TextureAtlas.AtlasRegion region : regions) {
            Sprite sprite = fruitsAtlas.createSprite(region.name);

            float width = sprite.getWidth();
            float height = sprite.getHeight();

            sprite.setSize(width, height);
            sprite.setOrigin(0, 0);
            sprite.setRotation(0);

            sprites.put(region.name, sprite);
        }
    }

    private void drawSprite(String name, float x, float y, float degrees) {
        Sprite sprite = sprites.get(name);
        sprite.setPosition(x, y);
        sprite.setRotation(degrees);
        sprite.setAlpha(1);
        sprite.setOrigin(0, 0);

        sprite.draw(fs.batch);

    }

    private void drawSprite(Sprite sprite, float x, float y, float degrees) {
        // fs.batch.begin();

        sprite.setPosition(x, y);
        sprite.setRotation(degrees);
        sprite.setAlpha(1);
        sprite.setOrigin(0, 0);

        sprite.draw(fs.batch);
        //fs.batch.end();
    }

    private void generateFruits() {
        Bannana bannana = new Bannana();
        Cherry cherry = new Cherry();
        Orange orange = new Orange();

        fruit[0] = bannana.createBody("banana", 95200 / FirstScreen.PPM,
                85000 / FirstScreen.PPM, 0, fruitBodies, world);
        fruit[1] = cherry.createBody("cherries", 97000 / FirstScreen.PPM,
                150000 / FirstScreen.PPM, 0, fruitBodies, world);
        fruit[2] = orange.createBody("orange", 97000 / FirstScreen.PPM,
                60000 / FirstScreen.PPM, 0, fruitBodies, world);


       // arcSprite.setPosition(arcBody.getPosition().x, arcBody.getPosition().y);



    }

    /*public void createResponseSprites() {
        Texture t1 = new Texture(Gdx.files.internal("answers/answer1.png"));
        Texture t2 = new Texture(Gdx.files.internal("answers/answer1.png"));
        Texture t3 = new Texture(Gdx.files.internal("answers/answer1.png"));

        resOp[0] = new Sprite(t1);
        resOp[0].setPosition(500, 500);
        resOp[1] = new Sprite(t2);
        resOp[2] = new Sprite(t3);



    }*/

    public void captureContacts() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();

                if(fixtureA.getUserData().equals("dart") && (!fixtureB.getUserData().equals("floor")) ||
                        (!fixtureA.getUserData().equals("floor")) && fixtureB.getUserData().equals("dart")     ) {
                    isDartCollideWF = true;
                    collition.play();
                    if(fixtureA.getUserData().equals("dart")){
                        dartToDelete = fixtureA.getBody();

                    }else{
                        dartToDelete = fixtureB.getBody();
                    }

                    isPosibleShot = true;


                }else if(fixtureA.getUserData().equals("dart") && (fixtureB.getUserData().equals("floor")) ||
                        (fixtureA.getUserData().equals("floor")) && fixtureB.getUserData().equals("dart")){
                        isDartCollideWF = true;
                        collition.play();
                        if(fixtureA.getUserData().equals("dart")){
                            dartToDelete = fixtureA.getBody();
                            }else {
                                dartToDelete = fixtureB.getBody();
                        }
                        isPosibleShot = true;
                }else {
                        if(((fixtureA.getUserData().equals("banana") || fixtureA.getUserData().equals("cherry")
                            || fixtureA.getUserData().equals("orange")) && fixtureB.getUserData().equals("Main_floor")) ||
                                ((fixtureB.getUserData().equals("banana") || fixtureB.getUserData().equals("cherry")
                                        || fixtureB.getUserData().equals("orange")) && fixtureA.getUserData().equals("Main_floor")) ){

                            System.out.println("the fruit has collide whit the main floor");
                        }
                }





            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }


    public TextureAtlas getAtlas() {
        return crossbowAtlas;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        System.out.println("you touch the: (x= "+screenX+", y= "+screenY);
        System.out.println("central arc point: (x="+xValue+" , y="+yValue+")");
        xFinal=screenX ; yFinal=screenY;
        yAxisY=yFinal;

        calculateShootingAngle();

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("se ha lebantado el dedo");
        //arcBody.applyForceToCenter((10000)*9,0,true);


        System.out.println("angle: "+angle);

        if(Math.toDegrees(angle)>45) {

                if(isPosibleShot) {

                    arcBody = dartBody.createBody("arc",world,1,1);
                    Array<Fixture> fixturesArray = arcBody.getFixtureList();

                    for(Fixture fixture: fixturesArray){
                        fixture.setUserData("dart");

                    }

                    float differenceAngle = (angle - 45);
                    arcBody.setTransform((CrossBow.CROSS_BOW_POSITION_X + (differenceAngle + 80)),
                            (CrossBow.CROSS_BOW_POSITION_Y - 64), angle);

                    dart = new Dart(new Texture(Gdx.files.internal("arc.png")), arcBody);
                    shootinDart = true;
                    arcBody.setLinearVelocity(2500, 2500);
                    archery_sound.play();

                    //when the dart is shot the arc must contract
                    crossBow.changeTexture(0, 0, CrossBow.CROSS_BOW_POSITION_X, CrossBow.CROSS_BOW_POSITION_Y);
                    //restore the arc . The stretch distance, the initial texture {
                    higerH = 0;
                    actualTexture = 0;
                    stretched = true;
                    isPosibleShot = false;
                }

            //isPosibleShot = false;
            //}
            //actionResolver.goToAndroid();
        }else if(Math.toDegrees(angle)>= 0){

            if(isPosibleShot) {
                arcBody = dartBody.createBody("arc",world,1,1);
                Array<Fixture> fixturesArray = arcBody.getFixtureList();

                for(Fixture fixture: fixturesArray){
                    fixture.setUserData("dart");

                }

                arcBody.setTransform(CrossBow.CROSS_BOW_POSITION_X,
                        CrossBow.CROSS_BOW_POSITION_Y - 64, angle);

                dart = new Dart(new Texture(Gdx.files.internal("arc.png")), arcBody);
                shootinDart = true;
                arcBody.setLinearVelocity(2500, 500);
                archery_sound.play();
                //when the dart is shot the arc must contract
                crossBow.changeTexture(0, 0, CrossBow.CROSS_BOW_POSITION_X, CrossBow.CROSS_BOW_POSITION_Y);
                //restore the arc . The stretch distance, the initial texture {
                higerH = 0;
                actualTexture = 0;
                stretched = true;
                isPosibleShot = false;
            }

            //isPosibleShot = false;
            //}
        }else if((Math.toDegrees(angle))>-45){

            if(isPosibleShot) {

                arcBody = dartBody.createBody("arc",world,1,1);
                Array<Fixture> fixturesArray = arcBody.getFixtureList();

                for(Fixture fixture: fixturesArray){
                    fixture.setUserData("dart");

                }

                arcBody.setTransform(CrossBow.CROSS_BOW_POSITION_X - 75,
                        CrossBow.CROSS_BOW_POSITION_Y - 64, angle);
                dart = new Dart(new Texture(Gdx.files.internal("arc.png")), arcBody);
                shootinDart = true;
                arcBody.setLinearVelocity(3200, -1000);
                archery_sound.play();
                //when the dart is shot the arc must contract
                crossBow.changeTexture(0, 0, CrossBow.CROSS_BOW_POSITION_X, CrossBow.CROSS_BOW_POSITION_Y);
                //restore the arc . The stretch distance, the initial texture {
                higerH = 0;
                actualTexture = 0;
                stretched = true;
                isPosibleShot = false;
            }


            //isPosibleShot = false;
            //}
        }


        //fruit[0].applyForceToCenter(1000,10,true);

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("arc position: ( "+xValue+", "+yValue+")");
        System.out.println("you touch at: ( X = "+screenX+" , Y = "+screenY);
        System.out.println("the screen size: width= "+Gdx.graphics.getWidth()+" , "+Gdx.graphics.getHeight());
        return false;
    }

    public void calculateShootingAngle() {
        float a, b, c;

        if (xFinal < xValue && yFinal > yValue) {

            a = Math.abs(xFinal - xAxisY);
            b = Math.abs(yAxisY - yValue);
            c = (float) Math.sqrt((a * a) + (b * b));


            System.out.println("a= " + a + " ,b= " + b + " ,c= " + c);

            angle = (float) Math.asin(a / c);

            crossBow.setRotation((float) ((angle * 360) / (2 * Math.PI)));

            changeArcsTexture(c);
            System.out.println("the shooting angle is: " + angle);

        } else if (xFinal < xValue && yFinal < yValue) {

            a = Math.abs(xFinal - xAxisY);
            b = Math.abs(yAxisY - yValue);
            c = (float) Math.sqrt((a * a) + (b * b));

            System.out.println("a= " + a + " ,b= " + b + " ,c= " + c);

            angle = (float) Math.asin(a / c);

            crossBow.setRotation((float) -((angle * 360) / (2 * Math.PI)));
            angle = angle*-1;

            changeArcsTexture(c);


        }
    }

    public void changeArcsTexture(float c) {
        if (c >= 60) {
            if (c > higerH && stretched == true) {
                crossBow.changeTexture((int) CrossBow.textures[actualTexture].x, (int) CrossBow.textures[actualTexture].y,
                        CrossBow.CROSS_BOW_POSITION_X, CrossBow.CROSS_BOW_POSITION_Y);
                System.out.println("position: "+ crossBow.getArcPosition().x+", "+ crossBow.getArcPosition().y);
                higerH = c;
                if ((actualTexture + 1) > (CrossBow.textures.length - 1)) {
                    stretched = false;
                } else {
                    actualTexture++;
                }

            } else if (c < higerH && stretched == false) {
                crossBow.changeTexture((int) CrossBow.textures[actualTexture - 1].x, (int) CrossBow.textures[actualTexture - 1].y,
                        CrossBow.CROSS_BOW_POSITION_X, CrossBow.CROSS_BOW_POSITION_Y);
                higerH = c;
                if ((actualTexture -2
                ) < 0) {
                    stretched = true;
                } else {
                    actualTexture--;
                }

            }
        }
    }

    public void fillAnswers(){
        answers= new ArrayList<Texture>();
        answers.add(new Texture(Gdx.files.internal("answers/11_14.png")));
        answers.add(new Texture(Gdx.files.internal("answers/39_20.png")));
        answers.add(new Texture(Gdx.files.internal("answers/75_27.png")));
        answers.add(new Texture(Gdx.files.internal("answers/56_12.png")));
        answers.add(new Texture(Gdx.files.internal("answers/69_18.png")));
        answers.add(new Texture(Gdx.files.internal("answers/22_20.png")));
        answers.add(new Texture(Gdx.files.internal("answers/9_18.png")));
        answers.add(new Texture(Gdx.files.internal("answers/83_40.png")));
        answers.add(new Texture(Gdx.files.internal("answers/41_28.png")));
    }

    public  void fillQuestions(){
        questons = new ArrayList<Texture>();
        questons.add(new Texture(Gdx.files.internal("operations/1_2+2_7.png")));
        questons.add(new Texture(Gdx.files.internal("operations/3_4+6_5.png")));
        questons.add(new Texture(Gdx.files.internal("operations/7_9+6_3.png")));
        questons.add(new Texture(Gdx.files.internal("operations/8_3+8_4.png")));
        questons.add(new Texture(Gdx.files.internal("operations/7_2+3_9.png")));
        questons.add(new Texture(Gdx.files.internal("operations/2_4+3_5.png")));
        questons.add(new Texture(Gdx.files.internal("operations/1_3+1_6.png")));
        questons.add(new Texture(Gdx.files.internal("operations/7_8+6_5.png")));
        questons.add(new Texture(Gdx.files.internal("operations/5_7+3_4.png")));
    }

    public  void questionAndThreeAnswers(){

        int [] historyAnswerRandom = new int [2];
        int randomquestion = (int) (Math.random()*9);
        operationImage = new Sprite(questons.get(randomquestion));
        operationImage.setBounds(640,8000/FirstScreen.PPM,33700/FirstScreen.PPM,25600/FirstScreen.PPM);

        int randomAnswers = (int) (Math.random()*9);

        while (randomAnswers == randomquestion){
                randomAnswers = (int) (Math.random()*9);
                if(randomAnswers != randomquestion){
                    resOp[0] = new Sprite(answers.get(randomAnswers));
                    historyAnswerRandom[0] = randomAnswers;
                    randomAnswers = (int) (Math.random()*9);
                    break;
                }
        }

        while ((randomAnswers == randomquestion) || (randomAnswers == historyAnswerRandom[0])  ){
            randomAnswers = (int) (Math.random()*9);
            if((randomAnswers != randomquestion) && (randomAnswers != historyAnswerRandom[0])){
                resOp[1] = new Sprite(answers.get(randomAnswers));
                historyAnswerRandom[1] = randomAnswers;
                randomAnswers = (int) (Math.random()*9);
                break;
            }
        }

        while ((randomAnswers == randomquestion) || (randomAnswers == historyAnswerRandom[0]) ||  (randomAnswers == historyAnswerRandom[1])){
            randomAnswers = (int) (Math.random()*9);
            if((randomAnswers != randomquestion) && (randomAnswers != historyAnswerRandom[0]) && (randomAnswers != historyAnswerRandom[1])){
                resOp[2] = new Sprite(answers.get(randomAnswers));
                historyAnswerRandom[2] = randomAnswers;
                break;
            }
        }




        /*resOp[0] = new Sprite(answers.get(randomAnswers));
             if((randomAnswers-1) < 0){
                resOp[1] = new Sprite(answers.get(randomAnswers+3));
                resOp[2] = new Sprite(answers.get(randomAnswers+5));
            }else if((randomAnswers+1) > answers.size()){
                resOp[1] = new Sprite(answers.get(randomAnswers-3));
                resOp[2] = new Sprite(answers.get(randomAnswers-5));
            }*/
        resOp[0] = new Sprite(answers.get(randomquestion));






    }




}



