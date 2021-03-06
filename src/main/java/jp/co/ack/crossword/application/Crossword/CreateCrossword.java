package jp.co.ack.crossword.application.Crossword;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ack.crossword.domain.Word.Word;
import jp.co.ack.crossword.interfaces.vo.template;

@Component
public class CreateCrossword{

	@Autowired
	WordSetting ws;

	@Autowired
	RootSetting rs;

	// 方向
	public enum Direction{
		Up,
		Right,
		Down,
		Left;
	}

	/**
	 * @see [概要] メイン処理
	 * @param item
	 * @param temp
	 */
	public template create(int width,int height,int average) {

		//対象となる文字リストの取得
		int max  = width;
		if(max < width){
			max = height;
		}

		ArrayList<Word> wordlist = ws.getWordList(max);

		while(true){
			//初期化
            template temp = new template(width,height);
			init(temp,wordlist);

			//作成
			main(temp);

			//空白マス[■]のみで構成される列・行の存在確認
			if(checkEmpAll(temp)){
				//文字の使用率率が指標以上であるか確認
				temp.setUtilization(temp);
				if(temp.getUtilization() >= average){
					return temp;
				}
			}
		}
	}

	/**
	 * @see [概要] 初期化
	 * @param temp
	 */
	private void init(template temp,ArrayList<Word> wordlist){

		//退避領域の初期化
		for (int i = 0; i < temp.getSize(); i++){
			temp.setTemplate(i, temp.getNoempty());
		}
		temp.setWordlist(wordlist);
	}

	private void main(template temp){

		Random rnd = new Random();

		int index = rnd.nextInt(temp.getEnptylist().length);
		boolean flg;
		switch(rnd.nextInt()%2){
		case 0:
			flg = true;
			break;
		default:
			flg = false;
			break;
		}
		create(temp,flg,temp.getEnptylist()[index]);
	}

	/**
	 * @see [概要] クロスワード作成処理
	 * @param temp
	 * @param item
	 * @param flg :縦方向：true 横方向：false
	 * @param sp  :開始位置
	 */
	private void create(template temp,boolean flg,int sp){

		int start = sp;

		//配置可能方向を取得
		ArrayList<Direction> rootList = new ArrayList<Direction>();
		rs.getRootList(start,flg,rootList,temp);

		//配置可能方向の存在チェック
		int[] next;
		ArrayList<Word> wordList;
		boolean res = false;
		switch (rootList.size()) {
		case 1:
			//配置可能な最大文字数と移動サイズの取得
			next = rs.setRoot(start,rootList,temp);

			wordList = new ArrayList<Word>(temp.getWordlist());

			//配置可能文字の配備
			res = ws.setWord(start,next,flg,wordList,temp);

			//配置可能文字が存在すれば縦横フラグを反転
			if(res){
				flg = !flg;
			}
			break;
		case 2:
			//配置可能な最大文字数と移動サイズの取得
			next = rs.setRoot(start,rootList,temp);

			//単語候補が0の場合は処理を終了する
			if(temp.getWordlist().size() == 0){
				break;
			}
			wordList = new ArrayList<Word>(temp.getWordlist());

			//配置可能文字の配備
			res = ws.setWord(start,next,flg,wordList,temp);

			//配置可能文字が存在すれば縦横フラグを反転
			if(res){
				flg = !flg;
			}else{
				//配置可能な最大文字数と移動サイズの取得
				next = rs.setRoot(start,rootList,temp);
				wordList = new ArrayList<Word>(temp.getWordlist());

				//配置可能文字の配備
				res = ws.setWord(start,next,flg,wordList,temp);

				//配置可能文字が存在すれば縦横フラグを反転
				if(res){
					flg = !flg;
				}
			}
			break;
		default:
			break;
		}

		//次の配置開始位置を取得
		start = rs.getStart(flg,temp);
		if(start >= 0){
			create(temp,flg,start);
		}else{
			start = rs.getStart(!flg,temp);
			if(start >= 0){
				create(temp,!flg,start);
			}
		}
	}

	/**
	 * @see [概要] 空白マス[■]のみで構成される列・行の存在確認
	 * @param temp
	 * @retur 比較結果：成功[true] 失敗[false]
	 */
	private boolean checkEmpAll(template temp){

		boolean res = false;
		String[] str = temp.getTemplateToString().split(temp.getSp2());

		int start;
		int next;
		int max;

		//一行目
		start = 0;
		next = 1;
		max = temp.getWidth();
		boolean res01 = checkEmp(str,start,next,max,temp);

		//一列目
		start = 0;
		next = temp.getWidth();
		max = temp.getSize();
		boolean res02 = checkEmp(str,start,next,max,temp);

		//最終行
		start = temp.getSize()-temp.getWidth();
		next = 1;
		max = temp.getSize();
		boolean res03 = checkEmp(str,start,next,max,temp);

		//最終列
		start = temp.getWidth()-1;
		next = temp.getWidth();
		max = temp.getSize();
		boolean res04 = checkEmp(str,start,next,max,temp);

		if(res01&&res02&&res03&&res04){
			res = true;
		}
		return res;
	}

	/**
	 * @param str  :確認対象文字列
	 * @param start:比較開始位置
	 * @param next :比較位置移動係数
	 * @param max  :比較終了位置
	 * @param temp
	 * @retur 比較結果：成功[true] 失敗[false]
	 */
	private boolean checkEmp(String[] str,int start,int next,int max,template temp){
		boolean res = false;
		for(int i = start;i < max;i+=next){
			if(!str[i].equals(temp.getNoempty())){
				res = true;
				break;
			}
		}
		return res;
	}

}
