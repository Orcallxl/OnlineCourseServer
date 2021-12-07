package errortocorrect.dto;

public class JudgeDto {
    Integer compiler;
    String code;
    String input;
    Integer puzzleId;

    public Integer getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(Integer puzzleId) {
        this.puzzleId = puzzleId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Integer getCompiler() {
        return compiler;
    }

    public void setCompiler(Integer compiler) {
        this.compiler = compiler;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
