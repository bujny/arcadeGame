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

public class HowToPlay implements Screen{
	
	private final InvadersGame game;
	private final SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private Texture background;
	private TextButton createOkB;
	private Stage howToPlayStage;
	private Skin skin;
	private Sound button;

	public HowToPlay(final InvadersGame game) {
		this.game = game;
		this.spriteBatch = game.spriteBatch;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		background = new Texture(Gdx.files.internal(Resources.IMAGE_HOW_TO_PLAY));
		button = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_BUTTON));
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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
		howToPlayStage.draw();
		
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
		howToPlayStage = new Stage(new ScreenViewport());
		createButton(980, 30);
		Gdx.input.setInputProcessor(howToPlayStage);
		
	}
	public void createButton(int x, int y) {
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

		createOkB(x, y, skin);
		howToPlayStage.addActor(createOkB);
		
	}
	
	public void createOkB(int x, int y, Skin skin) {
		createOkB = new TextButton("", skin);
		createOkB.setX(x);
		createOkB.setY(y);
		createOkB.setWidth(270);
		createOkB.setVisible(true);
		createOkB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {	
				button.play();
				game.setScreen(InvadersGame.getMainMenuScreen());
				createOkB.setDisabled(true);
			}

		});
	}

}
