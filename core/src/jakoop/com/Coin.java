package jakoop.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Coin {
	float posX;
	float posY;
	float width;
	float height;
	Texture texture;
	int value;
	
	final float SPEED = 600.0f; 
	
	public Coin(int value, float posX, float posY) {
		width = 25;
		height = 25;
		this.posX = posX;
		this.posX -= width/2; 
		this.posY = posY;
		this.value = value;
		if(value == Resources.ONE_EURO) {
			texture = new Texture(Gdx.files.internal(Resources.COIN_ONE));
		}
		else if(value == Resources.TWO_EURO) {
				texture = new Texture(Gdx.files.internal(Resources.COIN_TWO));
			} 
		else if(value == Resources.FIVE_EURO) {
			texture = new Texture(Gdx.files.internal(Resources.COIN_FIVE));
		}
	}
	
	public boolean isOut() {
		if(posY>1280) return true;
		return false; 
	}
	
	public void draw(SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, posX, posY, width, height);
		movement();
	}
	
	public void drawStatic(SpriteBatch spriteBatch) {
		width=50;
		height=50;
		spriteBatch.draw(texture, posX, posY, width, height);
	}
	
	void movement() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		posY += deltaTime * SPEED;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public int getValue() {
		return value;
	}
}
