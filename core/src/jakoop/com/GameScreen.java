package jakoop.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
	
	final InvadersGame game;
	final SpriteBatch spriteBatch;
    OrthographicCamera camera;
    Viewport viewport;
    Texture background;
    Player player;
    Enemy enemy1, enemy2, enemy3; 
    Music stage1;
    Sound shot;
	
	public GameScreen(final InvadersGame game) {
		this.game=game;
		this.spriteBatch = game.spriteBatch;
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        //viewport = new StretchViewport(1280, 720, camera)
        background = new Texture(Gdx.files.internal(Resources.IMAGE_BACKGROUND)); 
        player = new Player(); 
        enemy1 = new Enemy(Resources.ONE_EURO,100,150);
        enemy2 = new Enemy(Resources.TWO_EURO,300,150);
        enemy3 = new Enemy(Resources.FIVE_EURO,500,150);
        stage1 = Gdx.audio.newMusic(Gdx.files.internal(Resources.MUSIC_STAGE1));
        stage1.play();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(background,0,0);
        player.draw(spriteBatch);
        enemy1.draw(spriteBatch);
        enemy2.draw(spriteBatch);
        enemy3.draw(spriteBatch);
        spriteBatch.end();
        
        
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
