package jakoop.com;

public class User {
	private int id;
	private String name;
	private int score;
	private String skin;
	
	public User (int id, String name, String skin) {
		this.id = id;
		this.name = name;
		this.skin = skin;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}
}
