package errortocorrect.exception;

public class TestCaseNotFoundException extends Exception{
    private String msg;

    public TestCaseNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
