package errortocorrect.judge.task;

import errortocorrect.dto.CommitDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class CppAsyncServiceImpl implements AsyncService{
    private static final Logger logger = LoggerFactory.getLogger(CppAsyncServiceImpl.class);
    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync(CommitDto dto) {
        String seq = UUID.randomUUID().toString();
        logger.info("Start cpp task");

        try {
            System.out.println("创建文件夹");

            Process newFolder = Runtime.getRuntime().exec(createFolderCMD(seq));
            newFolder.waitFor();
            String paths =System.getProperty("user.dir").replace("\\","/").concat("/").concat(seq).concat("/");


            System.out.println("创建cpp文件");
            System.out.println(paths);
            File path = new File(paths);
            Process createCpp = Runtime.getRuntime().exec(createCppFileCMD("main.cpp", dto.getCode()), null, path);
            createCpp.waitFor();

            System.out.println("编译");
            Process compile  = Runtime.getRuntime().exec(compileCMD("main.cpp"), null, path);
            compile.waitFor();

            System.out.println("运行");
            Process run = Runtime.getRuntime().exec(runCMD("main.exe"), null, path);
            run.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CPP运行");
        logger.info("end executeAsync");
    }

    private String createFolderCMD(String folderName)
    {
        return "cmd /c mkdir " + folderName;
    }

    private String createCppFileCMD(String fileName, String content)
    {
        return "cmd /c echo " + content + " > " +fileName;
    }

    private String compileCMD(String fileName)
    {
        return "cmd /c g++ " + fileName;
    }

    private String cdDir(String dir)
    {
        return "cmd /c cd " + dir;
    }

    private String runCMD(String pro)
    {
        return "cmd /c " + pro;
    }
}
