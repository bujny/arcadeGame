package jakoop.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy {
	float posX;
	float posY;
	float width;
	float height;
	Texture texture;
	int value;

	public Enemy (int valueType, int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		value = valueType;
		
		if(value == Resources.ONE_EURO) {
			texture = new Texture(Gdx.files.internal(Resources.ENEMY_BEER));
			width = 45;
			height = 65;
		}
		else if(value == Resources.TWO_EURO) {
				texture = new Texture(Gdx.files.internal(Resources.ENEMY_SHOT));
				width = 33;
				height = 44;
			} 
		else if(value == Resources.FIVE_EURO) {
			texture = new Texture(Gdx.files.internal(Resources.ENEMY_DRINK));
			width = 54;
			height = 70;
		}
	}
		
		public void draw(SpriteBatch spriteBatch, float speed) {
			spriteBatch.draw(texture, posX, posY, width, height);
			movement(speed);
		}

		private void movement(float speed) {
			 float deltaTime = Gdx.graphics.getDeltaTime();
			 posY -= deltaTime * speed;
			 if(posY<75) posY=75;
		}

		public float getPosX() {
			return posX;
		}

		public void setPosX(float posX) {
			this.posX = posX;
		}

		public float getPosY() {
			return posY;
		}

		public void setPosY(float posY) {
			this.posY = posY;
		}	
}
