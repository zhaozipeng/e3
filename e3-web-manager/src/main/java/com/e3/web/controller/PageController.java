package com.e3.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class PageController {
    @RequestMapping("/")
    public String index() {

        return "index";
    }
    //item-add
    @RequestMapping("{path}")
    public String index(@PathVariable  String path) {

        return  path;
    }


}
