package errortocorrect.exception;

public class PuzzleNotFoundException extends Exception{
    private String msg;

    public PuzzleNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
