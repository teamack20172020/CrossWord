package jp.co.ack.crossword.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ack.crossword.application.GameService;
import jp.co.ack.crossword.application.PlayService;
import jp.co.ack.crossword.application.UserService;

@Controller
@RequestMapping("/")
public class PlayPageController {

	@Autowired
	UserService userService;
	@Autowired
	PlayService playService;
	@Autowired
	GameService playService2;

	@GetMapping
	public ModelAndView home() {
		//playService.create();

		ModelAndView mav = new ModelAndView("userPage");
		return mav;
	}

}