package jp.co.ack.crossword.application;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.ack.crossword.domain.User.User;


@Service
//public class UserService implements UserDetailsService {
public class UserService {
	@Autowired
	private jp.co.ack.crossword.domain.User.UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@PersistenceContext
	EntityManager entityManager;

	Logger log = LoggerFactory.getLogger(this.getClass());

	public User getUserById(String id) {
		return userRepository.findByIdBinary(id);
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
