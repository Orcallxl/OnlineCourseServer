package errortocorrect.exception;

public class CompileAndRunCodeException extends Exception{
    private String type;//0为正常 1编译错误 2运行错误 3未找到编译器
    private String msg;

    public CompileAndRunCodeException(String type, String msg) {
        super(msg);
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
