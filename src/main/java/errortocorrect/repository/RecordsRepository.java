package errortocorrect.repository;

import errortocorrect.entity.Records;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecordsRepository extends CrudRepository<Records, Integer> {
    List<Records> findAll();

    List<Records> findByUserId(Long userId);



}
