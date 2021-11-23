package errortocorrect.exception;

public class CannotEditException extends Exception{
    private String msg;

    public CannotEditException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
