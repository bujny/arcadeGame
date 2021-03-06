package jakoop.com;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	float posX;
	float posY;
	float width;
	float height;
	int currentCoin;
	Lifes lifes;
	Texture texture;
	ArrayList<Coin> coins;

	Coin staticCoin;

	final float SPEED = 600.0f;
	Sound shoot;
	Sound changeCoin;

	public Player() {
		width = 70;
		height = 70;
		posX = 640 - width / 2;
		posY = 180 - height / 2;
		currentCoin = Resources.COIN_ONE_EURO;
		staticCoin = new Coin(currentCoin, 605, 15);
		lifes = new Lifes();
		texture = new Texture(Gdx.files.internal(InvadersGame.getMainMenuScreen().getCurrentUser().getSkin()));
		changeCoin = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_CHANGE_COIN));
		coins = new ArrayList<Coin>();
	}

	public void draw(SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, posX, posY, width, height);
		staticCoin.drawStatic(spriteBatch);
		lifes.draw(spriteBatch);
		movement();
		for (int i = 0; i < coins.size(); i++) {
			Coin coin = coins.get(i);
			if (coin.isOut())
				coins.remove(i);
			coin.draw(spriteBatch);
		}
	}

	public void movement() {
		float deltaTime = Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT))
			posX -= deltaTime * SPEED;
		if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT))
			posX += deltaTime * SPEED;
		if (Gdx.input.isKeyPressed(Keys.DPAD_UP))
			posY += deltaTime * SPEED;
		if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN))
			posY -= deltaTime * SPEED;

		if (posX < 0)
			posX = 0;
		if (posY < 75)
			posY = 75;
		if (posX > 1280 - width)
			posX = 1280 - width;
		if (posY > 360 - height)
			posY = 360 - height;

		if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			changeCoin(Resources.COIN_ONE_EURO);
		} else if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
			changeCoin(Resources.COIN_TWO_EURO);
		} else if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
			changeCoin(Resources.COIN_FIVE_EURO);
		}
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			coins.add(new Coin(currentCoin, posX + (width / 2), posY + height));
			shoot = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_THROW_COIN));
			shoot.play();
		}
	}

	void changeCoin(int coin) {
		if (Resources.COIN_TWO_EURO == coin) {
			currentCoin = Resources.COIN_TWO_EURO;
			staticCoin = new Coin(currentCoin, 605, 15);
			changeCoin.play();
			return;
		}
		if (Resources.COIN_FIVE_EURO == coin) {
			currentCoin = Resources.COIN_FIVE_EURO;
			staticCoin = new Coin(currentCoin, 605, 15);
			changeCoin.play();
			return;
		}
		if (Resources.COIN_ONE_EURO == coin) {
			currentCoin = Resources.COIN_ONE_EURO;
			staticCoin = new Coin(currentCoin, 605, 15);
			changeCoin.play();
			return;
		}
	}

	public ArrayList<Coin> getCoins() {
		return coins;
	}

	public void menosLife() {
		lifes.menosLife();
	}

	public boolean isGameOver() {
		return lifes.isGameOver();
	}
}
