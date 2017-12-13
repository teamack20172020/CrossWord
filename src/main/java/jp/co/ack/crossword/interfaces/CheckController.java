package jp.co.ack.crossword.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ack.crossword.application.PlayService;
import jp.co.ack.crossword.application.UserService;

@Controller
@RequestMapping("/home/play/")
public class CheckController {

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
	 * クロスワード結果確認
	 */
	@GetMapping("check")
	public ModelAndView croCheck() {
		ModelAndView mav = new ModelAndView("play");
		return mav;
	}
}