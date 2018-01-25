package jp.co.ack.crossword.application;

import org.springframework.stereotype.Service;

import jp.co.ack.crossword.interfaces.vo.crossplayForm;
import jp.co.ack.crossword.interfaces.vo.template;

@Service
public class PlayService {

	/**
	 * 結果確認処理
	 */
	public boolean checkCrossWord(crossplayForm j_from,template temp){
		String[] str = temp.getTemplateToString().split(temp.getSp2());
		for(int i=0;i < j_from.getCrossWord_res().length;i++){
			if(!str[i].equals(j_from.getCrossWord_res()[i])){
				return false;
			}
		}
			return true;
	}
}
