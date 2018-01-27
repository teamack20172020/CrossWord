package jp.co.ack.crossword.domain.User;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {
	private int id;
	private String name;
	private String password;
	private int  missCnt;
	private long playTime;
	private long score;
	private Date created;
	private Date updated;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	public long getPlayTime() {
		return playTime;
	}

	public void setPlayTime(long playTime) {
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
	@Column(name = "Created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Basic
	@Column(name = "Updated")
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date
			updated) {
		this.updated = updated;
	}
}
