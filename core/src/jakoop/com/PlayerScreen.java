package jakoop.com;

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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayerScreen implements Screen {
	private final InvadersGame game;
	private final SpriteBatch spriteBatch;
	private Stage stage;
	private OrthographicCamera camera;
	private Texture background;
	private Skin skin;
	private Sound button;
	private TextButton okB;
	private TextButton newPlayerB;

	
	public PlayerScreen(final InvadersGame game) {
		this.game = game;
		this.spriteBatch = game.spriteBatch;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		background = new Texture(Gdx.files.internal(Resources.IMAGE_SELECT_PLAYER));
		button = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_BUTTON));
	}
	
	@Override
	public void show() {
		stage = new Stage(new ScreenViewport());
		createButton(980, 29, 0);
		createButton(30, 29, 1);
		Gdx.input.setInputProcessor(stage);
		
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
			stage.addActor(okB);
			break;
		case 1:
			createNewPlayerB(x, y, skin);
			stage.addActor(newPlayerB);
			break;
		default:
			break;
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
		spriteBatch.end();
		stage.draw();
		
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
				stage.dispose();
				game.setScreen(new Menu(game));
				disableButtons();
			}
		});
	}
	
	public void createNewPlayerB(int x, int y, Skin skin) {
		newPlayerB = new TextButton("", skin);
		newPlayerB.setX(x);
		newPlayerB.setY(y);
		newPlayerB.setWidth(270);
		newPlayerB.setVisible(true);
		newPlayerB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				// ACTION
			}
		});
	}
	
	private void disableButtons() {
		okB.setDisabled(true);
		newPlayerB.setDisabled(true);
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
