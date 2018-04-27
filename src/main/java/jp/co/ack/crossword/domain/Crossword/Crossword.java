package jp.co.ack.crossword.domain.Crossword;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jp.co.ack.crossword.domain.Crossworditem.Crossworditem;
import jp.co.ack.crossword.domain.User.User;

@Entity
@Table(name = "crossword")
public class Crossword implements Serializable {
	private int id;
	private int size;
	private int width;
	private int height;
	private int imgno;
	private double utilization;
	private int level;
	private int kbn;
	private String template;
	private String template_view;
	private int clearcnt;
	private Date created;

	private User user;
	private List<Crossworditem> crossworditem;

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
	@Column(name = "size")
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Basic
	@Column(name = "width")
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Basic
	@Column(name = "height")
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Basic
	@Column(name = "imgno")
	public int getImgno() {
		return imgno;
	}

	public void setImgno(int imgno) {
		this.imgno = imgno;
	}

	@Basic
	@Column(name = "utilization")
	public double getUtilization() {
		return utilization;
	}

	public void setUtilization(double utilization) {
		this.utilization = utilization;
	}

	@Basic
	@Column(name = "level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Basic
	@Column(name = "clearcnt")
	public int getClearcnt() {
		return clearcnt;
	}

	public void setClearcnt(int clearcnt) {
		this.clearcnt = clearcnt;
	}

	@Basic
	@Column(name = "template")
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Basic
	@Column(name = "template_view")
	public String getTemplate_view() {
		return template_view;
	}

	public void setTemplate_view(String template_view) {
		this.template_view = template_view;
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

	@OneToMany(mappedBy = "crossword", cascade = CascadeType.ALL)
	public List<Crossworditem> getCrossworditem() {
		return crossworditem;
	}

	public void setCrossworditem(List<Crossworditem> crossworditem) {
		this.crossworditem = crossworditem;
	}

}
