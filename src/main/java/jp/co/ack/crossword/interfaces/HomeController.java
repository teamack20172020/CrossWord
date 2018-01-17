package jp.co.ack.crossword.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ack.crossword.application.CreateService;
import jp.co.ack.crossword.application.PlayService;
import jp.co.ack.crossword.application.TestService;
import jp.co.ack.crossword.application.UserService;

@Controller
@RequestMapping("/")
public class HomeController {

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
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
}