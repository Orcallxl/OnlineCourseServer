package errortocorrect.judge.task;

import errortocorrect.dto.CommitDto;

public interface AsyncService {

    void executeAsync(CommitDto judgeDto);
}
