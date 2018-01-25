package jp.co.ack.crossword.interfaces;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.ack.crossword.application.CreateService;
import jp.co.ack.crossword.application.PlayService;
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
	HttpSession session;

	/**
	 * トップぺージ
	 *
	 */
	@GetMapping
	public ModelAndView home() {
		//セッションクリア
		session.invalidate();
		//ページの描画
		ModelAndView mav = new ModelAndView("homeCrossWord");
		return mav;
	}

	/**
	 * プレイページ
	 */
	@GetMapping("play")
	public ModelAndView play(Model model) {
		int userId;
		String str;


		//画面情報の取得
		User j_user = new User();
		crossplayForm j_from = new crossplayForm();
		template j_temp = (template) session.getAttribute("template");
		if(j_temp==null){
			//クロスワードの自動生成
			j_temp = createService.create(7,7,50);

			// セッションへ保存
			session.setAttribute("template", j_temp);

			str = j_from.getStrCro(j_temp);

			//新規ユーザー登録・取得
			Date datetime = userService.getCreateDate();
			userService.saveUser(datetime,true);
			j_user = userService.getUserByCreated(datetime);
			userId = j_user.getId();
			j_user.setPlayTime(0);
		}else{
			userId = (int) model.asMap().get("userId");
			str = (String) model.asMap().get("crossWord_wk");
		}
		j_user = userService.getUserById(userId);
		j_from.setini(j_temp,str,j_user.getPlayTime());
		j_from.setUser(j_user);

		//ページの描画
		ModelAndView mav = new ModelAndView("playCrossWord");
		mav.addObject("h_user",j_user);
		mav.addObject("h_from",j_from);
		return mav;
	}

	/**
	 * クロスワード結果確認
	 */
	@GetMapping("play/check")
	public String croCheck(RedirectAttributes attributes,crossplayForm j_from) {

		String url = "";

		//セッションの取得
		template j_temp = (template) session.getAttribute("template");
		//解答の確認
		boolean res = playService.checkCrossWord(j_from,j_temp);
		System.out.println(j_from.getTime());
		userService.updateUser(j_from.getUser().getId(),j_from.getTime(),res);
		if(res){
			url = "redirect:/crossword/play/rank";
		}else{
			String str = j_from.getStrCro_reload(j_from.getCrossWord_res(),j_temp);
			attributes.addFlashAttribute("crossWord_wk",str);
			attributes.addFlashAttribute("userId",j_from.getUser().getId());
			url = "redirect:/crossword/play";
		}
		return url;
	}

	/**
	 * クロスワードランキング表示
	 */
	@GetMapping("play/rank")
	public ModelAndView rank() {
		//セッションクリア
		session.invalidate();
		//ページの描画
		ModelAndView mav = new ModelAndView("rankCrossWord");
		return mav;
	}
}