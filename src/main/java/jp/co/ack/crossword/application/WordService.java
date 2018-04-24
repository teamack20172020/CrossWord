package jp.co.ack.crossword.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ack.crossword.domain.Word.Word;
import jp.co.ack.crossword.domain.Word.WordRepository;
import jp.co.ack.crossword.interfaces.vo.template;

@Service
public class WordService {

	@Autowired
	WordRepository wrep;
	@PersistenceContext
	EntityManager entityManager;

	public ArrayList<Word> getWordList(int size){
		return (ArrayList<Word>) wrep.findBySize(size);
	}

	public List<Word> getWordList(int size,String key,ArrayList<Word> delwordlist){

		String delword  = "''";
		for(int i = 0; i < delwordlist.size();i++){
			delword += ",'"+delwordlist.get(i).getWord()+"'";
		}

		StringBuffer getWordListSQL = new StringBuffer();
		getWordListSQL.append("select ");
		getWordListSQL.append(" * ");
		getWordListSQL.append("from ");
		getWordListSQL.append(" word main ");
		getWordListSQL.append("where ");
		getWordListSQL.append("main.size <= " + size + " and " );
		getWordListSQL.append("main.word like '" + key + "%' and ");
		getWordListSQL.append("main.word not in (" + delword + ") ");
		//System.out.println(getWordListSQL.toString());
		// クエリの生成
		Query q = entityManager.createNativeQuery(getWordListSQL.toString(), Word.class);
		// 抽出
		@SuppressWarnings("unchecked")
		List<Word> wordList = q.getResultList();
		return wordList;
	}

	/**
	 * @see [概要] 単語の配置
	 * @param start   :配置開始位置
	 * @param next    :移動サイズ [0] 配置可能な最大文字数 [1]
	 * @param flg     :縦方向：true 横方向：false
	 * @param wordList:単語リスト
	 * @param temp
	 * @param item
	 * @return 	単語の配置結果 [成功:ture 失敗:false]
	 */
	public boolean setWord(int start,int[] next,boolean flg,ArrayList<Word> wordList,template temp){

		//単語リストを取得
		ArrayList<Word> wordListwk = new ArrayList<Word>(wordList);

		//文字一致条件を満たすか、リストが空になるまで実行
		while(wordListwk.size() > 0){

			//単語をランダムに取得
			Word wordwk = getWord(wordListwk);
			if(wordwk == null){
				break;
			}

			//使用済み位置リストの初期化
			ArrayList<Integer> delpoint = new ArrayList<Integer>();

			//ランダムに取得した単語が配置可能か確認
			int cnt = checkWord(start,next,flg,delpoint,wordwk,temp);

			//単語の開始前・終了後位置の算出
			int index = start;
			int startPoint = -1;
			int endPoint = -1;

			if(flg){
				if(next[0] < 0){
					index += next[0] * (wordwk.getSize()-1);
				}
				int y = index / temp.getWidth();
				if(y > 0){
					startPoint = index-temp.getWidth();
				}
				y += wordwk.getSize();
				if(y < temp.getHeight()){
					endPoint = index+(temp.getWidth()*wordwk.getSize());
				}
			}else{
				int x = index % temp.getWidth();
				if(x > 0){
					startPoint = index-1;
				}
				x += wordwk.getSize();
				if(x < temp.getWidth()){
					endPoint = index + wordwk.getSize();
				}
			}

			//単語の前後位置が配置可能か確認
			if(checkFromTo(startPoint,endPoint,cnt,wordwk.getSize(),flg,temp)){
				//すべての条件をクリアした単語を配置する
				setWord(start,startPoint,endPoint,next, flg, delpoint, wordwk, temp);
				return true;
			}
			//確認済み単語の削除
			wordListwk.remove(wordListwk.indexOf(wordwk));
		}
		return false;
	}

	/**
	 * @see [概要] 単語が配置可能か確認
	 * @param start   :配置開始位置
	 * @param next    :移動サイズ [0] 配置可能な最大文字数 [1]
	 * @param delpoint:使用済み位置リスト
	 * @param word
	 * @param temp
	 * @return 配置可能文字数
	 */
	private int checkWord(int start,int[] next,boolean flg,ArrayList<Integer> delpoint,Word word,template temp){
		int cnt = 0;
		int Index = word.getSize();
		String[] str = word.getWord().split("");

		//開始位置を使用済み位置リストに追加
		delpoint.add(start);

		int point = start;
		while(cnt < word.getSize() && word.getSize() <= next[1]){
			//確認位置の移動サイズを設定
			if(next[0] > 0){
				Index = cnt;
			}else{
				Index--;
			}

			//対象の文字と配置予定位置の文字を取得
			String checkWord = str[Index];
			String target = temp.getTemplate()[point];

			// 以下の全て条件に一致するか確認
			// 配置予定位置の文字が"□"でない
			// 配置可能リストに配置予定位置が存在する
			// 対象の文字と配置予定位置の文字が一致する、もしくは"■"である
			boolean checkFlg = false;
			if(!target.equals(temp.getEmpty())){
				if(temp.getPointList(!flg).indexOf(point) >= 0){
					if(target.equals(temp.getNoempty())
							|| checkWord.equals(temp.getTemplate()[point])){
						//使用済みリストに配置予定位置を追加する
						if(flg){
							getDelpoint(point,temp.getHeight(),delpoint, temp);
						}else{
							getDelpoint(point,temp.getWidth(),delpoint, temp);
						}
						//次の配置予定位置を算出
						point = point + next[0];
						checkFlg = true;
					}
				}
			}

			//配置結果を確認
			if(!checkFlg){

				break;
			}
			//配置可能な文字数をカウントアップ
			cnt++;
		}
		return cnt;
	}

	/**
	 * @see [概要] 使用済みリストに配置予定位置を追加する
	 * @param point   :配置予定位置
	 * @param next    :移動サイズ
	 * @param delpoint:使用済み位置リスト
	 * @param temp
	 */
	private void getDelpoint(int point,int next,ArrayList<Integer> delpoint,template temp){

		delpoint.add(point - next);
		delpoint.add(point + next);

		if(((point % temp.getWidth()) - 1) >= 0){
			delpoint.add(point - 1);
		}

		if(((point % temp.getWidth()) + 1) < temp.getWidth()){
			delpoint.add(point + 1);
		}

	}

	/**
	 * @see [概要] 単語の開始前・終了後の文字未配置確認
	 * @param fromb :単語の開始前位置
	 * @param to    :単語の終了後位置
	 * @param cnt   :配置可能な文字数
	 * @param size  :単語の文字数
	 * @param flg   :縦方向：true 横方向：false
	 * @param temp
	 * @return 	単語の配置結果 [成功:ture 失敗:false]
	 */
	private boolean checkFromTo(int from,int to,int cnt ,int size,boolean flg,template temp){
		//配置可能な文字数と単語の文字数が一致するか確認
		if(cnt != size){
			return false;
		}
		//単語の開始前が"□"、もしくは"■"であるか確認
		if(from > 0 &&
				!temp.getTemplate()[from].equals(temp.getEmpty()) &&
				!temp.getTemplate()[from].equals(temp.getNoempty())
				){
			return false;
		}
		//単語の終了後が"□"、もしくは"■"であるか確認
		if(to > 0 &&
				!temp.getTemplate()[to].equals(temp.getEmpty()) &&
				!temp.getTemplate()[to].equals(temp.getNoempty())
				){
			return false;
		}
		return true;
	}

	/**
	 * @see [概要] 条件に一致する単語の配置
	 * @param start    :配置開始位置
	 * @param startEmp :単語の開始前位置
	 * @param endEmp   :単語の終了後位置
	 * @param next     :移動サイズ [0] 配置可能な最大文字数 [1]
	 * @param delpoint :使用済み位置リスト
	 * @param word
	 * @param temp
	 * @param item
	 */
	public void setWord(int start,int startEmp,int endEmp,int[] next,boolean flg,ArrayList<Integer> delpoint,Word word,template temp){

		//単語の開始前・終了後の"□"を配置
		if(startEmp > 0){
			setEnpty(startEmp,temp.getEmpty(),flg,temp);
			temp.deletePoint(startEmp, flg);
		}
		if(endEmp > 0 ){
			setEnpty(endEmp,temp.getEmpty(),flg,temp);
			temp.deletePoint(endEmp, flg);
		}


		//単語を配置
		int cnt = 0;
		int Index = word.getSize();

		String[] str = word.getWord().split("");

		int point = start;
		while(cnt < word.getSize() && word.getSize() <= next[1]){
			//確認位置の移動サイズを設定
			if(next[0] > 0){
				Index = cnt;
			}else{
				Index--;
			}
			setEnpty(point,str[Index],flg,temp);
			point = point + next[0];
			cnt++;
		}

		//使用した単語を配値済み単語リストに格納
		temp.setWord(word, flg);
		//使用済み単語の削除
		//temp.deleteWordlist(word);
		//使用済み位置を配置可能リストから削除
		temp.deletePointList(!flg,delpoint);
	}

	/**
	 * @see [概要] 条件に一致している文字の配置
	 * @param start :配置位置
	 * @param str   :配置文字
	 * @param flg   :縦方向：true 横方向：false
	 * @param temp
	 */
	public void setEnpty(int start,String str,boolean flg,template temp){
		//配置文字が初期値でない、かつ配置位置が配列サイズより小さいか確認
		if(!(str.equals(temp.getEmpty()) && start > temp.getTemplate().length)){
			temp.setTemplate(start, str);
		}

		if(!str.equals(temp.getEmpty())){
			if(flg){
				int y = start / temp.getWidth();
				if(y%2==0){
					temp.setPointList_Root(start,!flg);
				}
			}else{
				int x = start % temp.getWidth();
				if(x%2==0){
					temp.setPointList_Root(start,!flg);
				}
			}
		}
	}

	/**
	 * @see [概要] 単語をランダムに取得
	 * @param wordList :単語リスト
	 * @return ランダムに取得した単語
	 */
	private Word getWord(ArrayList<Word> wordList){
		if(wordList.size() < 0){
			return null;
		}
		Random rnd = new Random();
		int index = rnd.nextInt(wordList.size());
		return wordList.get(index);
	}
}