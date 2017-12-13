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
	private int  missCnt;
	private long playTime;
	private String Name;
	private String password;
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
	@Column(name = "MissCnt")
	public int getMissCnt() {
		return missCnt;
	}

	public void setMissCnt(int missCnt) {
		this.missCnt = missCnt;
	}

	@Basic
	@Column(name = "PlayTime")
	public long getPlayTime() {
		return playTime;
	}

	public void setPlayTime(long playTime) {
		this.playTime = playTime;
	}

	@Basic
	@Column(name = "Name")
	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
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
