package jp.co.ack.crossword.domain.Crosswordplay;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jp.co.ack.crossword.domain.Crossword.Crossword;
import jp.co.ack.crossword.domain.User.User;

@Entity
@Table(name = "crosswordplay")
public class Crosswordplay implements Serializable {
	private int id;
	private int missCnt;
	private double playTime;
	private long score;
	private boolean completeFlg;
	private Date created;

	private User user;
	private Crossword crossword;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "missCnt")
	public int getMissCnt() {
		return missCnt;
	}

	public void setMissCnt(int missCnt) {
		this.missCnt = missCnt;
	}

	@Basic
	@Column(name = "playTime")
	public double getPlayTime() {
		return playTime;
	}

	public void setPlayTime(double playTime) {
		this.playTime = playTime;
	}

	@Basic
	@Column(name = "score")
	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	@Basic
	@Column(name = "completeFlg")
	public boolean isCompleteFlg() {
		return completeFlg;
	}

	public void setCompleteFlg(boolean completeFlg) {
		this.completeFlg = completeFlg;
	}

	@Basic
	@Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "crossword_id", referencedColumnName = "id", nullable = false)
	public Crossword getCrossword() {
		return crossword;
	}

	public void setCrossword(Crossword crossword) {
		this.crossword = crossword;
	}

}
