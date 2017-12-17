package com.e3.web.inteceptor;

import com.e3.service.goods.pojo.ActiveUser;
import com.e3.utils.ResourcesUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zhiyuan on 2017/11/20.
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //执行之前进行拦截
        String requestURI = request.getRequestURI();
        //判断哪些是要放行的
        //login  error  resuse
        List<String> keys = ResourcesUtil.gekeyList("login");
        //在豁免名单中有,就放行
        for (String k : keys
                ) {
            if (requestURI.indexOf(k) >= 0) {
                return true;
            }
        }
        //判断是否进行了登陆
        HttpSession session = request.getSession();
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        if (activeUser != null) {
            return true;
        }
        //如果没有登陆,到登陆界面
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
        return false;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
