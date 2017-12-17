package com.e3.web.controller;

import com.e3.service.goods.pojo.TbItem;
import com.e3.service.goods.pojo.TbItemDesc;
import com.e3.service.goods.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/11/30.
 */
@Controller
public class ItemController {
    @Resource
    ItemService itemService;
    public String item(@PathVariable Long id , Model model){
        //1--查询商品基本信息--做缓存
        TbItem item = itemService.selectItemById(id);
        model.addAttribute("item",item);
        //2--查询商品描述信息
        TbItemDesc itemDesc= itemService.selectItemDescById(id);
        model.addAttribute("itemDesc",itemDesc);


        //3--查询商品规格模板信息(ajax---json)


        return "item";
    }
}
