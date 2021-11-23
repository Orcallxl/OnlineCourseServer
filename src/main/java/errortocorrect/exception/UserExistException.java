package errortocorrect.exception;

public class UserExistException extends Exception{
    private String msg;

    public UserExistException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
