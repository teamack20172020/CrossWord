package jp.co.ack.crossword.domain.Crosswordplay;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrosswordplayRepository extends CrudRepository<Crosswordplay, String> {
	Crosswordplay findById(int id);
	Crosswordplay findByCrosswordIdAndUserId(int crosswordId,int userId);
}
