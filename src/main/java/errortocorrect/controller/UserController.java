package errortocorrect.controller;

import errortocorrect.dto.*;
import errortocorrect.entity.User;
import errortocorrect.exception.PassWordNotEqualException;
import errortocorrect.exception.UserExistException;
import errortocorrect.global.Const;
import errortocorrect.service.CourseService;
import errortocorrect.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger("ArticleController");

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AjaxResult login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        User user = userService.login(loginDto);
        if(user == null)
        {
            return AjaxResult.error("用户名或密码错误");
        }
        if(null == request.getSession())
        {
            logger.warn("session is null, username {}", loginDto.getUserName());
        }
        request.getSession().setMaxInactiveInterval(6*60*60);//登录6小时过期
        request.getSession().setAttribute(Const.SESSION_UID, user.getId());
        LoginRetDto loginRetDto = new LoginRetDto();
        loginRetDto.setUid(String.valueOf(user.getId()));
        loginRetDto.setClassName(user.getClassName());
        loginRetDto.setGradSpe(user.getGradeSpe());
        loginRetDto.setSno(String.valueOf(user.getSno()));
        if(user.getType() == 0)
        {
            loginRetDto.setType("老师");
        }
        else{
            loginRetDto.setType("学生");
        }
        loginRetDto.setUserName(user.getUserName());
        return AjaxResult.success(loginRetDto);
    }

    @RequestMapping(value = "/log_out", method = RequestMethod.POST)
    public AjaxResult logOut(String uid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute(uid);
        request.getSession().invalidate();
        //response.setContentType("application/json;charset=UTF-8");
        //response.getWriter().write(objectMapper.writeValueAsString(AjaxResult.success("退出成功")));
        //response.sendRedirect("/");
        return AjaxResult.success("退出成功");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public AjaxResult register(@RequestBody RegDto regDto) {
        try {
            userService.register(regDto);
        } catch (UserExistException e) {
            return AjaxResult.error(e.getMsg());
        } catch (PassWordNotEqualException e) {
            return AjaxResult.error(e.getMsg());
        }
        return AjaxResult.success("注册成功");
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelMap articleList() {
        ModelMap returnMap = new ModelMap();
        return returnMap;
    }

    @RequestMapping(value = "/user-list", method = RequestMethod.GET)
    public List<User> userList() {

        return userService.userList();

    }

    @RequestMapping(value = "/user-courses", method = RequestMethod.POST)
    public AjaxResult userCourse(@RequestBody UserDto user) {
        return AjaxResult.success(courseService.findUserCurrentCourses(user));
    }

    @RequestMapping(value = "/not-user-courses", method = RequestMethod.POST)
    public AjaxResult findNotUserCurrentCourses(@RequestBody UserDto user) {
        return AjaxResult.success(courseService.findNotUserCurrentCourses(user));
    }

    @RequestMapping(value = "/user-his-courses", method = RequestMethod.POST)
    public AjaxResult userHistoryCourse(@RequestBody UserDto user) {
        return AjaxResult.success(courseService.findUserHistoryCourses(user));
    }

    @RequestMapping(value = "/session_check", method = RequestMethod.GET)
    public AjaxResult sessionCheck(HttpServletRequest request) {
        request.getSession();
        return AjaxResult.success("valid");
    }
}
