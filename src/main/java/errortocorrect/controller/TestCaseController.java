package errortocorrect.controller;

import errortocorrect.dto.AjaxResult;
import errortocorrect.dto.TestCaseDto;
import errortocorrect.exception.PuzzleNotFoundException;
import errortocorrect.exception.TestCaseNotFoundException;
import errortocorrect.service.TestCaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/testcase")
public class TestCaseController {
    @Autowired
    TestCaseService testCaseService;

    private static final Logger logger = LogManager.getLogger("TestCaseController");

    @RequestMapping(value = "/find-testcases", method = RequestMethod.POST)
    public AjaxResult findTestCases(@RequestBody TestCaseDto testCaseDto) {
        return AjaxResult.success(testCaseService.findByPuzzleId(testCaseDto.getPuzzleId()));

    }

    @RequestMapping(value = "/create-testcase", method = RequestMethod.POST)
    public AjaxResult createTestCase(@RequestBody  TestCaseDto testCaseDto) {
        try {
            return AjaxResult.success(testCaseService.createTestCase(testCaseDto));
        } catch (PuzzleNotFoundException e) {
            return AjaxResult.error(e.getMsg());
        }
    }

    @RequestMapping(value = "/edit-testcase", method = RequestMethod.POST)
    public AjaxResult editTestCase(@RequestBody TestCaseDto testCaseDto) {
            try {
                return AjaxResult.success(testCaseService.editTestCase(testCaseDto));
            } catch (TestCaseNotFoundException | PuzzleNotFoundException e) {
                return AjaxResult.error(e.getMessage());
            }
    }

    @RequestMapping(value = "/delete-testcase", method = RequestMethod.POST)
    public AjaxResult deleteTestCase(@RequestBody TestCaseDto testCaseDto) {
        try {
            testCaseService.deleteTestCase(testCaseDto);
            return AjaxResult.success("删除成功");
        } catch (PuzzleNotFoundException | TestCaseNotFoundException e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
