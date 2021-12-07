package errortocorrect.controller;

import errortocorrect.dto.AjaxResult;
import errortocorrect.dto.CommitDto;
import errortocorrect.dto.JudgeDto;
import errortocorrect.judge.CalcService;
import errortocorrect.judge.task.CppAsyncServiceImpl;
import errortocorrect.service.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/calc")
public class CalcController {
    private static final Logger logger = LogManager.getLogger("CalcController");

    @Autowired
    private CourseService courseService;

    @Autowired
    private CalcService calcService;

    @Autowired
    private CppAsyncServiceImpl asyncService;

    @RequestMapping(value = "/run", method = RequestMethod.POST)
    public AjaxResult runCode(@RequestBody CommitDto commitDto) {
        try {
            return AjaxResult.success(calcService.commit(false,commitDto));
        } catch (Exception e) {
           return  AjaxResult.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public AjaxResult commit(@RequestBody CommitDto commitDto) {
        //生成提交记录
        try {
            return AjaxResult.success(calcService.commit(true,commitDto));
        } catch (Exception e) {
            return  AjaxResult.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/default-code-java", method = RequestMethod.POST)
    public int defaultCodeJava() {

        return 0;
    }

    @RequestMapping(value = "/default-code-cpp", method = RequestMethod.POST)
    public int defaultCodeCpp() {
        return 0;
    }
}
