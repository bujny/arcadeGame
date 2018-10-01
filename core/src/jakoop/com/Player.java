package jakoop.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	float posX;
	float posY;
	float width;
	float height; 
	int currentCoin;
	int lifes; 
    Texture texture;
    
    final float SPEED = 600.0f; 


 public Player() {
	 width = 70;
	 height = 70;
	 posX = 640 - width/2; 
	 posY = 180 - height/2;
	currentCoin = Resources.ONE_EURO;
	lifes = 3;
	texture = new Texture(Gdx.files.internal("txapela.png"));
 }
 
 public void draw(SpriteBatch spriteBatch) {
	 float deltaTime = Gdx.graphics.getDeltaTime();
	 spriteBatch.draw(texture, posX, posY, width, height);
	 
	 if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) posX -= deltaTime * SPEED;
	 if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) posX += deltaTime * SPEED;
	 if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) posY += deltaTime * SPEED;
	 if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) posY -= deltaTime * SPEED;

 }
}
