package jp.co.ack.crossword.interfaces.vo;

import java.util.ArrayList;

import jp.co.ack.crossword.domain.Word.Word;

public class template{

	private String empty = "□";
	private String noempty = "■";
	private String sp1 = "\r\n";
	private String sp2 = ",";

	//マス目総数
	private int size;
	//一辺の最大値
	private int max;
	//幅
	private int width;
	//高さ
	private int height;
	//初期開始可能位置リスト[上下左右の四隅]
	private int[] enptylist;
	//クロスワード文字列
	private String templateToString;
	//クロスワード配列
	private String[] template;

	//配値可能リスト(横方向)
	private ArrayList<Integer> pointListCol = new ArrayList<Integer>();

	//配値可能リスト(縦方向)
	private ArrayList<Integer> pointListRow = new ArrayList<Integer>();

	//配値開始可能リスト(横方向)
	private ArrayList<Integer> pointListCol_Root = new ArrayList<Integer>();

	//配値開始可能リスト(縦方向)
	private ArrayList<Integer> pointListRow_Root = new ArrayList<Integer>();

	//単語リスト
	ArrayList<Word> wordlist;

	//配値済み単語リスト(横方向)
	private ArrayList<Word> wordListCol = new ArrayList<Word>();

	//配値済み単語リスト(縦方向)
	private ArrayList<Word> wordListRow = new ArrayList<Word>();

	//初期値の設定
	public template(int width, int height){
		this.width = width;
		this.height = height;
		this.size = width * height;
		this.template = new String[size];
		this.enptylist = new int[4];
		enptylist[0] = 0;
		enptylist[1] = this.width-1;
		enptylist[2] = size-this.width;
		enptylist[3] = size-1;
		if(width >= height){
			max = width;
		}else{
			max =height;
		}

		for(int i =0; i < this.size;i++){
			pointListCol.add(i);
			pointListRow.add(i);
		}
	}

	/*
	 * 値のセット
	 */
	public void setTemplate(int i,String str) {
		this.template[i] = str;
	}

	public void setPointList_Root(int i,boolean flg) {
		if(flg){
			this.pointListCol_Root.add(i);
		}else{
			this.pointListRow_Root.add(i);
		}
	}

	public void setWordlist(ArrayList<Word> wordlist) {
		this.wordlist = new ArrayList<Word>(wordlist);
	}

	public void setWord(Word word,boolean flg) {
		Word word_wk = new Word();
		word_wk.setWord(word);
		if(!flg){
			this.wordListCol.add(word_wk);
		}else{
			this.wordListRow.add(word_wk);
		}
	}

	/*
	 * 値の追加
	 */
	public void addWordlist(Word word) {
		this.wordlist.add(word);
	}

	/*
	 * 値の取得
	 */
	public String getEmpty() {
		return empty;
	}

	public String getNoempty() {
		return noempty;
	}

	public String getSp1() {
		return sp1;
	}

	public String getSp2() {
		return sp2;
	}

	public int getSize() {
		return size;
	}

	public int getMax() {
		return max;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getEnptylist() {
		return enptylist;
	}

	public String[] getTemplate() {
		return template;
	}

	public String getTemplateToString() {
		String[] temp = this.template;
		String str = "";
		str += temp[0];
		for(int i = 1; i < temp.length;i++){
			str += ",";
			str += temp[i];
		}

		return str.replace(empty,noempty);
	}

	public ArrayList<Integer> getPointList(boolean flg) {
		if(flg){
			return pointListCol;
		}else{
			return pointListRow;
		}
	}

	public ArrayList<Integer> getPointList_Root(boolean flg) {
		if(flg){
			return pointListCol_Root;
		}else{
			return pointListRow_Root;
		}
	}

	public ArrayList<Word> getWordlist() {
		return wordlist;
	}

	public ArrayList<Word> getWordListCol() {
		return wordListCol;
	}

	public ArrayList<Word> getWordListRow() {
		return wordListRow;
	}

	/*
	 * 値の削除
	 */
	public void deletePoint(int i,boolean flg) {
		int index;
		if(flg){
			index = pointListCol.indexOf(i);
			if(index >= 0){
				this.pointListCol.remove(index);
			}
		}else{
			index = pointListRow.indexOf(i);
			if(index >= 0){
				this.pointListRow.remove(index);
			}
		}
	}

	public void deletePoint_Root(int i,boolean flg) {
		int index;
		if(flg){
			index = pointListCol_Root.indexOf(i);
			if(index >= 0){
				this.pointListCol_Root.remove(index);
			}
		}else{
			index = pointListRow_Root.indexOf(i);
			if(index >= 0){
				this.pointListRow_Root.remove(index);
			}
		}
	}

	public void deletePointList(boolean flg,ArrayList<Integer> pointList) {
		for(int i = 0; i < pointList.size();i++){
			deletePoint(pointList.get(i),flg);
		}
	}

	public void deleteWordlist(Word word) {
		this.wordlist.remove(wordlist.indexOf(word));
	}

}
