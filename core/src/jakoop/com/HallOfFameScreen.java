package jakoop.com;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HallOfFameScreen implements Screen {
	private final InvadersGame game;
	private final SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private Texture background;
	private Stage hallOfFameStage;
	private Skin skin;
	private Sound button;
	private TextButton okB;
	private TextButton resetScoresB;
	private BitmapFont userFont;
	private ArrayList<User> orderedArrayUsers;
	
	public HallOfFameScreen(final InvadersGame game) {
		this.game = game;
		this.spriteBatch = game.spriteBatch;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		background = new Texture(Gdx.files.internal(Resources.IMAGE_HALL_OF_FAME));
		button = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_BUTTON));
		getUserScores();
		printUserScores();
	}
	
	private void printUserScores() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Resources.FONT_BRODWAY));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;
		userFont = generator.generateFont(parameter); 
		generator.dispose();
	}

	private void getUserScores() {
		orderedArrayUsers = new ArrayList<User>();
		
		Collections.sort(orderedArrayUsers, (user1, user2) -> user1.getScore() - user2.getScore());
	}
	
	@Override
	public void show() {
		hallOfFameStage = new Stage(new ScreenViewport());
		createButton(980, 30, 0);
		createButton(30, 29, 1);
		Gdx.input.setInputProcessor(hallOfFameStage);
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
			createOkB(x, y, skin);
			hallOfFameStage.addActor(okB);
			break;
		case 1:
			createResetScoresB(x, y, skin);
			hallOfFameStage.addActor(resetScoresB);
			break;
		}
	}
	
	public void createOkB(int x, int y, Skin skin) {
		okB = new TextButton("", skin);
		okB.setX(x);
		okB.setY(y);
		okB.setWidth(270);
		okB.setVisible(true);
		okB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				hallOfFameStage.dispose();
				game.setScreen(InvadersGame.getMainMenuScreen());
				okB.setDisabled(true);
				resetScoresB.setDisabled(true);
			}
		});
	}
	
	public void createResetScoresB(int x, int y, Skin skin) {
		resetScoresB = new TextButton("", skin);
		resetScoresB.setX(x);
		resetScoresB.setY(y);
		resetScoresB.setWidth(270);
		resetScoresB.setVisible(true);
		resetScoresB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				// RESET THE SCORES CODE
			}
		});
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
		//userFont.draw(spriteBatch, "" + orderedArrayUsers.get(0), 500, 650);
		//userFont.draw(spriteBatch, "" + orderedArrayUsers.get(1), 500, 550);
		//userFont.draw(spriteBatch, "" + orderedArrayUsers.get(2), 500, 450);
		//userFont.draw(spriteBatch, "" + orderedArrayUsers.get(3), 500, 350);
		//userFont.draw(spriteBatch, "" + orderedArrayUsers.get(4), 500, 250);
		//userFont.draw(spriteBatch, "" + orderedArrayUsers.get(2), 500, 150);
		spriteBatch.end();
		hallOfFameStage.draw();
			
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
