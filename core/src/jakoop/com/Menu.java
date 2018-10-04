package jakoop.com;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Menu implements Screen{

	final InvadersGame game;
	final SpriteBatch spriteBatch;
    OrthographicCamera camera;
    Viewport viewport;
    Texture background;
    Stage stage;
    TextButton startB, exitB;
    Skin skin;
    Sound button;
    public GameScreen mainGameScreen;

    
	public Menu(final InvadersGame game) {
		this.game=game;
		this.spriteBatch = game.spriteBatch;
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        //viewport = new StretchViewport(1280, 720, camera)
        background = new Texture(Gdx.files.internal("main.png")); 
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
        spriteBatch.draw(background,0,0);
        spriteBatch.end();
        stage.draw();

		
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
		stage = new Stage(new ScreenViewport());
		createButton(505,290, 0);
		createButton(30,25, 1);
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
		if(id==0) {
			createStartB(x, y, skin);
			stage.addActor(startB);

		}
		if(id==1) {
			createExitB(x, y,skin);
			stage.addActor(exitB);
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
				System.out.println("entra");
				button = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_BUTTON));
	        	button.play();
				GameScreen gameScreen = new GameScreen(game);
				game.setScreen(gameScreen);
				startB.setDisabled(true);
				exitB.setDisabled(true);
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
				button = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_BUTTON));
	        	button.play();
				stage.dispose();
				game.dispose();
			}
			
	    });
	}

}
