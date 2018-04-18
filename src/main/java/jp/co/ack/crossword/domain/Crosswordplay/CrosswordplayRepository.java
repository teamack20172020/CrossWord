package jp.co.ack.crossword.domain.Crosswordplay;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrosswordplayRepository extends CrudRepository<Crosswordplay, String> {
	Crosswordplay findById(int id);

	@Query(value = "SELECT * From crosswordplay WHERE crossword_id=?1 AND user_id=?2 AND complete_flg =true" , nativeQuery = true)
	Crosswordplay findBycomplete(int crosswordid,int userid);

	Crosswordplay findByCrosswordIdAndUserId(int crosswordid,int userid);
}
