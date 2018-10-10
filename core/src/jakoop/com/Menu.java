package jakoop.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Menu implements Screen {

	private final InvadersGame game;
	private final SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private Texture background;
	private Stage mainStage;
	private TextButton startB;
	private TextButton exitB;
	private TextButton playerB;
	private TextButton hallOfFameB;
	private TextButton howToPlayB;
	private TextButton creditsB;
	private Skin skin;
	private Sound button;
	private Music main;
	private PlayerScreen newPlayerScreen;
	private HallOfFameScreen hallOfFameScreen;
	private HowToPlayScreen howToPayScreen;
	private CreditsScreen creditsScreen;
	private ArrayList<User> arrayUsers;
	private User currentUser;
	private BitmapFont userFont;

	public Menu(final InvadersGame game) {
		this.game = game;
		this.spriteBatch = game.spriteBatch;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		background = new Texture(Gdx.files.internal(Resources.IMAGE_MAIN));
		button = Gdx.audio.newSound(Gdx.files.internal(Resources.SOUND_BUTTON));
		main = Gdx.audio.newMusic(Gdx.files.internal(Resources.MUSIC_MAIN));
		newPlayerScreen = new PlayerScreen(game);
		howToPayScreen = new HowToPlayScreen(game);
		hallOfFameScreen = new HallOfFameScreen(game);
		creditsScreen = new CreditsScreen(game);
		createUsers();
		loadCurrentUser();
		printCurrentUserName();
	}

	public void createUsers() {
		arrayUsers = new ArrayList<User>();
		arrayUsers.add(new User(1, Resources.NAME_USER1, Resources.SKIN_USER1));
		arrayUsers.add(new User(2, Resources.NAME_USER2, Resources.SKIN_USER2));
		arrayUsers.add(new User(3, Resources.NAME_USER3, Resources.SKIN_USER3));
		arrayUsers.add(new User(4, Resources.NAME_USER4, Resources.SKIN_USER4));
		arrayUsers.add(new User(5, Resources.NAME_USER5, Resources.SKIN_USER5));
		arrayUsers.add(new User(6, Resources.NAME_USER6, Resources.SKIN_USER6));
		
		currentUser = arrayUsers.get(0);
		
		// ERASE, IS FOR CHECKING
		arrayUsers.get(0).setScore(10);
		arrayUsers.get(1).setScore(60);
		arrayUsers.get(2).setScore(30);
		arrayUsers.get(3).setScore(50);
		arrayUsers.get(4).setScore(40);
		arrayUsers.get(5).setScore(20);
	}
	
	public void loadCurrentUser() {
		Integer currentID = null;
		File file = new File("currentPlayerID.txt");
		BufferedReader reader = null;

		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;

		    if ((text = reader.readLine()) != null) {
		        currentID = (Integer.parseInt(text));
		        currentUser = arrayUsers.get(currentID);
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
	}
	
	private void storePlayer() {
		Writer file = null;
		try {
			file = new FileWriter("currentPlayerID.txt");
			file.write(new Integer(currentUser.getId()).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(file!=null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void printCurrentUserName() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Resources.FONT_BRODWAY));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 30;
		userFont = generator.generateFont(parameter); 
		generator.dispose();
	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
		userFont.draw(spriteBatch, "Hello, " + currentUser.getName() + " !", 535, 440);
		spriteBatch.end();
		mainStage.draw();

	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		mainStage = new Stage(new ScreenViewport());
		createButton(505, 288, 0);
		createButton(30, 30, 1);
		createButton(505, 370, 2);
		createButton(505, 115, 3);
		createButton(505, 203, 4);
		createButton(980, 30, 5);
		
		Gdx.input.setInputProcessor(mainStage);
		main.play();
		main.play();
		main.setVolume(0.6f);
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

		switch (id) {
		case 0:
			createStartB(x, y, skin);
			mainStage.addActor(startB);
			break;
		case 1:
			createExitB(x, y, skin);
			mainStage.addActor(exitB);
			break;
		case 2:
			createPlayerB(x, y, skin);
			mainStage.addActor(playerB);
			break;
		case 3:
			createHallOfFameB(x, y, skin);
			mainStage.addActor(hallOfFameB);
			break;
		case 4:
			createHowToPlayB(x, y, skin);
			mainStage.addActor(howToPlayB);
			break;
		case 5:
			createCreditsB(x, y, skin);
			mainStage.addActor(creditsB);
			break;
		default:
			break;
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
				button.play();
				main.stop();
				GameScreen gameScreen = new GameScreen(game);
				game.setScreen(gameScreen);
				disableButtons();
			}

		});
	}

	public void createPlayerB(int x, int y, Skin skin) {
		playerB = new TextButton("", skin);
		playerB.setX(x);
		playerB.setY(y);
		playerB.setWidth(270);
		playerB.setHeight(30);
		playerB.setVisible(true);
		playerB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				game.setScreen(newPlayerScreen);
				disableButtons();
			}

		});
	}
	
	public void createHallOfFameB(int x, int y, Skin skin) {
		hallOfFameB = new TextButton("", skin);
		hallOfFameB.setX(x);
		hallOfFameB.setY(y);
		hallOfFameB.setWidth(270);
		hallOfFameB.setVisible(true);
		hallOfFameB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				button.play();
				game.setScreen(hallOfFameScreen);
				disableButtons();
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
				button.play();
				storePlayer();
				main.stop();
				mainStage.dispose();
				game.dispose();
			}

		});
	}
	
	public void createHowToPlayB(int x, int y, Skin skin) {
		howToPlayB = new TextButton("", skin);
		howToPlayB.setX(x);
		howToPlayB.setY(y);
		howToPlayB.setWidth(270);
		howToPlayB.setVisible(true);
		howToPlayB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {				
				button.play();
				game.setScreen(howToPayScreen);
				disableButtons();
			}

		});
	}
	
	public void createCreditsB(int x, int y, Skin skin) {
		creditsB = new TextButton("", skin);
		creditsB.setX(x);
		creditsB.setY(y);
		creditsB.setWidth(270);
		creditsB.setVisible(true);
		creditsB.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {				
				button.play();
				game.setScreen(creditsScreen);
				disableButtons();
			}

		});
	}
	
	private void disableButtons() {
		startB.setDisabled(true);
		playerB.setDisabled(true);
		hallOfFameB.setDisabled(true);
		exitB.setDisabled(true);
		howToPlayB.setDisabled(true);
		creditsB.setDisabled(true);
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public ArrayList<User> getArrayUsers() {
		return arrayUsers;
	}

	public void setArrayUsers(ArrayList<User> arrayUsers) {
		this.arrayUsers = arrayUsers;
	}

	@Override
	public void dispose() {
		storePlayer();
		
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
	public void resume() {
		// TODO Auto-generated method stub

	}

	public void storeScore(int score) {
		Integer currentID = null;
		File file = new File("highscores.txt");
		BufferedReader reader = null;

		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;

		    if ((text = reader.readLine()) != null) {
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
		
		Writer file = null;
		try {
			file = new FileWriter("currentPlayerID.txt");
			file.write((new Integer(currentUser.getId()).toString())+"="+score);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(file!=null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
