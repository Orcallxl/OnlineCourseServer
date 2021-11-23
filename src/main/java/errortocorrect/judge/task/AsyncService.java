package errortocorrect.judge.task;

import errortocorrect.dto.RecordDto;

public interface AsyncService {

    void executeAsync(RecordDto judgeDto);
}
