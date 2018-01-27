package jp.co.ack.crossword.domain.Mst;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstRepository extends CrudRepository<Mst, String> {
	List<Mst> findByKbn(int kbn);
}
