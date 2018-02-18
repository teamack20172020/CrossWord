package jp.co.ack.crossword.domain.Crossworditem;

import java.io.Serializable;

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

@Entity
@Table(name = "crossworditem")
public class Crossworditem implements Serializable {
	private int id;
	private int kbn;
	private String word;

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
	@Column(name = "kbn")
	public int getKbn() {
		return kbn;
	}

	public void setKbn(int kbn) {
		this.kbn = kbn;
	}

	@Basic
	@Column(name = "word")
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
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
