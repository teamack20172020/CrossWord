package jp.co.ack.crossword.domain.Crossword;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrosswordRepository extends CrudRepository<Crossword, String> {
	Crossword findById(int id);
}
