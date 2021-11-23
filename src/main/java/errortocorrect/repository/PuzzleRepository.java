package errortocorrect.repository;

import errortocorrect.entity.Puzzle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PuzzleRepository extends CrudRepository<Puzzle, Integer> {

    Puzzle findById(Long integer);

    List<Puzzle> findAll();

    List<Puzzle> findByExpId(Long expId);

    List<Puzzle> findByCourseId(Long courseId);




}
