package jp.co.ack.crossword.application;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ack.crossword.config.ApplicationConfig;
import jp.co.ack.crossword.domain.Mst.Mst;
import jp.co.ack.crossword.domain.User.User;


@Service
public class ScoreService {

	@Autowired
	ApplicationConfig config;

	@PersistenceContext
	EntityManager entityManager;


	/**
	 * スコア算出[クロスワード]
	 */
	public int getCrosswordScore(User user){

		int kbn;
		String field01;
		String field02;
		String order;
		String limit;

		kbn = config.MST_CROSSWORD_CNT_KBN;
		field01 = " >= " + user.getMissCnt();
		order = "order by field01 ASC";
		limit = "limit 1";
		List<Mst> mstCnt = getMstList(kbn,field01,"","",order,limit);

		double cntKeisu = 0;
		if(mstCnt.size() > 0){
			cntKeisu = Double.parseDouble(mstCnt.get(0).getField03());
		}

		kbn = config.MST_CROSSWORD_TIM_KBN;
		field01 = " >= " + user.getPlayTime();
		field02 = " <= " + user.getPlayTime();
		order = "order by field03 ASC";
		limit = "limit 1";
		List<Mst> mstTime = getMstList(kbn,field01,field02,"",order,limit);

		int cntkaten = 0;
		if(mstTime.size() > 0){
			cntkaten = Integer.valueOf(mstTime.get(0).getField03());
		}

		int base = 9999;
		int score = (int)(base * cntKeisu) + cntkaten;

		return score;
	}

	/**
	 * スコア係数取得処理
	 * @param userId
	 * @param passWord
	 * @param email
	 */
	private List<Mst> getMstList(int kbn,String field01,String field02,String field03,String order,String limit) {
		StringBuffer strUsersearchSQL = new StringBuffer();
		strUsersearchSQL.append("select ");
		strUsersearchSQL.append(" * ");
		strUsersearchSQL.append("from  mst ");
		strUsersearchSQL.append("where id = " + kbn);
		if(!field01.equals("")){
			strUsersearchSQL.append(" and field01 " + field01);
		}
		if(!field02.equals("")){
			strUsersearchSQL.append(" and field02 " + field02);
		}
		if(!field03.equals("")){
			strUsersearchSQL.append(" and field03 " + field03);
		}
		strUsersearchSQL.append(" " + order);
		strUsersearchSQL.append(" " + limit);
		System.out.println(strUsersearchSQL.toString());
		// クエリの生成
		Query q = entityManager.createNativeQuery(strUsersearchSQL.toString(), Mst.class);
		// 抽出
		@SuppressWarnings("unchecked")
		List<Mst> msts = q.getResultList();
		return msts;
	}
}
