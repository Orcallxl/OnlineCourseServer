package errortocorrect.util;

public class JudgeTask {

    public JudgeTask() {
    }

    public JudgeTask(String appName, int submitId, int compilerId, int problemId, String source, int timeLimit, int memoryLimit, boolean isSpecial) {
        this.appName = appName;
        this.submitId = submitId;
        this.compilerId = compilerId;
        this.problemId = problemId;
        this.source = source;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.isSpecial = isSpecial;
    }

    private String appName;

    private int submitId;

    private int compilerId;

    private int problemId;

    private String source;

    private int timeLimit;

    private int memoryLimit;

    private boolean isSpecial;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getSubmitId() {
        return submitId;
    }

    public void setSubmitId(int submitId) {
        this.submitId = submitId;
    }

    public int getCompilerId() {
        return compilerId;
    }

    public void setCompilerId(int compilerId) {
        this.compilerId = compilerId;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }

    @Override
    public String toString() {
        return "JudgeTask{" +
                "appName='" + appName + '\'' +
                ", submitId=" + submitId +
                ", compilerId=" + compilerId +
                ", problemId=" + problemId +
                ", source='" + source + '\'' +
                ", timeLimit=" + timeLimit +
                ", memoryLimit=" + memoryLimit +
                ", isSpecial=" + isSpecial +
                '}';
    }
}
