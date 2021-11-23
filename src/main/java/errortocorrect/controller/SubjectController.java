package errortocorrect.controller;

import errortocorrect.dto.AjaxResult;
import errortocorrect.dto.ReplyDto;
import errortocorrect.dto.SubjectDto;
import errortocorrect.entity.Subject;
import errortocorrect.global.Const;
import errortocorrect.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @RequestMapping(value = "/all-subject-latest", method = RequestMethod.GET)
    public AjaxResult allSubjectLatest() {
        return AjaxResult.success(subjectService.subjectListLatest());
    }

    @RequestMapping(value = "/all-subject-hottest", method = RequestMethod.GET)
    public AjaxResult allSubjectHottest() {
        return AjaxResult.success(subjectService.subjectListHottest());
    }

    @RequestMapping(value = "/all-subject-hottest-limit", method = RequestMethod.GET)
    public AjaxResult allSubjectHottestLimit() {
        List<Subject> subjectList = subjectService.subjectListHottest();
        if(subjectList.size()>10)
        {
            return AjaxResult.success(subjectList.subList(0,10));
        }
        return AjaxResult.success(subjectList);
    }

    @RequestMapping(value = "/create-subject", method = RequestMethod.POST)
    public AjaxResult createSubject(@RequestBody SubjectDto subjectDto, HttpServletRequest request) {
        Long uid = (Long) request.getSession().getAttribute(Const.SESSION_UID);
        return AjaxResult.success(subjectService.createSubject(subjectDto, uid));
    }

    @RequestMapping(value = "/thumb-subject", method = RequestMethod.POST)
    public AjaxResult thumbUp(@RequestBody SubjectDto subjectDto) {

        return AjaxResult.success(subjectService.thumbSubject(subjectDto));
    }

    @RequestMapping(value = "/thumb-down-subject", method = RequestMethod.POST)
    public AjaxResult thumbDown(@RequestBody SubjectDto subjectDto) {

        return AjaxResult.success(subjectService.thumbDownDownSubject(subjectDto));
    }

    @RequestMapping(value = "/my-subject", method = RequestMethod.GET)
    public AjaxResult mySubject( HttpServletRequest request) {
        Long uid = (Long) request.getSession().getAttribute(Const.SESSION_UID);
        return AjaxResult.success(subjectService.mySubject(uid));
    }

    @RequestMapping(value = "/subject-detail", method = RequestMethod.POST)
    public AjaxResult subjectDetail( @RequestBody SubjectDto subjectDto) {
        return AjaxResult.success(subjectService.subjectDetail(subjectDto));
    }

    @RequestMapping(value = "/reply-subject", method = RequestMethod.POST)
    public AjaxResult replySubject( @RequestBody ReplyDto replyDto) {
        return AjaxResult.success(subjectService.replySubject(replyDto));
    }

    @RequestMapping(value = "/delete-subject", method = RequestMethod.POST)
    public AjaxResult deletesSubject( @RequestBody SubjectDto subjectDto) {
        subjectService.deletesSubject(subjectDto);
        return AjaxResult.success("删除成功");
    }



}
