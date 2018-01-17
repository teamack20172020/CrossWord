package jp.co.ack.crossword.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.ack.crossword.domain.User.User;
import jp.co.ack.crossword.domain.User.UserRepository;


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
	public void saveUser(Date now){
		try {
			User user = new User();
			user.setMissCnt(0);
			user.setCreated(now);
			user.setPlayTime(System.currentTimeMillis());
			userRepository.save(user);
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ユーザー更新処理
	 *
	 */
	public void updateUser(User user,boolean res){
		user.setMissCnt(user.getMissCnt()+1);
		if(res){
			long end = System.currentTimeMillis();
			user.setPlayTime(end-user.getPlayTime());
		}
		userRepository.save(user);
	}

	/**
	 * ユーザー更新処理
	 *
	 */
	public Date getCreateDate(){
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

	/*	@Override
	public Mst loadUserByUsername(String username) throws UsernameNotFoundException {
		Mst user = getUserById(username);
		if (user == null || !user.getId().equals(username)) {
			log.info("Mst not found: " + username);
			throw new UsernameNotFoundException("Mst not found: " + username);
		}
		return user;
	}*/

	/**
	 * ユーザー検索処理
	 *
	 * @param userId
	 * @param passWord
	 * @param email
	 */
	/*	public List<Mst> userSearch(SearchUser form) {
		Mst user = new Mst();
		StringBuffer strUsersearchSQL = new StringBuffer();
		strUsersearchSQL.append("select ");
		strUsersearchSQL.append("user.* ");
		strUsersearchSQL.append("from ");
		strUsersearchSQL.append("user ");
		strUsersearchSQL.append("where 1=1 ");
		if (!(form.getId() == null || form.getId().isEmpty())) {
			strUsersearchSQL.append("and user.id like  '%");
			strUsersearchSQL.append(form.getId());
			strUsersearchSQL.append("%' ");
		}
		if (!(form.getFirstName() == null || form.getFirstName().isEmpty())) {
			strUsersearchSQL.append("and user.first_name like '%");
			strUsersearchSQL.append(form.getFirstName());
			strUsersearchSQL.append("%' ");
		}
		if (!(form.getLastName() == null || form.getLastName().isEmpty())) {
			strUsersearchSQL.append("and user.last_name like '%");
			strUsersearchSQL.append(form.getLastName());
			strUsersearchSQL.append("%' ");
		}
		if (form.getOperator().equals("1")) {
			strUsersearchSQL.append("and user.operator = b'0' ");
		} else if (form.getOperator().equals("2")) {
			strUsersearchSQL.append("and user.operator = b'1' ");
		}
		if (form.getDeleted().equals("1")) {
			strUsersearchSQL.append("and user.deleted = b'0' ");
		} else if (form.getDeleted().equals("2")) {
			strUsersearchSQL.append("and user.deleted = b'1' ");
		}
		strUsersearchSQL.append("order by user.id ");
		System.out.println(strUsersearchSQL.toString());
		// クエリの生成
		Query q = entityManager.createNativeQuery(strUsersearchSQL.toString(), Mst.class);
		// 抽出
		@SuppressWarnings("unchecked")
		List<Mst> users = q.getResultList();
		return users;
	}*/
}
