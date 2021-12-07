package errortocorrect.service;

import errortocorrect.dto.ActiveRankDto;
import errortocorrect.dto.RankDto;
import errortocorrect.entity.Records;
import errortocorrect.entity.User;
import errortocorrect.repository.RecordsRepository;
import errortocorrect.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankService {
    @Autowired
    RecordsRepository recordsRepository;
    @Autowired
    RecordService recordService;

    public List<RankDto> totalRank() {
        List<Records> records = recordsRepository.findByResultEquals("Accept");

        //过滤同一用户多次提交
        records = recordFiler(records);

        //分组计算总分
        List<RankDto> ranks = getRankDtos(records);
        return ranks;
    }

    private List<Records> recordFilerByUser(List<Records> records) {
        records = records.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getUserId()))), ArrayList::new)
        );
        return records;
    }

    private List<Records> recordFiler(List<Records> records) {
        records = records.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getPuzzleId() + ";" + o.getUserId()))), ArrayList::new)
        );
        return records;
    }

    private List<RankDto> getRankDtos(List<Records> records) {
        Map<User, Integer> map = records.stream().
                collect(Collectors.groupingBy(Records::getUser, LinkedHashMap::new, Collectors.summingInt(Records::getScore)))
                .entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue(), (x1, x2) -> x2, LinkedHashMap::new));

        List<RankDto> ranks = new ArrayList<>();
        int order = 1;
        for (Map.Entry entry : map.entrySet()) {
            RankDto rankDto = new RankDto();
            User user = (User) entry.getKey();
            rankDto.setOrder(order);
            rankDto.setUserName(user.getUserName());
            rankDto.setTotalScore((Integer) entry.getValue());
            rankDto.setAcceptNum(calcUserAcceptNum(user.getId(), records));
            order++;
            ranks.add(rankDto);
        }
        return ranks;
    }

//    private Double calcUserCorrectRate(Long id, List<Records> records) {
//        double c_sum = (double)calcUserAcceptNum(id, records);
//        double sum =  recordsRepository.findByUserId(id).size();
//        return c_sum/sum;
//    }

    private Integer calcUserAcceptNum(Long id, List<Records> records) {
        Integer sum = 0;
        for (Records record : records) {
            if (record.getUser().getId().equals(id)) {
                sum++;
            }
        }
        return sum;
    }

    public List<RankDto> courseRank(Long courseId) {
        //某个课程得所有提交记录
        List<Records> records = recordService.findCourseRecords(courseId);
        //过滤
        records = recordFiler(records);
        List<RankDto> ranks = getRankDtos(records);

        return ranks;
    }

    public List<RankDto> expRank(Long expId) {
        //某个课程得所有提交记录
        List<Records> records = recordService.findExpRecords(expId);
        //过滤
        records = recordFiler(records);
        List<RankDto> ranks = getRankDtos(records);

        return ranks;
    }

    public List<ActiveRankDto> activeRank() {
        PageRequest pageable = PageRequest.of(0, 10);
        List<Records> records = recordsRepository.findByOrderByCreateTimeDesc(pageable);
        records = recordFilerByUser(records);
        int order = 1;
        List<ActiveRankDto> list = new ArrayList<>();
        for (Records record : records) {
            ActiveRankDto activeRankDto = new ActiveRankDto();
            activeRankDto.setUserName(record.getUser().getUserName());
            activeRankDto.setOrder(order);
            activeRankDto.setLastCommit(TimeUtils.TimeStampToString(record.getCreateTime()));
            order++;
            list.add(activeRankDto);
        }
        return list;
    }
}