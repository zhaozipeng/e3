package com.e3.web.inteceptor;

 import com.e3.service.goods.pojo.ActiveUser;
 import com.e3.service.goods.pojo.SysPermission;
 import com.e3.utils.ResourcesUtil;
 import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */
public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取当前要访问的路径
        String requestURI = httpServletRequest.getRequestURI();
        //获取豁免的url

        List<String> permissions = ResourcesUtil.gekeyList("permission");
        for (String s:permissions
             ) {
            if(requestURI.indexOf(s)>=0){
                return  true;
            }
        }
        //获取用户所携带的url  可以访问
        ActiveUser activeUser = (ActiveUser) httpServletRequest.getSession().getAttribute("activeUser");
       //当前用户的权限
        List<SysPermission>permissions1=activeUser.getPermissions();
        //判断当前访问的url是否在用户权限清单中

        for (SysPermission p:permissions1
             ) {
            if(requestURI.indexOf(p.getUrl())>=0){
                return true;

            }
        }
            //拒绝访问
        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/refuse.jsp").forward(httpServletRequest,httpServletResponse);


             return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
