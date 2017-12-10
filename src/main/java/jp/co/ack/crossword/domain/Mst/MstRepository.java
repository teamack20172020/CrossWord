package jp.co.ack.crossword.domain.Mst;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MstRepository extends CrudRepository<Mst, String> {
}
