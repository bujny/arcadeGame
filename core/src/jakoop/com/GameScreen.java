package jakoop.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.print.DocFlavor.READER;
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
    Texture stageBackground;
    Texture background;
    Player player;
    Music stage1;
    Sound youLose;
    Sound victory;
    Sound loseLife;
    Sound drinkHit;
    Sound stageComplete;
    EnemyWave enemies;
    boolean isGameOver;
    boolean hasWon;
	boolean stageFinished;
    Texture gameOver;
    Texture youWin;
    Texture pressKey;
	
	public GameScreen(final InvadersGame game) {
		this.isGameOver = false;
		this.stageFinished = false;
		this.game=game;
		this.spriteBatch = game.spriteBatch;
		this.difficulty = 1;
		chooseSettings();
		enemies = new EnemyWave(enemiesDimension, speed);
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        //viewport = new StretchViewport(1280, 720, camera)
        stageBackground = new Texture(Gdx.files.internal(Resources.IMAGE_STAGE_BACKGROUND));
        background = new Texture(Gdx.files.internal(Resources.IMAGE_BACKGROUND));
        player = new Player(); 
        stage1 = Gdx.audio.newMusic(Gdx.files.internal(Resources.MUSIC_STAGE3));
        stage1.play();
        stage1.setVolume(0.1f);
        gameOver = new Texture(Gdx.files.internal(Resources.IMAGE_GAME_OVER));
        youWin = new Texture(Gdx.files.internal(Resources.IMAGE_YOU_WIN));
        pressKey = new Texture(Gdx.files.internal(Resources.IMAGE_PRESS_KEY));
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
        
        if(isGameOver && !stageFinished) {
        	stage1.stop();
            youLose = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_GAME_OVER));
            youLose.play();
            stageFinished = true;
        }
        else if(hasWon && !stageFinished) {
			stage1.stop();   
			victory = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_VICTORY));
			victory.play();
			stageFinished = true;
        }
        else if(stageFinished) {
        	spriteBatch.draw(background,0,0);
        	spriteBatch.draw(pressKey, 350, 70, 600, 100);
        	if(isGameOver) {
        		spriteBatch.draw(gameOver, 340, 220, 600, 300);
        	} else if (hasWon) {
        		spriteBatch.draw(youWin, 340, 220, 600, 300);           	
        	}
        	if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
        		this.dispose();
            	game.setScreen(new Menu(game));
            }
        }
        else {
        spriteBatch.draw(stageBackground,0,0);	
        player.draw(spriteBatch);
        enemies.drawEnemies(spriteBatch);
        checkConflicts();
        playerEnemyConflict();
        }
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
						player.menosLife();
			            loseLife = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_LOOSE_LIFE));
			        	loseLife.play();
						isGameOver = player.isGameOver();
					break;
				case 2: iteratorCoin.remove();
		    			iteratorEnemy.remove();
		    			drinkHit = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_DRINK));
			        	drinkHit.play();
		    			if(enemies.getEnemies().isEmpty()) {
		    				stageComplete = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_STAGE_COMPLETE));
		    	        	stageComplete.play();
		    				hasWon = true;
		    			}
					break;
				default: 
					break;
				}
			}
		}
	}
	
	private void playerEnemyConflict() {
		
		Iterator<Map.Entry<Enemy,Integer>> iteratorEnemy = enemies.getEnemies().entrySet().iterator();
		while (iteratorEnemy.hasNext()) {
		    Map.Entry<Enemy,Integer> entry = iteratorEnemy.next();    
				if(enemyHitPlayer(entry.getKey())) {
					isGameOver=true;
				}
		}
	}
	
	private boolean enemyHitPlayer(Enemy enemy) { 
		if(player.posX+player.width>enemy.posX &&
				player.posX<enemy.posX+enemy.width&&
				player.posY+player.height>enemy.posY&&
				player.posY<enemy.posY+enemy.height) return true;
		if(enemy.posY<70) return true;
		return false;	
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
		stage1.dispose(); 
		// TODO Auto-generated method stub
	}

}
