package jakoop.com;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GameScreen implements Screen {

	private final InvadersGame game;
	private final SpriteBatch spriteBatch;
	private final int difficulty;
	private float speed;
	private int[] enemiesDimension;
	private OrthographicCamera camera;
	private Texture stageBackground;
	private Texture background;
	private Player player;
	private Music stage;
	private Sound gameOver;
	private Sound victory;
	private Sound loseLife;
	private Sound drinkHit;
	private Sound stageComplete;
	private EnemyWave enemies;
	private boolean isGameOver;
	private boolean isVictory;
	private boolean stageFinished;
	private Texture gameOverTexture;
	private Texture youWinTexture;
	private Texture pressKeyTexture;
	private int score;
	private float combo;
	private BitmapFont scoreFont;
	DecimalFormat df;
	private static boolean flag = false;

	public GameScreen(final InvadersGame game) {
		this.isGameOver = false;
		this.stageFinished = false;
		this.game = game;
		this.spriteBatch = game.spriteBatch;
		this.difficulty = Resources.GAME_DIFFICULTY;
		this.score = 0;
		this.combo = Resources.GAME_COMBO_VALUE;
		chooseSettings();
		enemies = new EnemyWave(enemiesDimension, speed);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		// viewport = new StretchViewport(1280, 720, camera)
		stageBackground = new Texture(Gdx.files.internal(Resources.IMAGE_STAGE_BACKGROUND));
		background = new Texture(Gdx.files.internal(Resources.IMAGE_BACKGROUND));
		player = new Player();
		stage = Gdx.audio.newMusic(Gdx.files.internal(InvadersGame.getMainMenuScreen().getCurrentUser().getSong()));
		stage.play();
		stage.setVolume(Resources.GAME_VOLUME);
		gameOverTexture = new Texture(Gdx.files.internal(Resources.IMAGE_GAME_OVER));
		youWinTexture = new Texture(Gdx.files.internal(Resources.IMAGE_YOU_WIN));
		pressKeyTexture = new Texture(Gdx.files.internal(Resources.IMAGE_PRESS_KEY));
		printScore();
	}
	
	private void printScore() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Resources.FONT_BRODWAY));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 36;
		scoreFont = generator.generateFont(parameter); 
		generator.dispose();
		df = new DecimalFormat("#.00");
	}

	private void chooseSettings() {
		enemiesDimension = new int[2];
		enemiesDimension[0] = Resources.GAME_ENEMY_ROWS * difficulty; // rows
		enemiesDimension[1] = Resources.GAME_ENEMY_COLUMNS; // columns
		speed = Resources.GAME_SPEED_LVL;
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

		if (isGameOver && !stageFinished) {
			stage.stop();
			gameOver = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_GAME_OVER));
			gameOver.play();
			stageFinished = true;
		} else if (isVictory && !stageFinished) {
			stage.stop();
			victory = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_VICTORY));
			victory.play();
			stageFinished = true;
		} else if (stageFinished) {
			spriteBatch.draw(background, 0, 0);
			spriteBatch.draw(pressKeyTexture, 350, 70, 600, 100);
			if (isGameOver) {
				spriteBatch.draw(gameOverTexture, 340, 220, 600, 300);
			} else if (isVictory) {
				spriteBatch.draw(youWinTexture, 340, 220, 600, 300);
			}

			if(!flag) {
				flag = true;
				Gdx.app.postRunnable(new Runnable() {
			         @Override
			         public void run() {
			        	 try {
			        		 Thread.sleep(2500);
			        		 stage.dispose();
				             recordScore();
							 game.setScreen(InvadersGame.getMainMenuScreen());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	 
			         }
				});
			}

		} else {
			spriteBatch.draw(stageBackground, 0, 0);
			player.draw(spriteBatch);
			enemies.drawEnemies(spriteBatch);
			checkConflicts();
			playerEnemyConflict();
			scoreFont.draw(spriteBatch, "Score: "+ Integer.toString(score)+" (x"+df.format(combo)+")", 25, 55);
		}
		spriteBatch.end();
	}

	private void recordScore() {
		User user = InvadersGame.getMainMenuScreen().getCurrentUser();
		user.setScore(score);
		InvadersGame.getMainMenuScreen().getMapUsers().put(user.getId(), user);
	}
	
	private void checkConflicts() {
		ArrayList<Coin> coins = player.getCoins();

		Iterator<Map.Entry<Enemy, Integer>> iteratorEnemy = enemies.getEnemies().entrySet().iterator();
		while (iteratorEnemy.hasNext()) {
			Map.Entry<Enemy, Integer> entry = iteratorEnemy.next();
			for (Iterator<Coin> iteratorCoin = coins.iterator(); iteratorCoin.hasNext();) {
				Coin c = iteratorCoin.next();

				switch (checkPos(c, entry.getKey())) {
				case 0:
					break;
				case 1:
					iteratorCoin.remove();
					player.menosLife();
					combo=1.0f;
					loseLife = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_LOOSE_LIFE));
					loseLife.play();
					isGameOver = player.isGameOver();
					break;
				case 2:
					Enemy enemy = entry.getKey(); 
					score += enemy.getValue() * combo * 10; 
 					combo += 0.05;
					iteratorCoin.remove();
					iteratorEnemy.remove();
					drinkHit = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_DRINK));
					drinkHit.play();
					if (enemies.getEnemies().isEmpty()) {
						stageComplete = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_STAGE_COMPLETE));
						stageComplete.play();
						isVictory = true;
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private void playerEnemyConflict() {

		Iterator<Map.Entry<Enemy, Integer>> iteratorEnemy = enemies.getEnemies().entrySet().iterator();
		while (iteratorEnemy.hasNext()) {
			Map.Entry<Enemy, Integer> entry = iteratorEnemy.next();
			if (enemyHitPlayer(entry.getKey())) {
				isGameOver = true;
			}
		}
	}

	private boolean enemyHitPlayer(Enemy enemy) {
		if (player.posX + player.width > enemy.posX && player.posX < enemy.posX + enemy.width
				&& player.posY + player.height > enemy.posY && player.posY < enemy.posY + enemy.height)
			return true;
		if (enemy.posY < 70)
			return true;
		return false;
	}

	private int checkPos(Coin c, Enemy enemy) {
		if (((c.getPosX() + c.getWidth()) > enemy.getPosX()) && (c.getPosX() < (enemy.getPosX() + enemy.getWidth()))
				&& ((c.getPosY() + c.getHeight()) > enemy.getPosY())
				&& (c.getPosY() < (enemy.getPosY() + enemy.getHeight()))) {

			if (c.getValue() == enemy.getValue())
				return 2;
			else
				return 1;
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
