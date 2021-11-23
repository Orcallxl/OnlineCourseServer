package errortocorrect.repository;

import errortocorrect.entity.Exp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpRepository extends CrudRepository<Exp, Integer> {


    List<Exp> findAll();

    List<Exp> findByCourseName(String courseName);

    List<Exp> findByCourseId(Long courseId);

    Exp findByExpId(Long expId);





}
