package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.funnyfractions.game.archery_game.ActionResolver;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Game implements InputProcessor {

	private ArrayList<Drops> dropImage;
	private Texture bucketImage, pause, dropsFalling;
	private Sound dropSound;
	private Music rainMusic;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private Array<Rectangles> raindrops;
	private long lastDropTime, toques;
	private int random;
	private boolean nextDrop = false;
	private String respuesta;
	private ActionResolver  actionResolver;
	private Preferences pref;

	public MainActivity(String respuesta, ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
		this.respuesta = respuesta;

	}

	@Override
	public void create() {
		random = 0;
		dropImage = new ArrayList<Drops>();
		pref = Gdx.app.getPreferences("SHARED_PREFERENCES");
		pref.putBoolean("pause",false);
		pref.flush();


		LlenarOperaciones();

		bucketImage = new Texture(Gdx.files.internal("buho.png"));
		pause = new Texture(Gdx.files.internal("pause_button.png"));
		dropsFalling = new Texture(Gdx.files.internal("dropsFalling.png"));


		dropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/rain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		raindrops = new Array<Rectangles>();
		toques = 1;
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
		spawnRaindrop();
	}

	public void LlenarOperaciones(){
		if(respuesta != "") {
			dropImage.add(new Drops("respuestas/4_8.png","4_8"));
			dropImage.add(new Drops("respuestas/8_33.png","8_33"));
			dropImage.add(new Drops("respuestas/12_42.png","12_42"));
			dropImage.add(new Drops("respuestas/14_25.png","14_25"));
			dropImage.add(new Drops("respuestas/21_135.png","21_135"));
			dropImage.add(new Drops("respuestas/40_36.png","40_36"));
			dropImage.add(new Drops("respuestas/48_45.png","48_45"));
			dropImage.add(new Drops("respuestas/54_48.png","54_48"));
			dropImage.add(new Drops("respuestas/72_6.png","72_6"));
			dropImage.add(new Drops("respuestas/105_40.png","105_40"));
		}
	}

	private void spawnRaindrop() {
		Rectangles raindrop = new Rectangles();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime()+1000000000;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		if(!pref.getBoolean("sound_drop",true)){
			rainMusic.pause();
		}else  if(!rainMusic.isPlaying()){
			rainMusic.play();
		}
		//here we put the background game
		batch.draw(dropsFalling,0,0);
		batch.draw(bucketImage, bucket.x, bucket.y);
		/*for(Rectangle raindrop: raindrops) {
			batch.draw(dropImage.get(random), raindrop.x, raindrop.y);

		}*/
		for(int i = 0; i<raindrops.size; i++){
			batch.draw(dropImage.get(random), raindrops.get(i).x, raindrops.get(i).y);
			raindrops.get(i).setDescription(dropImage.get(random).getDescription());
		}
		batch.draw(pause,0,416,64,64);
		batch.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;

		if(nextDrop && toques < 10 && !pref.getBoolean("pause",false)){
			spawnRaindrop();
			toques++;
			random = (int) (Math.random()*dropImage.size());
		}
		nextDrop = false;

		Iterator<Rectangles> iter = raindrops.iterator();
		while(iter.hasNext() && !pref.getBoolean("pause",false)) {
			Rectangles raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) {
				nextDrop = true;
                iter.remove();

            } else if(raindrop.overlaps(bucket)) {
				if(raindrop.getDescription().equals(respuesta)){
					System.out.println("Se ha tocado capturado la respuesta correcta!!");
				}
				dropSound.play();
				nextDrop = true;
				iter.remove();
			}
		}

		if(toques == 10 && nextDrop){
            actionResolver.menuGotas();
        }

	}

	@Override
	public void dispose() {
		for(int i = 0; i < dropImage.size(); i++){
			dropImage.get(i).dispose();
		}
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int btnBounds = (64 * Gdx.graphics.getWidth()) / 1280;
		if ((screenX == 0 || screenX < btnBounds) && (screenY == 0 || screenY < btnBounds)) {
			Preferences pref = Gdx.app.getPreferences("SHARED_PREFERENCES");
			pref.putBoolean("pause",true);
			pref.flush();
			actionResolver.menuGotas();
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}