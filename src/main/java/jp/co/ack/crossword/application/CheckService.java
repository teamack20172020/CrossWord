package jp.co.ack.crossword.application;

import org.springframework.stereotype.Service;

import jp.co.ack.crossword.interfaces.vo.crossplayForm;

@Service
public class CheckService {

	/**
	 * 結果確認処理
	 */
	public boolean check(crossplayForm j_from){
		System.out.println("結果確認処理の開始");
			String[] strRightAnswer = j_from.getCrossWord().split(",");
			String[] strAnswer = j_from.getCrossWord_wk().split(",");
			for(int i = 0;i < strAnswer.length;i++){
				if(!strRightAnswer[i].equals(strAnswer[i])){
					return false;
				}
			}
		System.out.println("結果確認処理の終了");
			return true;
	}
}
