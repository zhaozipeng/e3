package com.e3.web.controller;

import com.e3.service.user.pojo.TbUser;
import com.e3.service.user.service.UserService;
import com.e3.utils.E3Result;
import com.e3.utils.JsonUtils;
import com.e3.utils.TextUtils;
import com.e3.web.utils.CookieUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/12/2.
 */
@RestController
public class UserController {
@Resource
    UserService userService;
   @RequestMapping("/user/check/{param}/{type}")

   public E3Result checkData(@PathVariable String param, @PathVariable Integer type){
    return userService.checkData(param,type);
   }
   @RequestMapping()
    public E3Result regist(TbUser tbUser){
    return userService.regist(tbUser);
   }
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public E3Result login(String username, String password, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
        E3Result e3Result = userService.login(username, password);
     /* Cookie cookie=new Cookie("USER:TT_TOKEN", (String) e3Result.getData());
      cookie.setMaxAge(30*24*60*60);
      cookie.setPath(request.getContextPath());
      response.addCookie(cookie);*/
        //从服务器获取浏览器传递来的cookie
        // cookie是客户端技术，保存到浏览器中，以字符串的形式进行保存，不能互相访问
        CookieUtils.setCookie(request, response, "TT_TOKEN", (String) e3Result.getData());

        return e3Result;
    }

    @RequestMapping("/user/token/{token}")
    public Object selectUserByToken(@PathVariable String token, String callback) {
        if (TextUtils.isEmpty(callback)) {
            return userService.selectUserByToken(token);
        }
        E3Result e3Result = userService.selectUserByToken(token);
        String str = callback + "(" + JsonUtils.objectToJson(e3Result) + ")";
        //根据token到redis中查询用户信息
        return str;
    }
    //退出

    @RequestMapping("/user/logout/{token}")
    public E3Result logout(@PathVariable String token) {
        return userService.logout(token);
    }
}
