package errortocorrect.exception;

public class PassCodeWrongException extends  Exception{
private String msg;
    public PassCodeWrongException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
