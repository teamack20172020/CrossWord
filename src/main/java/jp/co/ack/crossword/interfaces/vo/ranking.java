package jp.co.ack.crossword.interfaces.vo;

public class ranking{

	private int id;
	private int score;
	private int ranking;
	private String name;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}