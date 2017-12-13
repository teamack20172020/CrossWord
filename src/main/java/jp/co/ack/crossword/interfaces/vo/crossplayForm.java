package jp.co.ack.crossword.interfaces.vo;

import jp.co.ack.crossword.domain.User.User;

public class crossplayForm{
	private User user;
	private String crossWord;

	public crossplayForm(User user,String crossWord){
		this.user = user;
		this.crossWord = crossWord;
	}

	public User getUser() {
		return user;
	}

	public String getCrossWord() {
		return crossWord;
	}

}
