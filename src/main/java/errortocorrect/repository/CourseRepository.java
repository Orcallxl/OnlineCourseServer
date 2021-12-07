package errortocorrect.repository;

import errortocorrect.entity.Course;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
    List<Course> findAll();

    List<Course> findByStatusOrderByCreateTimeAsc(Integer status, Sort sort);

    List<Course> findByTeacherId(Long teacherId);

    Course findByCourseId(Long courseId);


    List<Course> findByUsers_IdIsNotOrderByCreateTimeAsc(Long id);


}
