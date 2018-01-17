package jp.co.ack.crossword.domain.Word;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends CrudRepository<Word, String> {
	@Query(value = "SELECT * from word WHERE size <= :size", nativeQuery = true)
	List<Word> findBySize(@Param("size") int size);
}
