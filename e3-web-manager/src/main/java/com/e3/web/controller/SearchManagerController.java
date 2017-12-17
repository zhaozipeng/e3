package com.e3.web.controller;

 import com.e3.service.search.service.SearchService;
 import com.e3.utils.E3Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
/**
 * Created by Administrator on 2017/11/28.
 */
@Controller
public class SearchManagerController {
    @Resource
    SearchService searchService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result importSolr() {
        return searchService.addToSolr();
    }
}
