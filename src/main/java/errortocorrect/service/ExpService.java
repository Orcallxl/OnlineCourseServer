package errortocorrect.service;

import errortocorrect.dto.CourseDto;
import errortocorrect.dto.ExpDto;
import errortocorrect.entity.Course;
import errortocorrect.entity.Exp;
import errortocorrect.exception.CannotDeleteException;
import errortocorrect.exception.CannotEditException;
import errortocorrect.exception.NotFoundException;
import errortocorrect.repository.CourseRepository;
import errortocorrect.repository.ExpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpService {
    @Autowired
    ExpRepository expRepository;

    @Autowired
    CourseRepository courseRepository;

    public List<Exp> findCourseExps(CourseDto courseDto) {
        return expRepository.findByCourseId(courseDto.getCourseId());
    }

    public Exp createExp(ExpDto expDto) {
        Course course = courseRepository.findByCourseId(expDto.getCourseId());

        Exp exp = new Exp();
        exp.setExpName(expDto.getExpName());
        exp.setCourse(course);
        exp.setCourseName(expDto.getCourseName());
        course.addExp(exp);
        return expRepository.save(exp);
    }

    public Exp editExp(ExpDto expDto,Long uid) throws CannotEditException, NotFoundException {
        Exp exp = expRepository.findByExpId(expDto.getExpId());
//        Course course = courseRepository.findByCourseId(expDto.getCourseId());
//        if(course == null)
//        {
//            throw  new NotFoundException("未找到对应课程");
//        }
//        if(uid == course.getTeacherId())
//        {
            exp.setExpName(expDto.getExpName());
//            exp.setCourse(course);
//            exp.setCourseName(expDto.getCourseName());
            return expRepository.save(exp);
//        }
//        else {
//            throw new CannotEditException("仅创建者能修改");
//        }
    }

    public void deleteExp(ExpDto expDto, Long uid){
        Exp exp = expRepository.findByExpId(expDto.getExpId());
        expRepository.delete(exp);
    }

    public Exp findExp(ExpDto expDto) {
        return expRepository.findByExpId(expDto.getExpId());
    }
}
