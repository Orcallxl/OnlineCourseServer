package errortocorrect.judge;

import com.alibaba.fastjson.JSON;
import errortocorrect.dto.JudgeRetDto;
import errortocorrect.dto.CommitDto;
import errortocorrect.dto.JudgeDto;
import errortocorrect.entity.Puzzle;
import errortocorrect.entity.TestCase;
import errortocorrect.exception.CompileAndRunCodeException;
import errortocorrect.exception.PuzzleNotFoundException;
import errortocorrect.exception.TestCaseNotFoundException;
import errortocorrect.global.Const;
import errortocorrect.judge.pojo.JudgeResult;
import errortocorrect.judge.task.OutputHandler;
import errortocorrect.repository.PuzzleRepository;
import errortocorrect.repository.TestCaseRepository;
import errortocorrect.service.RecordService;
import errortocorrect.util.JudgeUtil;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CalcService {

    private static final Logger logger = LogManager.getLogger("CalcService");

    @Autowired
    PuzzleRepository puzzleRepository;

    @Autowired
    RecordService recordService;

    @Autowired
    TestCaseRepository testCaseRepository;

    public JudgeRetDto commit(boolean commit, CommitDto commitDto) throws IOException, TestCaseNotFoundException, InterruptedException, PuzzleNotFoundException {
        JudgeRetDto result =  judge(commitDto);;
        if(commit)
        {
            recordService.createRecord(commitDto,result);
        }
        return result;
    }


    public JudgeRetDto judge(CommitDto commitDto) throws TestCaseNotFoundException, PuzzleNotFoundException, IOException, InterruptedException {
        JudgeRetDto judgeRetDto = new JudgeRetDto();
        String seq = UUID.randomUUID().toString();
        try {
            switch (commitDto.getCompiler()) {
                case 1:
                    //编译环境
                    String[] cppEnv = new String[]{"path=C:\\Users\\rnbor\\Desktop\\教学平台\\MinGW32\\MinGW32\\bin"};
                    return compileAndJudge(commitDto, seq,"gb2312", cppEnv);

                case 0:
                    String[] pyEnv = new String[]{"path=C:\\Users\\rnbor\\Desktop\\教学平台\\MinGW32\\MinGW32\\bin"};
                    return compileAndJudgePy(commitDto, seq, "utf-8", pyEnv);
                default:
                    throw new CompileAndRunCodeException(Const.CompilerNotFound, "未找到对应编译器，请切换到其他语言");
            }
        } catch (CompileAndRunCodeException e) {
            judgeRetDto.setResult(e.getType());
        } finally {
            try {
                FileUtils.forceDelete(new File("./" + seq));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return judgeRetDto;
    }



    private JudgeRetDto compileAndJudge(CommitDto commitDto, String seq, String charset, String[] env) throws CompileAndRunCodeException, PuzzleNotFoundException, TestCaseNotFoundException, IOException, InterruptedException {

        JudgeRetDto judgeRetDto = new JudgeRetDto();
        //Create Folder
        FileUtils.forceMkdir(new File("./" + seq));

        //Create File
        String paths = System.getProperty("user.dir").concat("\\").concat(seq);
        File cppFile = new File(paths.concat("\\").concat("main.cpp"));

        cppFile.createNewFile();
        FileUtils.writeStringToFile(cppFile, commitDto.getCode(), charset);
        File path = new File(paths);

        //Compile

        Process compileProcess = Runtime.getRuntime().exec("cmd /c g++ main.cpp", env, path);
        BufferedReader compileStdInput = new BufferedReader(new
                InputStreamReader(compileProcess.getInputStream(), charset));
        BufferedReader compileStdError = new BufferedReader(new
                InputStreamReader(compileProcess.getErrorStream(), charset));

        String compileError = compileStdError.lines().collect(Collectors.joining());
        compileProcess.waitFor();
        if (!compileError.equals("") && compileError != null) {
            logger.error("Compile Error, seq {}", seq);
            throw new CompileAndRunCodeException(Const.CompileError, compileError);
        }

        Puzzle puzzle = puzzleRepository.findById(commitDto.getPuzzleId());
        if (puzzle != null) {
            List<TestCase> testCases = testCaseRepository.findByPuzzle_Id(puzzle.getId());
            String timeLimit = puzzle.getTimeLimit().toString();
            String memLimit = puzzle.getMemLimit().toString();
            Double max_time = 0.0;
            Double max_mem = 0.0;
            Double sum_time = 0.0;
            Double sum_mem = 0.0;
            int sum = 0;

            if (testCases.size() == 0) {
                throw new TestCaseNotFoundException("该题目还没有测试用例！请联系管理员补充。");
            }
            for (TestCase testCase : testCases) {
                String stdInput = testCase.getInput();
                String stdOutput = testCase.getOutput();
                String resultJson = JudgeUtil.Judge(commitDto.getCompiler().toString(), stdInput, stdOutput, timeLimit, memLimit, seq);
                JudgeResult result = JSON.parseObject(resultJson,JudgeResult.class);
                if (result.getResult().equals(Const.Accept)) {
                    sum += 1;
                } else if (result.getResult().equals(Const.WrongAnswer)) {
                    judgeRetDto.setResult(result.getResult());
                    judgeRetDto.setYourOutput(result.getStdOut());
                    judgeRetDto.setExpectedOutput(testCase.getOutput());
                } else if (result.getResult().equals(Const.RuntimeError)) {
                    judgeRetDto.setResult(result.getResult());
                    judgeRetDto.setRunTimeErrorOutput(result.getStdErr());
                } else {
                    judgeRetDto.setResult(result.getResult());
                }

                if (result.getTime() > max_time) {
                    max_time = result.getTime();
                }
                if (result.getMem() > max_mem) {
                    max_mem = result.getMem();
                }
                sum_time += result.getTime();
                sum_mem += result.getMem();
            }

            if (sum == testCases.size()) {
                judgeRetDto.setResult(Const.Accept);
            }

            judgeRetDto.setAvgMemConsuming(sum_mem / (double) testCases.size());
            judgeRetDto.setAvgTimeConsuming((sum_time / (double) testCases.size()));
            judgeRetDto.setMaxMemConsuming(max_mem);
            judgeRetDto.setMaxTimeConsuming(max_time);
            judgeRetDto.setScore((int)(((double)sum/(double)testCases.size())*100));
        } else {
            throw new PuzzleNotFoundException("未找到对应题目");
        }

        logger.info("Success, seq {}", seq);
        compileStdInput.close();
        compileStdError.close();
        return judgeRetDto;
    }

    private JudgeRetDto compileAndJudgePy(CommitDto commitDto, String seq,String charset, String[] env) throws CompileAndRunCodeException, TestCaseNotFoundException, PuzzleNotFoundException {
        JudgeRetDto judgeRetDto = new JudgeRetDto();
        try {

            //Create Folder
            FileUtils.forceMkdir(new File("./" + seq));

            //Create File
            String paths = System.getProperty("user.dir").concat("\\").concat(seq);
            File cppFile = new File(paths.concat("\\").concat("main.py"));

            cppFile.createNewFile();
            FileUtils.writeStringToFile(cppFile, commitDto.getCode(), charset);
            File path = new File(paths);

            //Run
            Puzzle puzzle = puzzleRepository.findById(commitDto.getPuzzleId());
            if (puzzle != null) {
                List<TestCase> testCases = testCaseRepository.findByPuzzle_Id(puzzle.getId());
                String timeLimit = puzzle.getTimeLimit().toString();
                String memLimit = puzzle.getMemLimit().toString();
                Double max_time = 0.0;
                Double max_mem = 0.0;
                Double sum_time = 0.0;
                Double sum_mem = 0.0;
                int sum = 0;

                if (testCases.size() == 0) {
                    throw new TestCaseNotFoundException("该题目还没有测试用例！请联系管理员补充。");
                }
                for (TestCase testCase : testCases) {
                    String stdInput = testCase.getInput();
                    String stdOutput = testCase.getOutput();
                    String resultJson = JudgeUtil.Judge(commitDto.getCompiler().toString(), stdInput, stdOutput, timeLimit, memLimit, seq);
                    JudgeResult result =  JSON.parseObject(resultJson,JudgeResult.class);
                    if (result.getResult().equals(Const.Accept)) {
                        sum += 1;
                    } else if (result.getResult().equals(Const.WrongAnswer)) {
                        judgeRetDto.setResult(result.getResult());
                        judgeRetDto.setYourOutput(result.getStdOut());
                        judgeRetDto.setExpectedOutput(testCase.getOutput());
                    } else if (result.getResult().equals(Const.RuntimeError)) {
                        judgeRetDto.setResult(result.getResult());
                        judgeRetDto.setRunTimeErrorOutput(result.getStdErr());
                    } else {
                        judgeRetDto.setResult(result.getResult());
                    }

                    if (result.getTime() > max_time) {
                        max_time = result.getTime();
                    }
                    if (result.getMem() > max_mem) {
                        max_mem = result.getMem();
                    }
                    sum_time += result.getTime();
                    sum_mem += result.getMem();
                }

                if (sum == testCases.size()) {
                    judgeRetDto.setResult(Const.Accept);
                }

                judgeRetDto.setAvgMemConsuming(sum_mem / (double) testCases.size());
                judgeRetDto.setAvgTimeConsuming((sum_time / (double) testCases.size()));
                judgeRetDto.setMaxMemConsuming(max_mem);
                judgeRetDto.setMaxTimeConsuming(max_time);
                judgeRetDto.setScore((int)(((double)sum/(double)testCases.size())*100));
            } else {
                throw new PuzzleNotFoundException("未找到对应题目");
            }
            logger.info("Success, seq {}", seq);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return judgeRetDto;
    }
}
