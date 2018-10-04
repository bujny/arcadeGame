package jakoop.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.SingleSelectionModel;

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
	final int difficulty;
	float speed;
	int[] enemiesDimension;
    OrthographicCamera camera;
    Viewport viewport;
    Texture background;
    Player player;
    Music stage1;
    Sound shot;
    EnemyWave enemies;
	
	public GameScreen(final InvadersGame game) {
		this.game=game;
		this.spriteBatch = game.spriteBatch;
		this.difficulty = 1;
		chooseSettings();
		enemies = new EnemyWave(enemiesDimension, speed);
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        //viewport = new StretchViewport(1280, 720, camera)
        background = new Texture(Gdx.files.internal(Resources.IMAGE_BACKGROUND)); 
        player = new Player(); 
        stage1 = Gdx.audio.newMusic(Gdx.files.internal(Resources.MUSIC_STAGE3));
        stage1.play();
        System.out.println("hola");
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
        enemies.drawEnemies(spriteBatch);
        checkConflicts();
        spriteBatch.end();
	}

	private void checkConflicts() {
		ArrayList<Coin> coins = player.getCoins();
		
		Iterator<Map.Entry<Enemy,Integer>> iteratorEnemy = enemies.getEnemies().entrySet().iterator();
		while (iteratorEnemy.hasNext()) {
		    Map.Entry<Enemy,Integer> entry = iteratorEnemy.next();
			for (Iterator<Coin> iteratorCoin = coins.iterator(); iteratorCoin.hasNext(); ) {
			    Coin c = iteratorCoin.next();	    
			    
				switch(checkPos(c, entry.getKey())) {
				case 0:
					break;
				case 1: iteratorCoin.remove();
						removeLife
					break;
				case 2: iteratorCoin.remove();
		    			iteratorEnemy.remove();
		    			if(enemies.getEnemies().isEmpty()) youWin
					break;
				default: 
					break;
				}
			}
		}
	}

	private int checkPos(Coin c, Enemy enemy) {
		if(((c.getPosX()+c.getWidth()) > enemy.getPosX()) &&
				(c.getPosX() < (enemy.getPosX()+enemy.getWidth())) &&
				((c.getPosY()+c.getHeight()) > enemy.getPosY()) &&
				(c.getPosY() < (enemy.getPosY()+enemy.getHeight()))) {
			
			if(c.getValue() == enemy.getValue()) return 2;
			else return 1;
		}
		return 0;
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
