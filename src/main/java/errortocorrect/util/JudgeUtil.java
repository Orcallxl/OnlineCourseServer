package errortocorrect.util;

import errortocorrect.judge.task.OutputHandler;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class JudgeUtil {
    public static String Judge(String compiler, String input, String stdOutput, String timeLimit, String memLimit, String seqId){
        //复制脚本
        try {
            FileUtils.copyFile(new File("./judge.py"), new File("./"+seqId+"/judge.py"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //判题
        try {
            String[] args = new String[] {"python", "./"+seqId+"/judge.py",compiler,input,stdOutput,timeLimit,memLimit,seqId};
            Process runProcess = Runtime.getRuntime().exec(args);
            runProcess.waitFor();

            OutputHandler stdHandler = new OutputHandler(runProcess.getInputStream(),"gb2312");
            Thread stdThread = new Thread(stdHandler);
            stdThread.start();
            stdThread.join();

            OutputHandler errHandler = new OutputHandler(runProcess.getErrorStream(),"gb2312");
            Thread errThread = new Thread(errHandler);
            errThread.start();
            errThread.join();

            System.out.println("*************"+stdHandler.getOutput()+"************");
            System.out.println("*************"+errHandler.getOutput()+"************");
            return stdHandler.getOutput();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
       return "['Error']";
}}
