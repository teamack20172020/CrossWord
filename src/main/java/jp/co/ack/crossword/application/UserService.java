package jp.co.ack.crossword.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.ack.crossword.domain.Crosswordplay.Crosswordplay;
import jp.co.ack.crossword.domain.User.User;
import jp.co.ack.crossword.domain.User.UserRepository;
import jp.co.ack.crossword.interfaces.vo.ranking;


@Service
//public class UserService implements UserDetailsService {
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@PersistenceContext
	EntityManager entityManager;

	Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * ユーザーIDを元に１件取得
	 */
	public User getUserById(int id) {
		return userRepository.findById(id);
	}

	/**
	 * ユーザー登録日時を元に１件取得
	 */
	public User getUserByCreated(Date datetime) {
		return userRepository.findByCreated(datetime);
	}


	/**
	 * ユーザー全件取得
	 *
	 */
	public List<User> getAll(){
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}


	/**
	 * ユーザー登録処理
	 *
	 */
	public User createUser(){
		User user = new User();
		user.setName("ゲスト");
		user.setCreated(new Date());
		user = userRepository.save(user);

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * ユーザー更新処理
	 *
	 */
	public void updateUser(User user,boolean res){
		//更新日付のセット
		user.setUpdated(getDate());
		userRepository.save(user);
	}

	/**
	 * 日時取得
	 *
	 */
	public Date getDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime = new Date();
		try {
			String str = format.format(datetime);
			datetime =  format.parse(str);
		} catch (ParseException e) {
			//日付の型フォーマットに失敗しても何もしない
			System.out.println(e);
		}
		return datetime;
	}

	/**
	 * ユーザー検索処理
	 * (ランキング表示用)
	 * @param userId
	 * @param passWord
	 * @param email
	 */
	public List<ranking> getRankUsers(int userid) {

		List<User> userList = getRankUserList();
		List<Crosswordplay> playList = getRankPlayList();

		int cnt = 0;
		int flg = -1;
		List<ranking> ranks = new ArrayList<ranking>();
		while((cnt < 10 || flg < 0) && cnt < playList.size()){
			if(cnt < 10 || userList.get(cnt).getId() == userid){
				ranking rank = new ranking();
				rank.setRanking(cnt + 1);
				rank.setId(userList.get(cnt).getId());
				rank.setName(userList.get(cnt).getName());
				rank.setScore((int) playList.get(cnt).getScore());
				ranks.add(rank);
				if(userList.get(cnt).getId() == userid){
					flg = 1;
				}
			}
			cnt++;
		}

		return ranks;
	}

	private List<User> getRankUserList(){
		StringBuffer getRankingSQL = new StringBuffer();
		getRankingSQL.append("select ");
		getRankingSQL.append(" main.* ");
		getRankingSQL.append("from ");
		getRankingSQL.append(" user main ");
		getRankingSQL.append(",crosswordplay sub ");
		getRankingSQL.append("where ");
		getRankingSQL.append("main.id = sub.user_id and ");
		getRankingSQL.append("sub.complete_flg is true ");
		getRankingSQL.append("order by sub.score desc, sub.id desc ");
		System.out.println(getRankingSQL.toString());
		// クエリの生成
		Query q = entityManager.createNativeQuery(getRankingSQL.toString(), User.class);
		// 抽出
		@SuppressWarnings("unchecked")
		List<User> userList = q.getResultList();
		return userList;
	}

	private List<Crosswordplay> getRankPlayList(){
		StringBuffer getRankingSQL = new StringBuffer();
		getRankingSQL.append("select ");
		getRankingSQL.append(" sub.* ");
		getRankingSQL.append("from ");
		getRankingSQL.append(" user main ");
		getRankingSQL.append(",crosswordplay sub ");
		getRankingSQL.append("where ");
		getRankingSQL.append("main.id = sub.user_id and ");
		getRankingSQL.append("sub.complete_flg is true ");
		System.out.println(getRankingSQL.toString());
		// クエリの生成
		Query q = entityManager.createNativeQuery(getRankingSQL.toString(), Crosswordplay.class);
		// 抽出
		@SuppressWarnings("unchecked")
		List<Crosswordplay> playList = q.getResultList();
		return playList;

	}
}
