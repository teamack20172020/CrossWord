package jp.co.ack.crossword.interfaces;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ack.crossword.application.CreateService;
import jp.co.ack.crossword.application.PlayService;
import jp.co.ack.crossword.application.TestService;
import jp.co.ack.crossword.application.UserService;
import jp.co.ack.crossword.domain.User.User;
import jp.co.ack.crossword.interfaces.vo.crossplayForm;
import jp.co.ack.crossword.interfaces.vo.template;

@Controller
@RequestMapping("/crossword")
public class CrossWordController {

	@Autowired
	UserService userService;
	@Autowired
	CreateService createService;

	@Autowired
	PlayService playService;
	@Autowired
	TestService testService;

	/**
	 * トップぺージ
	 *
	 */
	@GetMapping
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("homeCrossWord");
		return mav;
	}

	/**
	 * プレイページ
	 */
	@GetMapping("play")
	public ModelAndView play() {
		//新規ユーザー登録・取得
		Date datetime = userService.getCreateDate();
		User user = userService.getUserByCreated(datetime);

		//クロスワードの自動生成
		template temp = createService.create(7,7,50);

		//ページの描画
		crossplayForm j_from = new crossplayForm();
		j_from.ini(user, temp);
		ModelAndView mav = new ModelAndView("playCrossWord");
		mav.addObject("h_from",j_from);
		return mav;
	}

	/**
	 * クロスワード結果確認
	 */
	@GetMapping("play/check")
	public ModelAndView croCheck() {
		ModelAndView mav = new ModelAndView("play");
		return mav;
	}

}