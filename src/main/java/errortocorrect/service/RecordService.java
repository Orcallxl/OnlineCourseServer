package errortocorrect.service;

import errortocorrect.dto.RecordDto;
import errortocorrect.dto.UserDto;
import errortocorrect.entity.Records;
import errortocorrect.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static errortocorrect.util.TimeUtils.stringToTimestamp;

@Service
public class RecordService {
    @Autowired
    RecordsRepository recordsRepository;


    public List<Records> findUserRecords(UserDto userDto) {
        return recordsRepository.findByUserId(userDto.getId());
    }

    public Records createRecord(RecordDto recordDto){
        Records record = new Records();
        record.setCode(recordDto.getCode());
        record.setCommitDate(stringToTimestamp(recordDto.getCommitDate()));
        record.setInfo(recordDto.getInfo());
        record.setLang(recordDto.getLang());
        record.setResult(0);
        record.setPuzzleName(record.getPuzzleName());
        record.setPuzzleId(recordDto.getPuzzleId());
        record.setUserName(recordDto.getUserName());
        record.setUserId(recordDto.getUserId());
        return recordsRepository.save(record);
    }
}
