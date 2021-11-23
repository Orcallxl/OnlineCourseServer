package errortocorrect.service;

import errortocorrect.dto.ExpDto;
import errortocorrect.dto.PuzzleDto;
import errortocorrect.dto.UserDto;
import errortocorrect.entity.Course;
import errortocorrect.entity.Exp;
import errortocorrect.entity.Puzzle;
import errortocorrect.exception.CannotDeleteException;
import errortocorrect.exception.CannotEditException;
import errortocorrect.exception.NotFoundException;
import errortocorrect.repository.CourseRepository;
import errortocorrect.repository.ExpRepository;
import errortocorrect.repository.PuzzleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PuzzleService {
    @Autowired
    PuzzleRepository puzzleRepository;

    @Autowired
    ExpRepository expRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;


    public List<Puzzle> findExpPuzzles(ExpDto expDto) {
        return puzzleRepository.findByExpId(expDto.getExpId());
    }

    public List<Puzzle> allPuzzle() {
        return puzzleRepository.findAll();
    }

    public List<Puzzle> findUserPuzzles(UserDto userDto) {
        return  courseService.findUserCurrentCourses(userDto)
                .stream().map(s->puzzleRepository.findByCourseId(s.getCourseId()))
                .flatMap(s -> s.stream())
                .collect(Collectors.toList());
    }

    public Puzzle findPuzzleById(PuzzleDto puzzleDto) {
        return puzzleRepository.findById(puzzleDto.getPuzzleId());
    }

    public Puzzle createPuzzle(PuzzleDto puzzleDto) {
        Puzzle puzzle = new Puzzle();
        Exp exp = expRepository.findByExpId(puzzleDto.getExpId());
        Course course = courseRepository.findByCourseId(exp.getCourse().getCourseId());
        puzzle.setCourse(course);
        puzzle.setCourseName(course.getCourseName());
        puzzle.setPuzzleName(puzzleDto.getPuzzleName());
        puzzle.setExp(exp);
        puzzle.setExpName(puzzleDto.getExpName());
        puzzle.setCodeTemplate(puzzleDto.getCodeTemplate());
        puzzle.setDescription(puzzleDto.getDescription());
        puzzle.setInputDescrip(puzzleDto.getInputDescription());
        puzzle.setOutputDescrip(puzzleDto.getOutputDescription());
        puzzle.setMemLimit(puzzleDto.getMemLimit());
        puzzle.setTimeLimit(puzzleDto.getTimeLimit());
        puzzle.setPoint(puzzleDto.getPoint());
        puzzle.setDifficulty(puzzleDto.getDifficulty());
        puzzle.setTeacherName(course.getTeacherName());
        exp.addPuzzle(puzzle);
        return puzzleRepository.save(puzzle);
    }

    public Puzzle editPuzzle(PuzzleDto puzzleDto, Long uid) throws CannotEditException, NotFoundException {
        Exp exp = expRepository.findByExpId(puzzleDto.getExpId());
        if(exp == null)
        {
            throw new NotFoundException("未找到题目对应实验");
        }
//
//        if(course.getTeacherId() == uid)
//        {
            Puzzle puzzle = puzzleRepository.findById(puzzleDto.getPuzzleId());
            puzzle.setCourse(exp.getCourse());
            puzzle.setCourseName(exp.getCourseName());
            puzzle.setPuzzleName(puzzleDto.getPuzzleName());
            puzzle.setExp(exp);
            puzzle.setExpName(puzzleDto.getExpName());
            puzzle.setCodeTemplate(puzzleDto.getCodeTemplate());
            puzzle.setDescription(puzzleDto.getDescription());
            puzzle.setInputDescrip(puzzleDto.getInputDescription());
            puzzle.setOutputDescrip(puzzleDto.getOutputDescription());
            puzzle.setMemLimit(puzzleDto.getMemLimit());
            puzzle.setTimeLimit(puzzleDto.getTimeLimit());
            puzzle.setPoint(puzzleDto.getPoint());
            puzzle.setDifficulty(puzzleDto.getDifficulty());
            puzzle.setTeacherName(puzzleDto.getTeacherName());
            return  puzzleRepository.save(puzzle);
//        }
//        else{
//            throw new CannotEditException("仅课程创建者能修改");
//        }


    }

    public void deletePuzzle(PuzzleDto puzzleDto, Long uid) throws CannotDeleteException, NotFoundException {
//        Exp exp = expRepository.findByExpId(puzzleDto.getExpId());
//        if(exp == null)
//        {
//            throw new NotFoundException("未找到题目对应实验");
//        }
//       // Puzzle puzzle = puzzleRepository.findById(puzzleDto.getPuzzleId());
//        Puzzle puzzle = exp.getPuzzles().stream().filter(puzzle1 -> puzzleDto.getPuzzleId().equals(puzzle1.getId())).collect(Collectors.toList()).get(0);
//        exp.getPuzzles().remove(puzzle);
        Puzzle puzzle = puzzleRepository.findById(puzzleDto.getPuzzleId());
        puzzleRepository.delete(puzzle);

       // expRepository.findByExpId(puzzleDto.getExpId()).getPuzzles().remove(puzzle);
        //puzzleRepository.delete(puzzle);
//        if(course.getTeacherId() == uid)
//        {
//            puzzleRepository.delete(puzzle);


//        }
//        else{
//            throw new CannotDeleteException("仅课程创建者能修改");
//        }

    }
}
