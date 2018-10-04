package jakoop.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Lifes {
	int amount;
	float posX;
	float posY;
	float width;
	float height; 
    Texture texture;
    
    public Lifes() {
    	texture = new Texture(Gdx.files.internal(Resources.IMAGE_HEART));
    	amount = 3;
    	width = 50;
    	height = 50;
    	posX = 1085;
    	posY = 15;
    }
    
    public void draw(SpriteBatch spriteBatch) {
    	int offest = 60;
    	for(int i=0; i<amount; i++)
    	spriteBatch.draw(texture, posX + i*offest, posY, width, height);
    }
    
    public void menosLife() {
    	amount--;
    }
    
    public boolean isGameOver() {
    	if(amount < 1) return true;
    	return false; 
    }
    
	

}
