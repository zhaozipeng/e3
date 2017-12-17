package com.e3.web.inteceptor;

import com.e3.service.user.pojo.TbUser;
import com.e3.service.user.service.UserService;
import com.e3.utils.E3Result;
import com.e3.utils.TextUtils;
import com.e3.web.utils.CookieUtils;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/12/5.
 */
public class CartInteceptor implements HandlerInterceptor{
   @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取cookie中的token
        String tt_token= CookieUtils.getCookieValue(httpServletRequest,"TT_TOKEN");
        if(TextUtils.isEmpty(tt_token)){
            //
            return true;

        }
        //根据token到redis中的用户信息

        E3Result e3Result=userService.selectUserByToken(tt_token);
        if(e3Result.getStatus().equals("200")){
            return true;

        }
       TbUser user = (TbUser) e3Result.getData();
        //将用户信息放入request中
        httpServletRequest.setAttribute("user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
