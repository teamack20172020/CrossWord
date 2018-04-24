package jp.co.ack.crossword.application.CrosswordService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	@PersistenceContext
	EntityManager entityManager;

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
	public Crosswordplay createPlayinfo(User user,Crossword crossword){
		Crosswordplay playinfo = new Crosswordplay();
		playinfo.setMissCnt(0);
		playinfo.setPlayTime(0);
		playinfo.setScore(0);
		playinfo.setCompleteFlg(false);
		playinfo.setCreated(new Date());
		playinfo.setUser(user);
		playinfo.setCrossword(crossword);
		playinfo.setTemplate_view(crossword.getTemplate_view());
		playinfo = crosswordplayrepository.save(playinfo);
		return playinfo;

	}

	/**
	 * クロスワードプレイ情報更新処理
	 */
	public Crosswordplay updatePlayinfo(Crosswordplay playinfo,boolean flg){
		if(flg){
			playinfo.setCompleteFlg(true);
			playinfo.setScore(score.getCrosswordScore(playinfo));
			updateCrossword(playinfo);
		}
		playinfo = crosswordplayrepository.save(playinfo);
		return playinfo;
	}

	/**
	 * クロスワード更新処理
	 */
	public Crossword updateCrossword(Crosswordplay playinfo){
		Crossword crossword = findById(playinfo.getCrossword().getId());
		crossword.setClearcnt(crossword.getClearcnt() + 1);
		crosswordrepository.save(crossword);
		return crossword;

	}

	/**
	 * 結果確認処理
	 */
	public boolean checkCrossWord(Crossword crossword,String template){
		String str = crossword.getTemplate();
		if(!str.equals(template)){
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

	public Crosswordplay findByCrosswordIdandUserId(int crosswordid,int userid){
		return crosswordplayrepository.findByCrosswordIdAndUserId(crosswordid,userid);
	}

	public Crosswordplay findBycomplete(int crosswordid,int userid){
		return crosswordplayrepository.findBycomplete(crosswordid,userid);
	}

	public List<Crossword> getCrossWordList(int userid){
		StringBuffer getCrosswordplaySQL = new StringBuffer();
		getCrosswordplaySQL.append("select ");
		getCrosswordplaySQL.append("main.* ");
		getCrosswordplaySQL.append("from ");
		getCrosswordplaySQL.append("crossword main,crosswordplay sub ");
		getCrosswordplaySQL.append("where ");
		getCrosswordplaySQL.append("main.id=sub.crossword_id and ");
		getCrosswordplaySQL.append("sub.complete_flg is true and ");
		getCrosswordplaySQL.append("main.id ");
		getCrosswordplaySQL.append("not in ");
		getCrosswordplaySQL.append("(select sub2.crossword_id from crosswordplay sub2 ");
		getCrosswordplaySQL.append("where ");
		getCrosswordplaySQL.append("sub2.user_id =" + userid + " and ");
		getCrosswordplaySQL.append("sub2.complete_flg is true ) ");
		getCrosswordplaySQL.append("group by main.id");
		System.out.println(getCrosswordplaySQL.toString());
		// クエリの生成
		Query q = entityManager.createNativeQuery(getCrosswordplaySQL.toString(), Crossword.class);
		@SuppressWarnings("unchecked")
		List<Crossword> crosswordList =q.getResultList();
		return crosswordList;
	}
	/*クロスワード取得処理*/
	public  List<Crossword> getmyCrossWordList(int userid){
		StringBuffer getCrosswordplaySQL = new StringBuffer();
		getCrosswordplaySQL.append("select ");
		getCrosswordplaySQL.append(" * ");
		getCrosswordplaySQL.append("from ");
		getCrosswordplaySQL.append("crossword main ");
		getCrosswordplaySQL.append("where ");
		getCrosswordplaySQL.append("main.id in (select ");
		getCrosswordplaySQL.append("sub.crossword_id ");
		getCrosswordplaySQL.append("from ");
		getCrosswordplaySQL.append("crosswordplay sub ");
		getCrosswordplaySQL.append("where ");
		getCrosswordplaySQL.append("sub.user_id = "+userid+" and ");
		getCrosswordplaySQL.append("sub.complete_flg is true ");
		getCrosswordplaySQL.append("group by crossword_id )");
		System.out.println(getCrosswordplaySQL.toString());
		// クエリの生成
		Query q = entityManager.createNativeQuery(getCrosswordplaySQL.toString(), Crossword.class);
		@SuppressWarnings("unchecked")
		List<Crossword> crosswordList =q.getResultList();
		return crosswordList;
	}
}
