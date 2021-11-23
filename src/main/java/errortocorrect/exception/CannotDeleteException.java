package errortocorrect.exception;

public class CannotDeleteException extends Exception{

    private String msg;

    public CannotDeleteException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
