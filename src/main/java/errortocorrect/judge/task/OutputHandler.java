package errortocorrect.judge.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

public class OutputHandler implements Runnable{

    private InputStream in;
    private String output;
    private String charset;

    public OutputHandler(InputStream in, String charset) {
        this.in = in;

        this.charset = charset;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(in,charset));
            output = reader.lines().collect(Collectors.joining());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public String getOutput() {
        return output;
    }
}
