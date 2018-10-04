package jakoop.com;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnemyWave {
	Map<Enemy, Integer> enemies;
	int[] enemiesDimension;
	float speed;
	
	public EnemyWave(int[] enemiesDimension, float speed) {
		enemies = new HashMap<Enemy, Integer>();
		this.enemiesDimension=enemiesDimension;
		this.speed = speed;
		initializeEnemies();
	}
	
	private void initializeEnemies() {
		Random rand = new Random();
		int enemyNum;
		Enemy enemy;
		
		for(int rows=0; rows<enemiesDimension[0]; rows++) {
			for(int columns=0; columns<enemiesDimension[1]; columns++) {
				enemyNum = rand.nextInt(3)+1;
				enemy = new Enemy(enemyNum,128+256*columns,(720+90*rows)); 
				enemies.put(enemy, rows);
			}
		}
	}
	
	 public void drawEnemies(SpriteBatch spriteBatch) {
		 for (Map.Entry<Enemy, Integer> entry : enemies.entrySet()) {
			 entry.getKey().draw(spriteBatch, speed);
			 }
	 }
	 
	public Map<Enemy, Integer> getEnemies() {
		return enemies;
	}
}
