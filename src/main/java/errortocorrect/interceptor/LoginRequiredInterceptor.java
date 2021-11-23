package errortocorrect.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import errortocorrect.dto.AjaxResult;
import errortocorrect.global.Const;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginRequiredInterceptor implements HandlerInterceptor {
    private static final Logger logger = LogManager.getLogger("HandlerInterceptor");
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Long uid = (Long) session.getAttribute(Const.SESSION_UID);
        if(uid == null)
        {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(AjaxResult.error("请先登录")));
            logger.warn("login first");
            return false;
        }
        return true;
    }
}
