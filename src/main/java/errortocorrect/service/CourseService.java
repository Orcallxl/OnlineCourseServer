package errortocorrect.service;

import errortocorrect.dto.ApplyCourseDto;
import errortocorrect.dto.CourseDto;
import errortocorrect.dto.TeacherDto;
import errortocorrect.dto.UserDto;
import errortocorrect.entity.Course;
import errortocorrect.entity.User;
import errortocorrect.exception.CannotEditException;
import errortocorrect.exception.PassCodeWrongException;
import errortocorrect.repository.CourseRepository;
import errortocorrect.repository.UserRepository;
import errortocorrect.util.TimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private static final Logger logger = LogManager.getLogger("CourseService");
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Set<Course> findUserCourses(UserDto user) {
        return userRepository.findById(user.getId()).map(User::getCourse).orElse(new HashSet<>());
    }

    /*历史课程*/
    public Set<Course> findUserHistoryCourses(UserDto user) {
        return userRepository.findById(user.getId()).map(User::getCourse)
                .orElse(new HashSet<>())
                .stream().filter(course -> course.getStatus()==1)
                .collect(Collectors.toSet());
    }

    /*当前课程*/
    public Set<Course> findUserCurrentCourses(UserDto user) {
        return userRepository.findById(user.getId()).map(User::getCourse)
                .orElse(new HashSet<>())
                .stream().filter(course -> course.getStatus()==0)
                .collect(Collectors.toSet());
    }

    /*未选课程*/
    public List<Course> findNotUserCurrentCourses(UserDto user) {
       List<Course> courses = findActiveCourses();
       List<Course> ret = new ArrayList<>();
       for(Course course : courses)
       {
           boolean found = false;
           for(User tuser:course.getUsers())
           {
               if(tuser.getId() == user.getId())
               {
                   found = true;
                   break;
               }
           }
           if(found == false) {
               ret.add(course);
           }
       }
        return ret;
    }

    public List<Course> findActiveCourses() {
        Sort sort = new Sort(Sort.Direction.ASC,"createTime");
        Integer ACTIVE = 0;
        return courseRepository.findByStatusOrderByCreateTimeAsc(ACTIVE, sort);
    }

    public List<Course> findTeacherCourses(TeacherDto teacherDto){
        return courseRepository.findByTeacherId(teacherDto.getTeacherId());
    }

    public Course createCourse(CourseDto courseDto){
        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        course.setExpire_date(TimeUtils.dateToTimestamp(courseDto.getExpireDate()));
        course.setTeacherId(courseDto.getCreatorId());
        course.setPassCode(courseDto.getPassCode());
        course.setStatus(0);
        User user = userRepository.findById(courseDto.getCreatorId()).get();
        user.addCourse(course);
        course.setTeacherName(user.getUserName());
        return courseRepository.save(course);
    }

    public Course editCourse(CourseDto courseDto, Long uid) throws CannotEditException {
        Course course = courseRepository.findByCourseId(courseDto.getCourseId());
        if(uid == course.getTeacherId())
        {
            course.setCourseName(courseDto.getCourseName());
            course.setExpire_date(TimeUtils.dateToTimestamp(courseDto.getExpireDate()));
            course.setPassCode(courseDto.getPassCode());
            return  courseRepository.save(course);
        }
        else {
            throw new CannotEditException("仅创建者教师可修改");
        }
    }

    public void deleteCourse(CourseDto courseDto) {
        Course course = courseRepository.findByCourseId(courseDto.getCourseId());
        for(User user: course.getUsers())
        {
            user.getCourse().remove(course);
        }
        courseRepository.delete(course);
    }

    public Course applyCourse(ApplyCourseDto applyCourseDto) throws PassCodeWrongException {
        Course course = courseRepository.findByCourseId(applyCourseDto.getCourseId());
        if(course.getPassCode().equals(applyCourseDto.getPasscode())){
            User user = userRepository.findById(applyCourseDto.getUid()).get();
            course.addUser(user);
            user.addCourse(course);
            userRepository.save(user);
            return courseRepository.save(course);
        }
        else
        {
            throw new PassCodeWrongException("课程码验证失败");
        }
    }

    public List<Course> findNotMyCourses(UserDto userDto) {
        return courseRepository.findByUsers_IdIsNotOrderByCreateTimeAsc(userDto.getId());
    }
}
