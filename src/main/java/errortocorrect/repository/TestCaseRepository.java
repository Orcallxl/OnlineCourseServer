package errortocorrect.repository;

import errortocorrect.entity.Subject;
import errortocorrect.entity.TestCase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestCaseRepository extends CrudRepository<TestCase, Integer> {


    TestCase findById(Long integer);

    List<TestCase> findAll();

    List<TestCase> findByPuzzle_Id(Long id);




}
