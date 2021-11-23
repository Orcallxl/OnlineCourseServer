package errortocorrect.controller;

import errortocorrect.dto.UserDto;
import errortocorrect.entity.Records;
import errortocorrect.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordService recordService;

    @RequestMapping(value = "/user-records", method = RequestMethod.POST)
    public List<Records> userRecords(@RequestBody UserDto userDto) {
        return recordService.findUserRecords(userDto);
    }
}
