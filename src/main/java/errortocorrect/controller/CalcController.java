package errortocorrect.controller;

import errortocorrect.dto.AjaxResult;
import errortocorrect.dto.RecordDto;
import errortocorrect.dto.RunDto;
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
    public AjaxResult runCode(@RequestBody RunDto runDto) {
        return AjaxResult.success(calcService.runCode(runDto));
    }



    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public int commit(@RequestBody RecordDto recordDto) {

        //生成提交记录
        calcService.judgeCode(recordDto);
        //异步编译运行检查
       // asyncService.executeAsync(recordDto);
        return 0;
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
