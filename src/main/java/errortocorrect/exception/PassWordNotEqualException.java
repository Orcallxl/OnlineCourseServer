package errortocorrect.exception;

public class PassWordNotEqualException extends Exception{
    private String msg;

    public PassWordNotEqualException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
