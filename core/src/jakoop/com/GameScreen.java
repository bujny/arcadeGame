package jakoop.com;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
	
	final InvadersGame game;
	final SpriteBatch spriteBatch;
	final int difficulty;
	float speed;
	int[] enemiesDimension;
    OrthographicCamera camera;
    Viewport viewport;
    Texture background;
    Player player;
    Map<Enemy, Integer> enemies;
	
	public GameScreen(final InvadersGame game, int difficulty) {
		this.game=game;
		this.spriteBatch = game.spriteBatch;
		this.difficulty = difficulty;
		chooseSettings();
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        //viewport = new StretchViewport(1280, 720, camera)
        background = new Texture(Gdx.files.internal("background.png")); 
        player = new Player(); 
        enemies = new HashMap<Enemy, Integer>();
        initializeEnemies();
	}

	private void chooseSettings() {
		enemiesDimension = new int[2];
		enemiesDimension[0]=6*difficulty; //rows
		enemiesDimension[1]=Resources.COLUMNS; //columns
		switch(difficulty) {
		case 1: speed = Resources.SPEED_LVL1;
			break;
		case 2: speed = Resources.SPEED_LVL2;
			break;
		case 3: speed = Resources.SPEED_LVL3;
			break;
		default: speed = Resources.SPEED_LVL1;
			break;
		}
	}

	private void initializeEnemies() {
		Random rand = new Random();
		int enemyNum;
		Enemy enemy;
		
		for(int rows=0; rows<enemiesDimension[0]; rows++) {
			for(int columns=0; columns<enemiesDimension[1]; columns++) {
				enemyNum = rand.nextInt(3)+1;
				enemy = new Enemy(enemyNum,128+256*columns,(720+90*rows)); 
				enemies.put(enemy, rows);
			}
		}
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
        drawEnemies();
        spriteBatch.end();
	}

	private void drawEnemies() {
		Enemy enemy;
		for (Map.Entry<Enemy, Integer> entry : enemies.entrySet()) {
		    entry.getKey().draw(spriteBatch, speed);
		}
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
