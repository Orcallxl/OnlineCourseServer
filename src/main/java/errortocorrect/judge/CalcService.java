package errortocorrect.judge;

import errortocorrect.dto.JudgeDto;
import errortocorrect.dto.RecordDto;
import errortocorrect.dto.RunDto;
import errortocorrect.dto.RunRetDto;
import errortocorrect.entity.Puzzle;
import errortocorrect.exception.CompileAndRunCodeException;
import errortocorrect.judge.task.OutputHandler;
import errortocorrect.repository.PuzzleRepository;
import errortocorrect.service.RecordService;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CalcService {

    private static final Logger logger = LogManager.getLogger("CalcService");

    @Autowired
    PuzzleRepository puzzleRepository;

    @Autowired
    RecordService recordService;

    public JudgeDto judgeCode(RecordDto recordDto)
    {
        Puzzle puzzle = puzzleRepository.findById(recordDto.getPuzzleId());
        recordService.createRecord(recordDto);

        RunDto runDto = new RunDto();
        runDto.setCode(recordDto.getCode());
        runDto.setCompiler(recordDto.getCompiler());
        RunRetDto result = runCode(runDto);

        JudgeDto judgeDto = new JudgeDto();


        return new JudgeDto();
    }


    public RunRetDto runCode(RunDto runDto)
    {

        RunRetDto runRetDto = new RunRetDto();
        String seq = UUID.randomUUID().toString();
        try {
            switch (runDto.getCompiler()){
                case "cpp":
                    String[] cppEnv = new String[] {"path=C:\\Users\\rnbor\\Desktop\\教学平台\\MinGW32\\MinGW32\\bin"};
                    compileAndRun(runRetDto, seq, runDto.getCode(),"gb2312", cppEnv);
                    break;
                case "python":
                    String[] pyEnv = new String[] {"path=C:\\Users\\rnbor\\Desktop\\教学平台\\MinGW32\\MinGW32\\bin"};
                    compileAndRunPy(runRetDto, seq, runDto.getCode(),"utf-8", pyEnv);
                    break;
                default:
                    throw new CompileAndRunCodeException(3, "未找到对应编译器，请切换到其他语言");
            }
        }
        catch (CompileAndRunCodeException e)
        {
            runRetDto.setType(e.getType());
            runRetDto.setErrorOutput(e.getMsg());
        }
        finally {
            try {
                FileUtils.deleteDirectory(new File("./"+seq));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return runRetDto;
    }

    private void compileAndRun(RunRetDto runRetDto , String seq, String code, String charset, String[] env) throws CompileAndRunCodeException {
        try {
            //Create Folder
            FileUtils.forceMkdir(new File("./"+seq));

            //Create File
            String paths =System.getProperty("user.dir").concat("\\").concat(seq);
            File cppFile = new File(paths.concat("\\").concat("main.cpp"));

            cppFile.createNewFile();
            FileUtils.writeStringToFile(cppFile, code, charset);
            File path = new File(paths);

            //Compile

            Process compileProcess = Runtime.getRuntime().exec("cmd /c g++ main.cpp", env, path);
            BufferedReader compileStdInput = new BufferedReader(new
                    InputStreamReader(compileProcess.getInputStream(), charset));
            BufferedReader compileStdError = new BufferedReader(new
                    InputStreamReader(compileProcess.getErrorStream(),charset));

            String compileError = compileStdError.lines().collect(Collectors.joining());
            compileProcess.waitFor();
            if(!compileError.equals("") && compileError !=null)
            {
                logger.error("Compile Error, seq {}", seq);
                throw new CompileAndRunCodeException(1, compileError);
            }

            //Run
            Long startTime = System.currentTimeMillis();
            Long memBefore = Runtime.getRuntime().freeMemory();
            Process runProcess = Runtime.getRuntime().exec("./"+seq+"/a.exe");


//            BufferedReader runStdInput = new BufferedReader(new
//                    InputStreamReader(runProcess.getInputStream(),charset));
//            BufferedReader runStdError = new BufferedReader(new
//                    InputStreamReader(runProcess.getErrorStream(),charset));
            Long memAfter = Runtime.getRuntime().freeMemory();
            runProcess.waitFor(30, TimeUnit.SECONDS);

            Long endTime = System.currentTimeMillis();


            logger.info("seq {} time consuming: {}ms, mem consuming: {}kb",seq, endTime - startTime, ((double)memBefore-(double)memAfter)/1000);
            runRetDto.setTimeConsuming(endTime - startTime);
            runRetDto.setMemConsuming(((double)memBefore-(double)memAfter)/1000);

            OutputHandler stdHandler = new OutputHandler(runProcess.getInputStream(),"gb2312");
            Thread stdThread = new Thread(stdHandler);
            stdThread.start();
            stdThread.join();

            OutputHandler errorHandler = new OutputHandler(runProcess.getErrorStream(),"gb2312");
            Thread errorThread = new Thread(errorHandler);
            errorThread.start();
            errorThread.join();

           // String runStd = runStdInput.lines().collect(Collectors.joining());
            String runStd = stdHandler.getOutput();
           // String runError = runStdError.lines().collect(Collectors.joining());
            String runError = errorHandler.getOutput();


            if(!runError.equals("") && runError !=null)
            {
                logger.error("Runtime Error, seq {}", seq);
                throw new CompileAndRunCodeException(2, runError);
            }
            runRetDto.setStdOutput(runStd);
            runRetDto.setType(0);

            logger.info("Success, seq {}", seq);

            compileStdInput.close();
            compileStdError.close();
//            runStdInput.close();
//            runStdError.close();

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
        }
    }

    private void compileAndRunPy(RunRetDto runRetDto, String seq, String code, String charset, String[] env)throws CompileAndRunCodeException{
        try {
            //Create Folder
            FileUtils.forceMkdir(new File("./" + seq));

            //Create File
            String paths = System.getProperty("user.dir").concat("\\").concat(seq);
            File cppFile = new File(paths.concat("\\").concat("main.py"));

            cppFile.createNewFile();
            FileUtils.writeStringToFile(cppFile, code, charset);
            File path = new File(paths);

            //Run
            Process runProcess = Runtime.getRuntime().exec("py "+"./"+seq+"/main.py",env);

            runProcess.waitFor();

            OutputHandler stdHandler = new OutputHandler(runProcess.getInputStream(),charset);
            Thread stdThread = new Thread(stdHandler);
            stdThread.start();
            stdThread.join();

            OutputHandler errorHandler = new OutputHandler(runProcess.getErrorStream(),charset);
            Thread errorThread = new Thread(errorHandler);
            errorThread.start();
            errorThread.join();

            String runStd = stdHandler.getOutput();
            String runError = errorHandler.getOutput();


            if(!runError.equals("") && runError !=null)
            {
                logger.error("Runtime Error, seq {}", seq);
                throw new CompileAndRunCodeException(2, runError);
            }
            runRetDto.setStdOutput(runStd);
            runRetDto.setType(0);

            logger.info("Success, seq {}", seq);
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
