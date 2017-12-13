package jp.co.ack.crossword.interfaces;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ack.crossword.application.PlayService;
import jp.co.ack.crossword.application.TestService;
import jp.co.ack.crossword.application.UserService;
import jp.co.ack.crossword.domain.User.User;
import jp.co.ack.crossword.interfaces.vo.crossplayForm;

@Controller
@RequestMapping("/")
public class PlayController {

	@Autowired
	UserService userService;
	@Autowired
	PlayService playService;
	@Autowired
	TestService testService;

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
		//新規ユーザー登録・取得
		Date datetime = userService.getCreateDate();
		User user = userService.getUserByCreated(datetime);
		crossplayForm j_from = new crossplayForm(user,testService.getCrossWord());
		//クロスワードの自動生成
		ModelAndView mav = new ModelAndView("userPage2-2");
		mav.addObject("h_from",j_from);
		return mav;
	}

}