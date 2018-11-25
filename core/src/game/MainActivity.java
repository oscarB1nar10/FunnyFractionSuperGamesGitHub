package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Game implements InputProcessor {
	ArrayList<Texture> dropImage;
	Texture bucketImage, pause, dropsFalling;
	Sound dropSound;
	Music rainMusic;
	SpriteBatch batch;
	OrthographicCamera camera;
	Rectangle bucket;
	Array<Rectangle> raindrops;
	long lastDropTime, toques;
	int random;
	String operacion;

	public MainActivity(String operacion) {
		this.operacion = operacion;
	}

	@Override
	public void create() {
		random = 0;
		dropImage = new ArrayList<Texture>();

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

		raindrops = new Array<Rectangle>();
		toques = 1;
		//Gdx.input.setCatchBackKey(true);
		//Gdx.input.setInputProcessor(this);
		spawnRaindrop();
	}

	public void LlenarOperaciones(){
		if(operacion.equals("3_4+5_4")) {
			dropImage.add(new Texture(Gdx.files.internal("respuestas/1_4.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/2_4.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/3_4.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/4_4.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/5_4.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/6_4.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/7_4.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/8_4.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/9_4.png")));
		}
		else if (operacion.equals("7_3+2_3")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/1_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/2_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/3_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/4_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/5_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/6_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/7_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/8_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/9_3.png")));
		}
		else if (operacion.equals("1_12+2_12")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/1_12.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/2_12.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/3_12.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/4_12.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/5_12.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/6_12.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/7_12.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/8_12.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/9_12.png")));
		}
		else if (operacion.equals("4_8+5_8")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/1_8.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/2_8.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/3_8.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/4_8.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/5_8.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/6_8.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/7_8.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/8_8.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/9_8.png")));
		}
		else if (operacion.equals("8_7+9_7")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/10_7.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/11_7.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/12_7.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/13_7.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/14_7.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/15_7.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/16_7.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/17_7.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/18_7.png")));
		}
		else if (operacion.equals("3_3+2_3")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/1_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/2_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/3_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/4_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/5_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/6_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/7_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/8_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/9_3.png")));
		}
		else if (operacion.equals("12_5+3_5")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/10_5.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/11_5.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/12_5.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/13_5.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/14_5.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/15_5.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/16_5.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/17_5.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/18_5.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/19_5.png")));
		}
		else if (operacion.equals("5_6+5_6")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/1_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/2_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/3_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/4_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/5_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/6_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/7_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/8_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/9_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/10_6.png")));
		}
		else if (operacion.equals("10_9+13_9")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/21_9.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/22_9.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/23_9.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/24_9.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/25_9.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/26_9.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/27_9.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/28_9.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/29_9.png")));
		}
		else if (operacion.equals("2_6+6_6")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/1_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/2_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/3_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/4_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/5_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/6_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/7_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/8_6.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/9_6.png")));
		}
		else if (operacion.equals("11_3+17_3")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/21_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/22_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/23_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/24_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/25_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/26_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/27_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/28_3.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/29_3.png")));
		}
		else if (operacion.equals("14_10+3_10")){
			dropImage.add(new Texture(Gdx.files.internal("respuestas/10_10.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/11_10.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/12_10.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/13_10.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/14_10.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/15_10.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/16_10.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/17_10.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/18_10.png")));
			dropImage.add(new Texture(Gdx.files.internal("respuestas/19_10.png")));
		}
	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime()+1000000000;
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		//here we put the background game
		batch.draw(dropsFalling,0,0);
		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop: raindrops) {
			batch.draw(dropImage.get(random), raindrop.x, raindrop.y);
		}
        batch.draw(pause, 0, 480-pause.getHeight());
		batch.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;

		if(TimeUtils.nanoTime() - lastDropTime > 2147483647 && toques < 10){
			spawnRaindrop();
			toques++;
			random = (int) (Math.random()*dropImage.size());
		}

		Iterator<Rectangle> iter = raindrops.iterator();
		while(iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) iter.remove();
			if(raindrop.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
			}
		}
	}

	@Override
	public void dispose() {
		for(int i = 0; i <= dropImage.size(); i++){
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
		System.out.println("screenX vale "+screenX+" y screenY vale "+screenY);
		if((screenX == 0 || screenX < 100) && (screenY == 0 || screenY < 100)){
		    
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