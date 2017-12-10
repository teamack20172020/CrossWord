package jp.co.ack.crossword.domain.Word;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "word")
public class Word implements Serializable {
	private int id;
	private int cnt;
	private int kbn;
	private String word;


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
	@Column(name = "cnt")
	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
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
}
