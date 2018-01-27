package jp.co.ack.crossword.domain.Mst;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mst")
public class Mst implements Serializable {
	private int id;
	private int kbn;
	private int kbn_sub;
	private String kbn_name;
	private String field01;
	private String field02;
	private String field03;

	@Id
	@Column(name = "id")
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
	@Column(name = "kbn_sub")
	public int getKbn_sub() {
		return kbn_sub;
	}

	public void setKbn_sub(int kbn_sub) {
		this.kbn_sub = kbn_sub;
	}

	@Basic
	@Column(name = "kbn_name")
	public String getKbn_name() {
		return kbn_name;
	}

	public void setKbn_name(String kbn_name) {
		this.kbn_name = kbn_name;
	}

	@Basic
	@Column(name = "field01")
	public String getField01() {
		return field01;
	}

	public void setField01(String field01) {
		this.field01 = field01;
	}

	@Basic
	@Column(name = "field02")
	public String getField02() {
		return field02;
	}

	public void setField02(String field02) {
		this.field02 = field02;
	}

	@Basic
	@Column(name = "field03")
	public String getField03() {
		return field03;
	}

	public void setField03(String field03) {
		this.field03 = field03;
	}

}
