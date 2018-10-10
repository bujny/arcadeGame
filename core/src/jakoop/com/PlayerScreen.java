package jakoop.com;

import java.util.Map;

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

public class PlayerScreen implements Screen {
	private final InvadersGame game;
	private final SpriteBatch spriteBatch;
	private Stage playerStage;
	private OrthographicCamera camera;
	private Texture background;
	private Texture rbSelected;
	private Skin skin;
	private Sound button;
	
	private TextButton okB;
	private TextButton player1B;
	private TextButton player2B;
	private TextButton player3B;
	private TextButton player4B;
	private TextButton player5B;
	private TextButton player6B;
	private static float rbXpos;
	private static float rbYpos;
	private BitmapFont userFont;
	
	public PlayerScreen(final InvadersGame game) {
		this.game = game;
		this.spriteBatch = game.spriteBatch;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		background = new Texture(Gdx.files.internal(Resources.IMAGE_SELECT_PLAYER));
		button = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_BUTTON));
		rbSelected = new Texture(Gdx.files.internal(Resources.IMAGE_RB_SELECTED));
		rbXpos = 174;
		rbYpos = 409;
		printUserNames();
	}
	
	private void printUserNames() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Resources.FONT_BRODWAY));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 50;
		userFont = generator.generateFont(parameter); 
		generator.dispose();
	}
	
	@Override
	public void show() {
		playerStage = new Stage(new ScreenViewport());
		showButtons();
		Gdx.input.setInputProcessor(playerStage);
	}
	
	public void showButtons() {
		createButton(980, 29, 0);
		createButton(165, 403, 1);
		createButton(165, 285, 2);
		createButton(165, 170, 3);
		createButton(723, 403, 4);
		createButton(723, 285, 5);
		createButton(723, 170, 6);
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
			playerStage.addActor(okB);
			break;
		case 1:
			createPlayer1B(x, y, skin);
			playerStage.addActor(player1B);
			break;
		case 2:
			createPlayer2B(x, y, skin);
			playerStage.addActor(player2B);
			break;
		case 3:
			createPlayer3B(x, y, skin);
			playerStage.addActor(player3B);
			break;
		case 4:
			createPlayer4B(x, y, skin);
			playerStage.addActor(player4B);
			break;
		case 5:
			createPlayer5B(x, y, skin);
			playerStage.addActor(player5B);
			break;
		case 6:
			createPlayer6B(x, y, skin);
			playerStage.addActor(player6B);
			break;
		default:
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
				playerStage.dispose();
				game.setScreen(InvadersGame.getMainMenuScreen());
				disableButtons();
			}
		});
	}
	
	private void disableButtons() {
		okB.setDisabled(true);
		player1B.setDisabled(true);
		player2B.setDisabled(true);
		player3B.setDisabled(true);
		player4B.setDisabled(true);
		player5B.setDisabled(true);
		player6B.setDisabled(true);
	}

	public void createPlayer1B(int x, int y, Skin skin) {
		player1B = new TextButton("", skin);
		player1B.setX(x);
		player1B.setY(y);
		player1B.setWidth(60);
		player1B.setVisible(true);
		player1B.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				rbXpos = 174;
				rbYpos = 409;
				Map<Integer, User> mapUsers = InvadersGame.getMainMenuScreen().getMapUsers();
				InvadersGame.getMainMenuScreen().setCurrentUser(mapUsers.get(1));
			}
		});
	}
	
	public void createPlayer2B(int x, int y, Skin skin) {
		player2B = new TextButton("", skin);
		player2B.setX(x);
		player2B.setY(y);
		player2B.setWidth(60);
		player2B.setVisible(true);
		player2B.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				rbXpos = 174;
				rbYpos = 292;
				Map<Integer, User> mapUsers = InvadersGame.getMainMenuScreen().getMapUsers();
				InvadersGame.getMainMenuScreen().setCurrentUser(mapUsers.get(2));
			}
		});
	}
	
	public void createPlayer3B(int x, int y, Skin skin) {
		player3B = new TextButton("", skin);
		player3B.setX(x);
		player3B.setY(y);
		player3B.setWidth(60);
		player3B.setVisible(true);
		player3B.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				rbXpos = 174;
				rbYpos = 176;
				Map<Integer, User> mapUsers = InvadersGame.getMainMenuScreen().getMapUsers();
				InvadersGame.getMainMenuScreen().setCurrentUser(mapUsers.get(3));
			}
		});
	}
	
	public void createPlayer4B(int x, int y, Skin skin) {
		player4B = new TextButton("", skin);
		player4B.setX(x);
		player4B.setY(y);
		player4B.setWidth(60);
		player4B.setVisible(true);
		player4B.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				rbXpos = 731;
				rbYpos = 408;
				Map<Integer, User> mapUsers = InvadersGame.getMainMenuScreen().getMapUsers();
				InvadersGame.getMainMenuScreen().setCurrentUser(mapUsers.get(4));
			}
		});
	}
	
	public void createPlayer5B(int x, int y, Skin skin) {
		player5B = new TextButton("", skin);
		player5B.setX(x);
		player5B.setY(y);
		player5B.setWidth(60);
		player5B.setVisible(true);
		player5B.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				rbXpos = 731;
				rbYpos = 292;
				Map<Integer, User> mapUsers = InvadersGame.getMainMenuScreen().getMapUsers();
				InvadersGame.getMainMenuScreen().setCurrentUser(mapUsers.get(5));
			}
		});
	}
	
	public void createPlayer6B(int x, int y, Skin skin) {
		player6B = new TextButton("", skin);
		player6B.setX(x);
		player6B.setY(y);
		player6B.setWidth(60);
		player6B.setVisible(true);
		player6B.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				rbXpos = 731;
				rbYpos = 176;
				Map<Integer, User> mapUsers = InvadersGame.getMainMenuScreen().getMapUsers();
				InvadersGame.getMainMenuScreen().setCurrentUser(mapUsers.get(6));
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
		spriteBatch.draw(rbSelected, rbXpos, rbYpos, 44, 44);
		userFont.draw(spriteBatch, InvadersGame.getMainMenuScreen().getMapUsers().get(1).getName(), 280, 450);
		userFont.draw(spriteBatch, InvadersGame.getMainMenuScreen().getMapUsers().get(2).getName(), 280, 332);
		userFont.draw(spriteBatch, InvadersGame.getMainMenuScreen().getMapUsers().get(3).getName(), 280, 219);
		userFont.draw(spriteBatch, InvadersGame.getMainMenuScreen().getMapUsers().get(4).getName(), 835, 450);
		userFont.draw(spriteBatch, InvadersGame.getMainMenuScreen().getMapUsers().get(5).getName(), 835, 332);
		userFont.draw(spriteBatch, InvadersGame.getMainMenuScreen().getMapUsers().get(6).getName(), 835, 219);
		spriteBatch.end();
		playerStage.draw();	
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
