package errortocorrect.repository;

import errortocorrect.entity.Records;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecordsRepository extends CrudRepository<Records, Integer> {
    List<Records> findAll();

    List<Records> findByUserId(Long userId);

    List<Records> findByUser_IdAndPuzzle_IdOrderByCreateTimeDesc(Long id, Long id1);

    List<Records> findByResultEquals(String result);

    List<Records> findByOrderByCreateTimeDesc(Pageable pageable);





    List<Records> findByPuzzle_Exp_ExpIdAndResultLike(Long expId, String result);

    List<Records> findByPuzzle_Course_CourseIdAndResultLike(Long courseId, String result);
















}
