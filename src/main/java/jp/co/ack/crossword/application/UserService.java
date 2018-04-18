package jp.co.ack.crossword.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	/*ユーザー名とpaswordを元にに取得*/
	public User  getUserByNameAndPassword(String name,String password){
		return	  userRepository.findByNameBINARYAndPasswordBINARY(name,password);
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
		return user;
	}
	public User createNewUser(String name, String pass){
		User user =new User();
		user.setName(name);
		user.setPassword(pass);
		user.setCreated(new Date());
		user = userRepository.save(user);
		return user;
	}

	/*押されたボタンからuserを探す処理*/
	public User getuser(String name,String password,String config){
		User user=null;
		try{
			user= getUserByNameAndPassword(name,password);
		}catch(Exception e){
		}
		if(config!=null&&user==null){
			user=createNewUser(name,password);
		}else if(config!=null&&user!=null){
			user=null;
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
	public List<ranking> getRankUsers(int userid,int crosswordid){
		List<User> userList = getRankUserList(crosswordid);
		List<Crosswordplay> playList = getRankPlayList(crosswordid);
		int cnt = 0;
		int flg = -1;
		List<ranking> ranks = new ArrayList<ranking>();
		while((cnt < 500 || flg < 0) && cnt < playList.size()){
			if(cnt < 500 || userList.get(cnt).getId() == userid){
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

	private List<User> getRankUserList(int crosswordid){
		StringBuffer getRankingSQL = new StringBuffer();
		getRankingSQL.append("select ");
		getRankingSQL.append(" main.* ");
		getRankingSQL.append("from ");
		getRankingSQL.append("user main");
		getRankingSQL.append(" ,crosswordplay sub ");
		getRankingSQL.append("where ");
		getRankingSQL.append("main.id = sub.user_id and ");
		getRankingSQL.append("sub.crossword_id="+crosswordid+" and ");
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

	private List<Crosswordplay> getRankPlayList(int crosswordid){
		StringBuffer getRankingSQL = new StringBuffer();
		getRankingSQL.append("select ");
		getRankingSQL.append(" sub.* ");
		getRankingSQL.append("from ");
		getRankingSQL.append(" user main ");
		getRankingSQL.append(",crosswordplay sub ");
		getRankingSQL.append("where ");
		getRankingSQL.append("main.id = sub.user_id and ");
		getRankingSQL.append("sub.crossword_id="+crosswordid+" and ");
		getRankingSQL.append("sub.complete_flg is true ");
		getRankingSQL.append("order by sub.score desc, sub.id desc ");
		System.out.println(getRankingSQL.toString());
		// クエリの生成
		Query q = entityManager.createNativeQuery(getRankingSQL.toString(), Crosswordplay.class);
		// 抽出
		@SuppressWarnings("unchecked")
		List<Crosswordplay> playList = q.getResultList();
		return playList;

	}
	public ArrayList<Integer> getrange(int crosswordid) {
		List<User> userList = getRankUserList(crosswordid);
		ArrayList<Integer> range=new ArrayList<Integer>();
		int count=1;
		int b=userList.size();
		while(count<=b){
			range.add(count);
			count=count+10;
		}

		return range;
	}


}
