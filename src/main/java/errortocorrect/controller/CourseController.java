package errortocorrect.controller;

import errortocorrect.dto.*;
import errortocorrect.entity.Course;
import errortocorrect.exception.CannotEditException;
import errortocorrect.exception.PassCodeWrongException;
import errortocorrect.global.Const;
import errortocorrect.service.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/course")
public class CourseController {

    private static final Logger logger = LogManager.getLogger("CourseController");

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/all-active-courses", method = RequestMethod.GET)
    public AjaxResult activeCourseList() {
        return AjaxResult.success(courseService.findActiveCourses());
    }

    @RequestMapping(value = "/not-my-courses", method = RequestMethod.POST)
    public AjaxResult notMyCourseList(UserDto userDto) {
        return AjaxResult.success(courseService.findNotMyCourses(userDto));
    }

    @RequestMapping(value = "/teacher-courses", method = RequestMethod.POST)
    public AjaxResult teacherCourseList(@RequestBody TeacherDto teacherDto) {
        return AjaxResult.success(courseService.findTeacherCourses(teacherDto));
    }

    @RequestMapping(value = "/new-course", method = RequestMethod.POST)
    public AjaxResult newCourse(@RequestBody CourseDto course) {
        return AjaxResult.success(courseService.createCourse(course));
    }

    @RequestMapping(value = "/apply-course", method = RequestMethod.POST)
    public AjaxResult applyCourse(@RequestBody ApplyCourseDto applyCourseDto) {
        try {
            Course course  = courseService.applyCourse(applyCourseDto);
            return AjaxResult.success(course);
        } catch (PassCodeWrongException e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }

    }

    @RequestMapping(value = "/edit-course", method = RequestMethod.POST)
    public AjaxResult editCourse(@RequestBody CourseDto course, HttpServletRequest request) {
        try {
            Long uid = (Long) request.getSession().getAttribute(Const.SESSION_UID);
            return AjaxResult.success(courseService.editCourse(course, uid));
        } catch (CannotEditException e) {
            return AjaxResult.error(e.getMsg());
        }
    }

    @RequestMapping(value = "/delete-course", method = RequestMethod.POST)
    public AjaxResult deleteCourse(@RequestBody CourseDto course) {
        courseService.deleteCourse(course);
        return AjaxResult.success();
    }
}
