package jakoop.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InvadersGame extends Game {
	public SpriteBatch spriteBatch;
    //public BitmapFont font;
    public GameScreen mainMenuScreen;
    

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        mainMenuScreen = new GameScreen(this);
        this.setScreen(mainMenuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        mainMenuScreen.dispose();
    }
}
