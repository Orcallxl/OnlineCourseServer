package errortocorrect.controller;

import errortocorrect.dto.*;
import errortocorrect.entity.Puzzle;
import errortocorrect.global.Const;
import errortocorrect.service.PuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/puzzle")
public class PuzzleController {

    @Autowired
    PuzzleService puzzleService;

    @RequestMapping(value = "/exp-puzzles", method = RequestMethod.POST)
    public AjaxResult expPuzzles(@RequestBody ExpDto expDto) {
        return AjaxResult.success(puzzleService.findExpPuzzles(expDto));
    }

    @RequestMapping(value = "/all-puzzle", method = RequestMethod.GET)
    public List<Puzzle> allPuzzle() {
        return puzzleService.allPuzzle();
    }

    @RequestMapping(value = "/user-all-puzzle", method = RequestMethod.POST)
    public AjaxResult allPuzzle(@RequestBody UserDto userDto) {
        return AjaxResult.success(puzzleService.findUserPuzzles(userDto));
    }

    @RequestMapping(value = "/puzzle-detail", method = RequestMethod.POST)
    public AjaxResult puzzleDetail(@RequestBody PuzzleDto puzzleDto) {
        return AjaxResult.success(puzzleService.findPuzzleById(puzzleDto));
    }

    @RequestMapping(value = "/create-puzzle", method = RequestMethod.POST)
    public AjaxResult createPuzzle(@RequestBody PuzzleDto puzzleDto) {
        return AjaxResult.success(puzzleService.createPuzzle(puzzleDto));
    }

    @RequestMapping(value = "/edit-puzzle", method = RequestMethod.POST)
    public AjaxResult editPuzzle(@RequestBody PuzzleDto puzzleDto, HttpServletRequest request) {
        Long uid = (Long) request.getSession().getAttribute(Const.SESSION_UID);
        try {
            return AjaxResult.success(puzzleService.editPuzzle(puzzleDto,uid));
        } catch (Exception e) {
          return AjaxResult.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/delete-puzzle", method = RequestMethod.POST)
    public AjaxResult deletePuzzle(@RequestBody PuzzleDto puzzleDto, HttpServletRequest request) {
        Long uid = (Long) request.getSession().getAttribute(Const.SESSION_UID);
        try {
            puzzleService.deletePuzzle(puzzleDto, uid);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
