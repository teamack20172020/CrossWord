package jp.co.ack.crossword.domain.User;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	@Query(value = "SELECT * from user WHERE id = BINARY ?1", nativeQuery = true)
	User findByIdBinary(String id);

	User findById(int id);

	@Query(value = "SELECT * from user WHERE created = :created", nativeQuery = true)
	User findByCreated(@Param("created") Date created);

}
