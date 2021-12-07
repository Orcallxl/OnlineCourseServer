package errortocorrect.util;

public class ExecMessage {
    public ExecMessage() {

    }

    public ExecMessage(String error, String stdout) {
        this.error = error;
        this.stdout = stdout;
    }

    private String error;

    private String stdout;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }
}
