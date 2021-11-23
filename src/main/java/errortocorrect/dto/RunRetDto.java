package errortocorrect.dto;

public class RunRetDto {
    private Integer type;
    private String stdInput;
    private String stdOutput;
    private String errorOutput;
    private Long timeConsuming;
    private Double memConsuming;//M

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStdInput() {
        return stdInput;
    }

    public void setStdInput(String stdInput) {
        this.stdInput = stdInput;
    }

    public String getStdOutput() {
        return stdOutput;
    }

    public void setStdOutput(String stdOutput) {
        this.stdOutput = stdOutput;
    }

    public String getErrorOutput() {
        return errorOutput;
    }

    public void setErrorOutput(String errorOutput) {
        this.errorOutput = errorOutput;
    }

    public Long getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(Long timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    public Double getMemConsuming() {
        return memConsuming;
    }

    public void setMemConsuming(Double memConsuming) {
        this.memConsuming = memConsuming;
    }
}
