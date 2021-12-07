package errortocorrect.util;

public class JudgeResult {

    private Integer submitId;
    private Integer status;
    private Integer timeUsed;
    private Integer memoryUsed;
    private String errorMessage;

    public JudgeResult() {

    }

    public JudgeResult(Integer submitId, Integer status, Integer timeUsed, Integer memoryUsed, String errorMessage) {
        this.submitId = submitId;
        this.status = status;
        this.timeUsed = timeUsed;
        this.memoryUsed = memoryUsed;
        this.errorMessage = errorMessage;
    }

    public Integer getSubmitId() {
        return submitId;
    }

    public void setSubmitId(Integer submitId) {
        this.submitId = submitId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Integer timeUsed) {
        this.timeUsed = timeUsed;
    }

    public Integer getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Integer memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "JudgeResult{" +
                "submitId=" + submitId +
                ", status=" + status +
                ", timeUsed=" + timeUsed +
                ", memoryUsed=" + memoryUsed +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
