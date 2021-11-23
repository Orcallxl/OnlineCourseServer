package errortocorrect.repository;

import errortocorrect.entity.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Integer> {

    Subject findById(Long integer);

    List<Subject> findAll();

    List<Subject> findByUser_Id(Long id);

    List<Subject> findByUser_IdAndParentSubjectIsNullOrderByCreateTimeDesc(Long id);



//    List<Subject> findByOrderByCreateTimeDesc();

    List<Subject> findByParentSubjectIsNullOrderByCreateTimeDesc();

    List<Subject> findByParentSubjectIsNullOrderByThumbNumDescReplyNumDescViewNumDesc();
//
//    List<Subject> findByOrderByReplyNumDescThumbNumAscViewNumAsc();

}
