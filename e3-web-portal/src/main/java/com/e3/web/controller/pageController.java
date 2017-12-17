package com.e3.web.controller;

import com.e3.service.content.pojo.TbContent;
import com.e3.service.content.service.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */
@Controller
public class pageController {
@Resource
    ContentService contentService;
    //跳转到主界面
    @RequestMapping("/")

    public String index(Model model){

    List<TbContent>contents= contentService.selectContentList(89);
        model.addAttribute("ad1List",contents);
        return "index";
    }
}
