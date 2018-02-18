package jp.co.ack.crossword.domain.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jp.co.ack.crossword.domain.Crossword.Crossword;
import jp.co.ack.crossword.domain.Crosswordplay.Crosswordplay;

@Entity
@Table(name = "user")
public class User implements Serializable {
	private int id;
	private String name;
	private String password;
	private Date created;
	private Date updated;
	@JsonIgnore
	private List<Crossword> crossWord;
	private List<Crosswordplay> crossWordplay;

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

	@OneToMany(mappedBy = "user")
	public List<Crossword> getCrossWord() {
		return crossWord;
	}

	public void setCrossWord(List<Crossword> crossWord) {
		this.crossWord = crossWord;
	}

	@OneToMany(mappedBy = "user")
	public List<Crosswordplay> getCrossWordplay() {
		return crossWordplay;
	}

	public void setCrossWordplay(List<Crosswordplay> crossWordplay) {
		this.crossWordplay = crossWordplay;
	}
}
