package jakoop.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InvadersGame extends Game {
	public SpriteBatch spriteBatch;
    //public BitmapFont font;
	public static Menu mainMenuScreen;
	
    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        mainMenuScreen = new Menu(this);
        this.setScreen(mainMenuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        mainMenuScreen.dispose();
    	Gdx.app.exit();
    }
    
	public static Menu getMainMenuScreen() {
		return mainMenuScreen;
	}
}