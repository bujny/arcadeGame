package jakoop.com;

public class User {
	private int id;
	private String name;
	private String skin;
	private String song;
	private int score;
	
	public User (int id, String name, String skin, String song) {
		this.id = id;
		this.name = name;
		this.skin = skin;
		this.song = song;
		this.score = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}
	
	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
