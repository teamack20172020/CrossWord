package jp.co.ack.crossword.interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@Autowired
	HttpServletRequest request;
	/**
	 * トップぺージ
	 *
	 */
	@GetMapping
	public ModelAndView home() {
		//セッションクリア
		//ページの描画
		ModelAndView mav = new ModelAndView("homeCrossWord");
		return mav;
	}

	@GetMapping("user")
	public ModelAndView newuser01(Model model){
		//セッションクリア
		//ページの描画
		User j_user=new User();
		ModelAndView mav = new ModelAndView("registration");
		mav.addObject("h_user", j_user);
		if(model.asMap().get("warning")!=null){
			mav.addObject("h_warning", model.asMap().get("warning"));
		}
		return mav;
	}
	/**
	 * 新規登録orゲストプレイ
	 */
	@GetMapping("user/next")
	public String newuser02(RedirectAttributes attributes,String name,String pass,String config,String login){
		session.invalidate();
		User user = new User();
		String url;
		String warning = null;
		//ページの描画
		if(config==null&&login==null){
			//ゲストプレイ
			user=userService.createUser();
		}else{
			//ユーザー検索
			user=userService.getuser(name,pass, config);
			if(config!=null){
				warning="入力された名前とパスは既に登録されています";
			}else{
				warning="ユーザーが見つかりませんでした。内容が未登録か、パスワードまたは名前が間違っています。";
			}
		}
		if(user==null){
			url = "redirect:/crossword/user";
			attributes.addFlashAttribute("warning",warning);
		}else{
			url="redirect:/crossword/wantdo";
			// セッションへ保存
			session.setAttribute("USERID",user.getId());
		}
		return url;
	}

	@GetMapping("wantdo")
	public ModelAndView wantdi(Model model){
		//プレイ情報の初期化
		session.setAttribute("PLAYID",null);
		int userid=(int) session.getAttribute("USERID");
		ModelAndView mav = new ModelAndView("wantdo");
		List<Crossword> clearcrossword=crosswordService.getmyCrossWordList(userid);
		if(clearcrossword.size()!=0){
			mav.addObject("h_1clearCross",clearcrossword);
		}else{
			mav.addObject("h_flag","flag");
		}
		return mav;
	}

	@GetMapping("wantdo/next")
	public String clearparam(RedirectAttributes attributes,@RequestParam("CROSSID") int crosswordId){
		attributes.addFlashAttribute("crosswordId",crosswordId);
		String url="redirect:/crossword/play/rank";
		return url;
	}

	@GetMapping("Chose")
	public ModelAndView Chose(Model model){
		//プレイ情報の初期化
		session.setAttribute("PLAYID",null);
		int userid=(int) session.getAttribute("USERID");
		ModelAndView mav = new ModelAndView("CrossChose");
		List<Crossword> randomCross=crosswordService.getCrossWordList(userid);
		if(randomCross.size()!=0){
			mav.addObject("h_randomCross",randomCross);
		}else{
			mav.addObject("h_flag","flag");
		}
		return mav;
	}

	@GetMapping("Chose/next")
	public String param(RedirectAttributes attributes,@RequestParam("CROSSID") int crosswordId){
		User user = userService.getUserById((int) session.getAttribute("USERID"));
		Crossword crossword = crosswordService.findById(crosswordId);
		Crosswordplay playInfo = crosswordService.createPlayinfo(user, crossword);
		attributes.addFlashAttribute("playId",playInfo.getId());
		String url="redirect:/crossword/play";
		return url;
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
			str = (String) session.getAttribute("PLAYID");
		}
		if(str == null || str.equals("null") || str.equals("")){
			j_user = userService.getUserById((int) session.getAttribute("USERID"));
			str = String.valueOf(crosswordService.main(j_user));
			j_playinfo = crosswordService.PlayInfofindById(Integer.valueOf(str));
			j_crossword = crosswordService.findById(j_playinfo.getCrossword().getId());
		}else{
			j_playinfo = crosswordService.PlayInfofindById(Integer.valueOf(str));
			j_crossword = crosswordService.findById(j_playinfo.getCrossword().getId());
			String strtemp = (String) model.asMap().get("template");
			if(!(strtemp == null || strtemp.equals("null") || strtemp.equals(""))){
				j_playinfo.setTemplate_view((String) model.asMap().get("template"));
			}
		}

		//PlayIdの設定
		session.setAttribute("PLAYID",str);

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
		playinfo.setTemplate_view(template);
		playinfo.setPlayTime(playTime);
		playinfo.setMissCnt(playinfo.getMissCnt()+1);
		Crossword crossword = crosswordService.findById(playinfo.getCrossword().getId());

		//解答の確認
		boolean res = crosswordService.checkCrossWord(crossword,template);

		crosswordService.updatePlayinfo(playinfo,res);
		attributes.addFlashAttribute("playId",playinfo.getId());
		attributes.addFlashAttribute("template",template);

		if(res){
			attributes.addFlashAttribute("crosswordId",playinfo.getCrossword().getId());
			url = "redirect:/crossword/play/rank";
		}else{
			url = "redirect:/crossword/play";
		}
		return url;
	}

	/**
	 * クロスワードランキング表示*/

	@GetMapping("play/rank")
	public ModelAndView rank(Model model) {
		String str = String.valueOf(model.asMap().get("crosswordId"));
		int userid ;
		//ランキングの取得
		int crosswordid;
		if(str == null || str.equals("null") || str.equals("")){
			str = (String) session.getAttribute("PLAYID");
			Crosswordplay playinfo = crosswordService.PlayInfofindById(Integer.valueOf(str));
			crosswordid = playinfo.getCrossword().getId();
		}else{
			crosswordid = Integer.valueOf(str);
		}

		userid=(int) session.getAttribute("USERID");
		List<ranking> j_rankUsers =		userService.getRankUsers(userid,crosswordid);
		//ページの描画
		ArrayList<Integer> range= userService.getrange(crosswordid);

		ModelAndView mav=new ModelAndView("rankCrossWord");
		mav.addObject("h_range",range);
		mav.addObject("h_userId",userid );
		mav.addObject("h_rankUsers", j_rankUsers);
		return mav;
	}
}