package jp.co.ack.crossword.interfaces.vo;

import java.util.ArrayList;

import jp.co.ack.crossword.domain.User.User;
import jp.co.ack.crossword.domain.Word.Word;

public class crossplayForm{
	private int width;
	private int height;
	private long time;
	private String crossWord;
	private String crossWord_wk;
	private String[] crossWord_res;
	private ArrayList<Word> wordListCol = new ArrayList<Word>();
	private ArrayList<Word> wordListRow = new ArrayList<Word>();

	private User user = new User();

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	public String getCrossWord() {
		return crossWord;
	}

	public void setCrossWord(String crossWord) {
		this.crossWord = crossWord;
	}

	public String getCrossWord_wk() {
		return crossWord_wk;
	}

	public void setCrossWord_wk(String crossWord_wk) {
		this.crossWord_wk = crossWord_wk;
	}

	public String[] getCrossWord_res() {
		return crossWord_res;
	}

	public void setCrossWord_res(String[] crossWord_res) {
		this.crossWord_res = crossWord_res;
	}

	public ArrayList<Word> getWordListCol() {
		return wordListCol;
	}

	public void setWordListCol(ArrayList<Word> wordListCol) {
		this.wordListCol = wordListCol;
	}

	public ArrayList<Word> getWordListRow() {
		return wordListRow;
	}

	public void setWordListRow(ArrayList<Word> wordListRow) {
		this.wordListRow = wordListRow;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStrCro_reload(String[] str,template temp){
		String crossWord = temp.getEmpty();

		if(str[0] != null){
			crossWord = str[0];
		}

		for(int i = 1; i < str.length;i++){
			if(str[i] != null){
				crossWord += "," + str[i];
			}else{
				crossWord += "," + temp.getEmpty();
			}
		}
		return crossWord;
	}

	public String getStrCro(template temp){

		String[] str = temp.getTemplateToString().split(",");

		for(int i = 0; i < str.length;i++){
			if(!str[i].equals(temp.getNoempty())){
				str[i] = temp.getEmpty();
			}
		}

		String crossWord = str[0];
		for(int i = 1; i < str.length;i++){
			crossWord += "," + str[i];
		}

		return crossWord;
	}

	public void setini(template temp,String str,long time){
		this.time = time;
		this.width = temp.getWidth();
		this.height = temp.getHeight();
		this.wordListCol = temp.getWordListCol();
		this.wordListRow = temp.getWordListRow();
		this.crossWord_wk = str;
	}

}
