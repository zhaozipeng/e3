package com.e3.web.controller;


import com.e3.service.content.pojo.TbContent;
import com.e3.service.content.pojo.TbContentCategory;
import com.e3.service.content.service.ContentService;
import com.e3.utils.E3Result;
import com.e3.utils.TreeResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */
@RestController
@RequestMapping
public class ContentController {

    @Resource
    ContentService contentService;
    ///content/category/list


    @RequestMapping("/content/category/list")
    public List<TreeResult> selectCatoryList(@RequestParam(defaultValue = "0") Long id) {
        List<TreeResult> results = new ArrayList<>();
        List<TbContentCategory> tbContentCategoryList = contentService.selectCatoryList(id);
        for (TbContentCategory c : tbContentCategoryList
                ) {
            TreeResult treeResult = new TreeResult();
            treeResult.setId(c.getId());
            treeResult.setText(c.getName());
            treeResult.setState(c.getIsParent() ? "closed" : "open");
            results.add(treeResult);
        }
        return results;
    }
    //http://localhost:8080/content/category/create
    @RequestMapping("/content/category/create")
    public E3Result create(Long parentId, String name){
        return contentService.create(parentId,name);
    }
    @RequestMapping("/content/category/delete")
    public E3Result delete(Long id){
        //删除分类节点
       return contentService.delete(id);

    }
    @RequestMapping("/content/save")
    public E3Result save(TbContent tbContent){
        return contentService.save(tbContent);
    }
}