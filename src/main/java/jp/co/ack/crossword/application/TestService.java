package jp.co.ack.crossword.application;

import org.springframework.stereotype.Service;


@Service
//public class UserService implements UserDetailsService {
public class TestService {

	/**
	 * テスト用データ作成
	 *
	 */
	public String getCrossWord(){
		String str = "";
		str += "ウ,ニ,■,ク,■,";
		str += "■,ホ,ク,ロ,■,";
		str += "■,ン,■,ム,■,";
		str += "■,ガ,モ,■,■,";
		str += "ア,ミ,モ,ノ,■";
		return str;
	}

}
