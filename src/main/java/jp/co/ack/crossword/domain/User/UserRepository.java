package jp.co.ack.crossword.domain.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	@Query(value = "SELECT * from user WHERE id = BINARY ?1", nativeQuery = true)
	User findByIdBinary(String id);
}
