package errortocorrect.controller;

import errortocorrect.dto.AjaxResult;
import errortocorrect.dto.CourseDto;
import errortocorrect.dto.ExpDto;
import errortocorrect.exception.CannotDeleteException;
import errortocorrect.exception.CannotEditException;
import errortocorrect.exception.NotFoundException;
import errortocorrect.global.Const;
import errortocorrect.service.ExpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/exp")
public class ExpController {

    @Autowired
    ExpService expService;

    @RequestMapping(value = "/course-exps", method = RequestMethod.POST)
    public AjaxResult courseExps(@RequestBody CourseDto courseDto) {
        return AjaxResult.success(expService.findCourseExps(courseDto));
    }

    @RequestMapping(value = "/find-exp", method = RequestMethod.POST)
    public AjaxResult findExp(@RequestBody ExpDto expDto) {
        return AjaxResult.success(expService.findExp(expDto));
    }

    @RequestMapping(value = "/create-exp", method = RequestMethod.POST)
    public AjaxResult createExp(@RequestBody ExpDto expDto) {
        return AjaxResult.success(expService.createExp(expDto));
    }

    @RequestMapping(value = "/edit-exp", method = RequestMethod.POST)
    public AjaxResult editExp(@RequestBody ExpDto expDto, HttpServletRequest request) {
        Long uid = (Long) request.getSession().getAttribute(Const.SESSION_UID);
        try {
            return AjaxResult.success(expService.editExp(expDto,uid));
        } catch (Exception e) {
          return AjaxResult.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/delete-exp", method = RequestMethod.POST)
    public AjaxResult deleteExp(@RequestBody ExpDto expDto, HttpServletRequest request) {
        Long uid = (Long) request.getSession().getAttribute(Const.SESSION_UID);
        try {
            expService.deleteExp(expDto,uid);
        } catch (Exception e) {
           return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }
}
