package jp.co.ack.crossword.interfaces;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ack.crossword.application.PlayService;
import jp.co.ack.crossword.application.UserService;
import jp.co.ack.crossword.domain.User.User;

@Controller
@RequestMapping("/")
public class PlayController {

	@Autowired
	UserService userService;
	@Autowired
	PlayService playService;

	@GetMapping
	public ModelAndView top() {
		ModelAndView mav = new ModelAndView("userPage");
		return mav;
	}

	/**
	 * トップぺージ
	 */
	@GetMapping("home")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}

	/**
	 * プレイページ
	 */
	@GetMapping("home/play")
	public ModelAndView play() {
		//登録日時の編集
		Date datetime = userService.getCreateDate();
		//新規ユーザー登録
		System.out.println("登録時刻：" + datetime);
		//登録ユーザーの取得
		User j_user = userService.getUserByCreated(datetime);
		ModelAndView mav = new ModelAndView("play");
		mav.addObject("h_user",j_user);
		return mav;
	}

}