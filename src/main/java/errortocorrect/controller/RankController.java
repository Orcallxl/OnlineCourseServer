package errortocorrect.controller;


import errortocorrect.dto.AjaxResult;
import errortocorrect.dto.RankDto;
import errortocorrect.dto.UserDto;
import errortocorrect.entity.Records;
import errortocorrect.service.RankService;
import errortocorrect.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {
    @Autowired
    RecordService recordService;
    @Autowired
    RankService rankService;

    @RequestMapping(value = "/total-rank", method = RequestMethod.POST)
    public AjaxResult totalRank() {
        return AjaxResult.success(rankService.totalRank());
    }

    @RequestMapping(value = "/course-rank", method = RequestMethod.POST)
    public AjaxResult courseRank(@RequestBody RankDto rankDto) {
        return AjaxResult.success(rankService.courseRank(rankDto.getCourseId()));
    }

    @RequestMapping(value = "/exp-rank", method = RequestMethod.POST)
    public AjaxResult expRank(@RequestBody RankDto rankDto) {
        return AjaxResult.success(rankService.expRank(rankDto.getExpId()));
    }

    @RequestMapping(value = "/active-rank", method = RequestMethod.POST)
    public AjaxResult activeRank() {
        return AjaxResult.success(rankService.activeRank());
    }

}
