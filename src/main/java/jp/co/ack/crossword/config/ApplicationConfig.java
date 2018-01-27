package jp.co.ack.crossword.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class ApplicationConfig {

	//共通
	public static final String SPLIT_CRLF = "\r\n";
	public static final String SPLIT_COMMA = ",";
	public static final String GAME_CROSSWORD = "1";



	//DB[汎用マスタテーブル]
	public static final int MST_CROSSWORD_CNT_KBN = 1;
	public static final int MST_CROSSWORD_TIM_KBN = 2;

	//GAME[クロスワード]
	public static final  String GAME_CROSSWORD_EMPTY = "□";
	public static final  String GAME_CROSSWORD_NOEMPTY = "■";


}
