package com.e3.web.controller;

import com.e3.service.goods.service.SysUserService;
import com.e3.utils.E3Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
public class SysUserController {

    @Resource
    SysUserService sysUserService;
    @RequestMapping("/doLogin")
    public String doLogin(String usercode, String password, String randomcode, HttpSession session, Model model) {
        //1如果说验证码正确，继续执行，否则返回登录界面
        String validateCode = (String) session.getAttribute("validateCode");
        if (!validateCode.equalsIgnoreCase(randomcode)) {
            model.addAttribute("message", "验证码错误");
            return "login";
        }
        //2-调用service验证是否登录成功
        E3Result result = sysUserService.checkUser(usercode, password);
        //封装一个对象，有返回的状态码status，有消息内容 msg，如果成功返回一个对象  data
        //2.1  用户名不存在
//            status=400  msg ==用户名不存在
        //2.2 用户名存在但是密码不对 用户名或者密码错误
        // status=400  msg ==用户名或者密码错误
        if (result.getStatus().equals("400")) {
            model.addAttribute("message", result
                    .getMsg());
            return "login";
        }
        session.setAttribute("activeUser",result.getData());
        //3 登录成功，返回到index  User
        return "index";
    }
}
