package errortocorrect.dto;

public class JudgeRetDto {

    private String result;
    private String stdInput;
    private String yourOutput;
    private String expectedOutput;

    private String runTimeErrorOutput;

    private Double maxTimeConsuming;//MS
    private Double maxMemConsuming;//KB
    private Double avgTimeConsuming;//MS
    private Double avgMemConsuming;//KB

    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRunTimeErrorOutput() {
        return runTimeErrorOutput;
    }

    public void setRunTimeErrorOutput(String runTimeErrorOutput) {
        this.runTimeErrorOutput = runTimeErrorOutput;
    }

    public String getYourOutput() {
        return yourOutput;
    }

    public void setYourOutput(String yourOutput) {
        this.yourOutput = yourOutput;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public Double getMaxTimeConsuming() {
        return maxTimeConsuming;
    }

    public void setMaxTimeConsuming(Double maxTimeConsuming) {
        this.maxTimeConsuming = maxTimeConsuming;
    }

    public Double getMaxMemConsuming() {
        return maxMemConsuming;
    }

    public void setMaxMemConsuming(Double maxMemConsuming) {
        this.maxMemConsuming = maxMemConsuming;
    }

    public Double getAvgTimeConsuming() {
        return avgTimeConsuming;
    }

    public void setAvgTimeConsuming(Double avgTimeConsuming) {
        this.avgTimeConsuming = avgTimeConsuming;
    }

    public Double getAvgMemConsuming() {
        return avgMemConsuming;
    }

    public void setAvgMemConsuming(Double avgMemConsuming) {
        this.avgMemConsuming = avgMemConsuming;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStdInput() {
        return stdInput;
    }

}
