package com.e3.web.controller;

import com.e3.service.search.pojo.SearchResult;
import com.e3.service.search.service.SearchService;
 import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/11/28.
 */
@Controller
public class SearchController {
    @Resource
    SearchService searchService;

    @RequestMapping("/search")
    public String search(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model){
        //调用service查询一个结果
        SearchResult searchResult=searchService.selectItems(keyword,page);
        model.addAttribute("page",searchResult.getPage());
        model.addAttribute("totalPages",searchResult.getTotalPage());
        model.addAttribute("recourdCount",searchResult.getRecourdCount());
        model.addAttribute("itemList",searchResult.getItemList());
        model.addAttribute("query",searchResult.getQuery());
        return "search";
    }
}
