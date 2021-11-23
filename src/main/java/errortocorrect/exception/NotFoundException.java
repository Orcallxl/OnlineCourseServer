package errortocorrect.exception;

public class NotFoundException extends Exception{
    private String msg;

    public NotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
