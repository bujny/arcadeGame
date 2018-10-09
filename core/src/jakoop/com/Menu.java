package jakoop.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Menu implements Screen {

	private final InvadersGame game;
	private final SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private Texture background;
	private Stage mainStage;
	private TextButton startB;
	private TextButton exitB;
	private TextButton playerB;
	private TextButton hallOfFameB;
	private TextButton createHowToPlayB;
	private Skin skin;
	private Sound button;
	private Music main;
	private PlayerScreen newPlayerScreen;
	private HallOfFameScreen hallOfFameScreen;
	private HowToPlay howToPayScreen;

	public Menu(final InvadersGame game) {
		this.game = game;
		this.spriteBatch = game.spriteBatch;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		background = new Texture(Gdx.files.internal(Resources.IMAGE_MAIN));
		button = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_BUTTON));
		main = Gdx.audio.newMusic(Gdx.files.internal(Resources.MUSIC_MAIN));
		newPlayerScreen = new PlayerScreen(game);
		howToPayScreen = new HowToPlay(game);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
		spriteBatch.end();
		mainStage.draw();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		mainStage = new Stage(new ScreenViewport());
		createButton(505, 288, 0);
		createButton(30, 30, 1);
		createButton(505, 370, 2);
		createButton(505, 115, 3);
		createButton(505, 203, 4);
		
		Gdx.input.setInputProcessor(mainStage);
		main.play();
		main.play();
		main.setVolume(0.6f);
	}

	public void createButton(int x, int y, int id) {
		skin = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		//pixmap.fill();
		skin.add("white", new Texture(pixmap));
		BitmapFont f = new BitmapFont();
		f.getData().setScale(3);
		skin.add("default", f);

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", new Color(0, 0, 0, 1));
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		switch (id) {
		case 0:
			createStartB(x, y, skin);
			mainStage.addActor(startB);
			break;
		case 1:
			createExitB(x, y, skin);
			mainStage.addActor(exitB);
			break;
		case 2:
			createPlayerB(x, y, skin);
			mainStage.addActor(playerB);
			break;
		case 3:
			createHallOfFameB(x, y, skin);
			mainStage.addActor(hallOfFameB);
			break;
		case 4:
			createHowToPlayB(x, y, skin);
			mainStage.addActor(createHowToPlayB);
			break;
		default:
			break;
		}
	}

	public void createStartB(int x, int y, Skin skin) {
		startB = new TextButton("", skin);
		startB.setX(x);
		startB.setY(y);
		startB.setWidth(270);
		startB.setVisible(true);
		startB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {				
				button.play();
				main.stop();
				GameScreen gameScreen = new GameScreen(game);
				game.setScreen(gameScreen);
				disableButtons();
			}

		});
	}

	public void createPlayerB(int x, int y, Skin skin) {
		playerB = new TextButton("", skin);
		playerB.setX(x);
		playerB.setY(y);
		playerB.setWidth(270);
		playerB.setHeight(30);
		playerB.setVisible(true);
		playerB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				game.setScreen(newPlayerScreen);
				disableButtons();
			}

		});
	}
	
	public void createHallOfFameB(int x, int y, Skin skin) {
		hallOfFameB = new TextButton("", skin);
		hallOfFameB.setX(x);
		hallOfFameB.setY(y);
		hallOfFameB.setWidth(270);
		hallOfFameB.setVisible(true);
		hallOfFameB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				game.setScreen(hallOfFameScreen);
				disableButtons();
			}

		});
	}

	public void createExitB(int x, int y, Skin skin) {
		exitB = new TextButton("", skin);
		exitB.setX(x);
		exitB.setY(y);
		exitB.setWidth(270);
		exitB.setVisible(true);
		exitB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				main.stop();
				mainStage.dispose();
				game.dispose();
			}

		});
	}
	
	public void createHowToPlayB(int x, int y, Skin skin) {
		createHowToPlayB = new TextButton("", skin);
		createHowToPlayB.setX(x);
		createHowToPlayB.setY(y);
		createHowToPlayB.setWidth(270);
		createHowToPlayB.setVisible(true);
		createHowToPlayB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {				
				button.play();
				game.setScreen(howToPayScreen);
				disableButtons();
			}

		});
	}
	
	private void disableButtons() {
		startB.setDisabled(true);
		playerB.setDisabled(true);
		hallOfFameB.setDisabled(true);
		exitB.setDisabled(true);
		createHowToPlayB.setDisabled(true);
	}

}
