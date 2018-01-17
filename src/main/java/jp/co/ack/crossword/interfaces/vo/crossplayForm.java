package jp.co.ack.crossword.interfaces.vo;

import java.util.ArrayList;

import jp.co.ack.crossword.domain.User.User;
import jp.co.ack.crossword.domain.Word.Word;

public class crossplayForm{
	private User user;
	private template temp;
	private String crossWord;
	private String crossWord_wk;
	//private ArrayList<ArrayList<String>> crossWord_list = new  ArrayList<ArrayList<String>>();
	private String[][] crossWord_list;

	//配値済み単語リスト(横方向)
	private ArrayList<Word> wordListCol = new ArrayList<Word>();

	//配値済み単語リスト(縦方向)
	private ArrayList<Word> wordListRow = new ArrayList<Word>();

	public void ini(User user,template temp){

		/*初期値の設定*/
		this.user = user;
		this.temp = temp;
		this.wordListCol = temp.getWordListCol();
		this.wordListRow = temp.getWordListRow();
		this.crossWord = temp.getTemplateToString();
		this.crossWord_list = new String[temp.getHeight()][temp.getWidth()];

		String[] strlist = temp.getTemplateToString().split(temp.getSp2());
		setCrossWord_list_ini(strlist);
	}

	public void setCrossWord_wk(String[] str) {
		this.crossWord_wk = "," + str[0];
		for(int i = 1;i < temp.getSize();i++){
			this.crossWord_wk = "," + str[i];
		}
	}

	public void setCrossWord_list(String[][] str) {
		this.crossWord_list = str;
	}

	public void setCrossWord_list_ini(String[] str) {
		for(int i = 0; i < temp.getHeight();i++){
			for(int j = 0; j < temp.getWidth();j++){
				int index = i * temp.getWidth() + j;
				String str_wk = str[index];
				if(!str_wk.equals(temp.getNoempty())){
					str_wk = "";
				}
				this.crossWord_list[i][j] = str_wk;
			}
		}
	}

	public User getUser() {
		return user;
	}

	public template getTemp() {
		return temp;
	}

	public String getCrossWord() {
		return crossWord;
	}

	public String getCrossWord_wk() {
		return crossWord_wk;
	}

	public String[][] getCrossWord_list() {
		return crossWord_list;
	}


	/*	public ArrayList<ArrayList<String>> getCrossWord_list() {
		return crossWord_list;
	}

	public void setCrossWord_list(ArrayList<ArrayList<String>> crossWord_list) {
		this.crossWord_list = crossWord_list;
	}*/

}
