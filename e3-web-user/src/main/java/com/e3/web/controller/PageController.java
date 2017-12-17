package com.e3.web.controller;

import com.e3.service.user.service.UserService;
import com.e3.utils.TextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/12/2.
 */
@Controller
public class PageController {
    @Resource
    UserService userService;
    @RequestMapping("/page/register")
    public String regist(){



        return "regist";
    }
    @RequestMapping("/page/login")
    public String login(String redirect, Model model){
        if(!TextUtils.isEmpty(redirect)){
            model.addAttribute("redirect",redirect);
        }

        return "login";
    }
}
