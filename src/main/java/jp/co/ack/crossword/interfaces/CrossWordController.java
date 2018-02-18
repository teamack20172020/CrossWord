package jp.co.ack.crossword.interfaces;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.ack.crossword.application.UserService;
import jp.co.ack.crossword.application.CrosswordService.CrossWordService;
import jp.co.ack.crossword.domain.Crossword.Crossword;
import jp.co.ack.crossword.domain.Crossworditem.Crossworditem;
import jp.co.ack.crossword.domain.Crosswordplay.Crosswordplay;
import jp.co.ack.crossword.domain.User.User;
import jp.co.ack.crossword.interfaces.vo.ranking;

@Controller
@RequestMapping("/crossword")
public class CrossWordController {

	@Autowired
	UserService userService;
	@Autowired
	CrossWordService crosswordService;

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

		User j_user = new User();
		Crossword j_crossword = new Crossword();
		Crosswordplay j_playinfo = new Crosswordplay();

		String str = String.valueOf(model.asMap().get("playId"));
		if(str == null || str.equals("null") || str.equals("")){
			//新規ユーザー登録・取得
			j_user = userService.createUser();
			//プレイ情報の取得
			str = String.valueOf(crosswordService.main(j_user));
			// セッションへ保存
			j_playinfo = crosswordService.findByCrosswordIdandUserId(j_user.getId(),Integer.valueOf(str));
			j_crossword = crosswordService.findById(j_playinfo.getCrossword().getId());
			j_playinfo.setPlayTime(0);
		}else{
			j_playinfo =
					crosswordService.PlayInfofindById((int) model.asMap().get("playId"));
			j_crossword = crosswordService.findById(j_playinfo.getCrossword().getId());
			j_crossword.setTemplate_view((String) model.asMap().get("template"));
		}

		//画面出力用情報の取得
		List<Crossworditem> j_crossworditemes = crosswordService.findByCrosswordId(j_crossword.getId());

		//ページの描画
		ModelAndView mav = new ModelAndView("playCrossWord");
		System.out.println(j_playinfo.getId());
		mav.addObject("h_user",j_user);
		mav.addObject("h_playinfo",j_playinfo);
		mav.addObject("h_crossword",j_crossword);
		mav.addObject("h_crossworditemes",j_crossworditemes);
		return mav;
	}

	/**
	 * クロスワード結果確認
	 */
	@GetMapping("play/check")
	public String croCheck(RedirectAttributes attributes,int id,int playTime,String template) {
		String url = "";

		int playId = Integer.valueOf(id);
		//セッションの取得
		Crosswordplay playinfo = crosswordService.PlayInfofindById(playId);
		playinfo.setPlayTime(playTime);
		Crossword crossword = crosswordService.findById(playinfo.getCrossword().getId());
		crossword.setTemplate_view(template);

		//解答の確認
		boolean res = crosswordService.checkCrossWord(crossword);

		crosswordService.updatePlayinfo(playinfo,res);
		attributes.addFlashAttribute("playId",playinfo.getId());
		attributes.addFlashAttribute("template",template);

		if(res){
			url = "redirect:/crossword/play/rank";
		}else{
			url = "redirect:/crossword/play";
		}


		return url;
	}

	/**
	 * クロスワードランキング表示
	 */
	@GetMapping("play/rank")
	public ModelAndView rank(Model model) {
		int userid = -1;
		//ランキングの取得
		String str = String.valueOf(model.asMap().get("playId"));
		if(str != null && str.equals("null") && str.equals("")){
			int playId = Integer.valueOf(str);
			Crosswordplay playinfo = crosswordService.PlayInfofindById(playId);
			userid = playinfo.getUser().getId();
		}
		List<ranking> j_rankUsers = userService.getRankUsers(userid);

		//セッションクリア
		session.invalidate();

		//ページの描画
		ModelAndView mav = new ModelAndView("rankCrossWord");
		mav.addObject("h_userId",userid);
		mav.addObject("h_rankUsers", j_rankUsers);

		return mav;
	}
}