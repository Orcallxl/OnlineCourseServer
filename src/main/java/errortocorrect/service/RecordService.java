package errortocorrect.service;

import errortocorrect.dto.CommitDto;
import errortocorrect.dto.JudgeRetDto;
import errortocorrect.dto.RecordDto;
import errortocorrect.dto.UserDto;
import errortocorrect.entity.Puzzle;
import errortocorrect.entity.Records;
import errortocorrect.entity.User;
import errortocorrect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static errortocorrect.util.TimeUtils.stringToTimestamp;

@Service
public class RecordService {
    @Autowired
    RecordsRepository recordsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PuzzleRepository puzzleRepository;
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ExpRepository expRepository;


    public List<Records> findUserRecords(UserDto userDto) {
        return recordsRepository.findByUserId(userDto.getId());
    }

    public Records createRecord(CommitDto commitDto, JudgeRetDto judgeRes){
        User user = userRepository.findById(commitDto.getUserId()).orElse(null);
        Puzzle puzzle = puzzleRepository.findById(commitDto.getPuzzleId());
        Records record = new Records();
        record.setCode(commitDto.getCode());
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        record.setCommitDate(timestamp);
        record.setInfo(commitDto.getInfo());
        record.setLang(commitDto.getLang());
        record.setResult(judgeRes.getResult());
        record.setPuzzle(puzzle);
        record.setUser(user);
        record.setMemConsume(judgeRes.getAvgMemConsuming());
        record.setTimeConsume(judgeRes.getAvgTimeConsuming());
        record.setMaxMemConsume(judgeRes.getMaxMemConsuming());
        record.setMaxTimeConsume(judgeRes.getMaxTimeConsuming());
        record.setScore(judgeRes.getScore());
        return recordsRepository.save(record);
    }

    public List<Records> findUserPuzzleRecords(RecordDto recordDto) {
       return  recordsRepository.findByUser_IdAndPuzzle_IdOrderByCreateTimeDesc(recordDto.getUid(), recordDto.getPuzzleId());
    }

    public List<Records> findCourseRecords(Long courseId) {
        return recordsRepository.findByPuzzle_Course_CourseIdAndResultLike(courseId, "Accept");

    }

    public List<Records> findExpRecords(Long expId) {
        return recordsRepository.findByPuzzle_Exp_ExpIdAndResultLike(expId,"Accept");
    }
}
