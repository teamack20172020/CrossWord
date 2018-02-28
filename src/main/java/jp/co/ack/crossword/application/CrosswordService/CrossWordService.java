package jp.co.ack.crossword.application.CrosswordService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ack.crossword.config.ApplicationConfig;
import jp.co.ack.crossword.domain.Crossword.Crossword;
import jp.co.ack.crossword.domain.Crossword.CrosswordRepository;
import jp.co.ack.crossword.domain.Crossworditem.Crossworditem;
import jp.co.ack.crossword.domain.Crossworditem.CrossworditemRepository;
import jp.co.ack.crossword.domain.Crosswordplay.Crosswordplay;
import jp.co.ack.crossword.domain.Crosswordplay.CrosswordplayRepository;
import jp.co.ack.crossword.domain.User.User;
import jp.co.ack.crossword.interfaces.vo.template;

@Service
public class CrossWordService {

	@Autowired
	ApplicationConfig apCon;
	@Autowired
	ScoreSetting score;
	@Autowired
	CreateCrossword createcrossword;
	@Autowired
	CrosswordRepository crosswordrepository;
	@Autowired
	CrossworditemRepository crossworditemrepository;
	@Autowired
	CrosswordplayRepository crosswordplayrepository;


	/**
	 *クロスワード登録処理
	 */
	public int main(User user){
		Crossword info = new Crossword();
		info = createCrossward(user);
		Crosswordplay playinfo = crosswordplayrepository.findByCrosswordIdAndUserId(info.getId(),user.getId());
		return playinfo.getId();
	}

	/**
	 *クロスワード登録処理
	 */
	private Crossword createCrossward(User user){
		Crossword crossword = new Crossword();
		template temp = createcrossword.create(7, 7, 50);
		crossword.setSize(temp.getSize());
		crossword.setWidth(temp.getWidth());
		crossword.setHeight(temp.getHeight());
		crossword.setUtilization(temp.getUtilization());
		crossword.setTemplate(temp.getTemplateToString());
		crossword.setTemplate_view(temp.getFormatToTemplate());
		crossword.setCreated(new Date());
		crossword.setUser(user);
		crossword = crosswordrepository.save(crossword);
		crossword.setCrossworditem(createItems(temp,crossword));
		crossword = crosswordrepository.save(crossword);
		createPlayinfo(user, crossword);
		return crossword;
	}

	/**
	 * クロスワードヒント登録処理
	 */
	private List<Crossworditem> createItems(template temp,Crossword crossword){
		ArrayList<Crossworditem> items = new ArrayList<Crossworditem>();
		for(int i = 0;i < temp.getWordListCol().size();i++){
			Crossworditem item = new Crossworditem();
			item.setKbn(1);
			item.setWord(temp.getWordListCol().get(i).getWord());
			item.setCrossword(crossword);
			item = crossworditemrepository.save(item);
			items.add(item);
		}

		for(int j = 0;j < temp.getWordListRow().size();j++){
			Crossworditem item = new Crossworditem();
			item.setKbn(0);
			item.setWord(temp.getWordListRow().get(j).getWord());
			item.setCrossword(crossword);
			item = crossworditemrepository.save(item);
			items.add(item);
		}
		return items;
	}

	/**
	 * クロスワードプレイ情報登録処理
	 */
	private Crosswordplay createPlayinfo(User user,Crossword crossword){
		Crosswordplay playinfo = new Crosswordplay();
		playinfo.setMissCnt(0);
		playinfo.setPlayTime(999999999);
		playinfo.setScore(0);
		playinfo.setCompleteFlg(false);
		playinfo.setCreated(new Date());
		playinfo.setUser(user);
		playinfo.setCrossword(crossword);
		playinfo = crosswordplayrepository.save(playinfo);
		return playinfo;

	}

	/**
	 * クロスワードプレイ情報更新処理
	 */
	public Crosswordplay updatePlayinfo(Crosswordplay playinfo,boolean flg){
		playinfo.setMissCnt(playinfo.getMissCnt()+1);
		if(flg){
			playinfo.setCompleteFlg(true);
			playinfo.setScore(score.getCrosswordScore(playinfo));
		}
		playinfo = crosswordplayrepository.save(playinfo);
		return playinfo;

	}

	/**
	 * 結果確認処理
	 */
	public boolean checkCrossWord(Crossword crossword){
		String str = crossword.getTemplate();
		if(!str.equals(crossword.getTemplate_view())){
			return false;
		}
		return true;
	}

	public Crossword findById(int id){
		return crosswordrepository.findById(id);
	}

	public Crosswordplay PlayInfofindById(int id){
		return crosswordplayrepository.findById(id);
	}

	public List<Crossworditem> findByCrosswordId(int id){
		return crossworditemrepository.findByCrosswordId(id);
	}

	public Crosswordplay findByCrosswordIdandUserId(int userid,int crosswordid){
		return crosswordplayrepository.findByCrosswordIdAndUserId(crosswordid,userid);
	}
}
