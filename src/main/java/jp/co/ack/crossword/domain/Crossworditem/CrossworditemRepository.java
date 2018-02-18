package jp.co.ack.crossword.domain.Crossworditem;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrossworditemRepository extends CrudRepository<Crossworditem, String> {
	List<Crossworditem> findByCrosswordId(int crossworId);
}
