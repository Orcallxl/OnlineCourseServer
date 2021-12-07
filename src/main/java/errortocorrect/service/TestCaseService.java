package errortocorrect.service;

import errortocorrect.dto.TestCaseDto;
import errortocorrect.entity.Puzzle;
import errortocorrect.entity.TestCase;
import errortocorrect.exception.PuzzleNotFoundException;
import errortocorrect.exception.TestCaseNotFoundException;
import errortocorrect.repository.PuzzleRepository;
import errortocorrect.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseService {
    @Autowired
    TestCaseRepository testCaseRepository;

    @Autowired
    PuzzleRepository puzzleRepository;

    public List<TestCase> findByPuzzleId(Long id)
    {
        return testCaseRepository.findByPuzzle_Id(id);
    }

    public TestCase createTestCase(TestCaseDto testCaseDto) throws PuzzleNotFoundException {
        Puzzle puzzle = puzzleRepository.findById(testCaseDto.getPuzzleId());
        if(puzzle !=null)
        {
            TestCase testCase = new TestCase();
            testCase.setInput(testCaseDto.getInput());
            testCase.setOutput(testCaseDto.getOutput());
            testCase.setPuzzle(puzzle);
            return testCaseRepository.save(testCase);
        }
        else{
            throw new PuzzleNotFoundException("未找到对应题目");
        }
    }

    public TestCase editTestCase(TestCaseDto testCaseDto) throws PuzzleNotFoundException, TestCaseNotFoundException {
        Puzzle puzzle = puzzleRepository.findById(testCaseDto.getPuzzleId());
        if(puzzle !=null)
        {
            TestCase testCase = testCaseRepository.findById(testCaseDto.getTestCaseId());
            if(testCase !=null)
            {
                testCase.setInput(testCaseDto.getInput());
                testCase.setOutput(testCaseDto.getOutput());
                testCase.setPuzzle(puzzle);
                return testCaseRepository.save(testCase);
            }
            else {
                throw new TestCaseNotFoundException("未找到对应测试用例");
            }
        }
        else{
            throw new PuzzleNotFoundException("未找到对应题目");
        }
    }

    public void deleteTestCase(TestCaseDto testCaseDto) throws PuzzleNotFoundException, TestCaseNotFoundException {
        Puzzle puzzle = puzzleRepository.findById(testCaseDto.getPuzzleId());
        if(puzzle !=null)
        {
            TestCase testCase = testCaseRepository.findById(testCaseDto.getTestCaseId());
            if(testCase !=null)
            {
                testCaseRepository.delete(testCase);
            }
            else {
                throw new TestCaseNotFoundException("未找到对应测试用例");
            }
        }
        else{
            throw new PuzzleNotFoundException("未找到对应题目");
        }
    }
}
