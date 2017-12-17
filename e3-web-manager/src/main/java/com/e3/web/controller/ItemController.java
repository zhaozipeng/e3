package com.e3.web.controller;

import com.e3.service.goods.pojo.TbItem;
import com.e3.service.goods.pojo.TbItemCat;
import com.e3.service.goods.service.ItemService;
import com.e3.utils.E3Result;
import com.e3.utils.EasyUIDataResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ItemController {

    @Resource
    ItemService itemService;

    @RequestMapping("/selectItemById/{id}")
    @ResponseBody
    public TbItem selectItemById(@PathVariable Long id) {

        return itemService.selectTbItemById(id);
    }

    //界面分页查询商品条目
    //http://localhost:8080/item/list?page=1&rows=30
    //total
    //rows{
    //[],[],[]
    //}
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataResult selectItemList(Integer page,Integer rows){

        return itemService.selectTbItem(page,rows);
    }

    //selectItemCat
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List selectItemCatList(@RequestParam(defaultValue = "0") Long id){
       //从数据库中查出父类节点查询出类别信息
        List<TbItemCat>catList=itemService.selectItemCatList(id);
        List resultList=new ArrayList();
        for (TbItemCat t:catList
             ) {
            Map map=new HashMap();
            map.put("id",t.getId()+"");
            map.put("text",t.getName());
            //如果父节点就先关闭   叶子节点就打开
            map.put("state",t.getIsParent()?"closed":"open");
            resultList.add(map);
        }
        return resultList;
    }

    @RequestMapping("/item/save")
    @ResponseBody

    public E3Result saveItem(TbItem tbItem, String desc,String itemParams) {
        return itemService.saveItem(tbItem, desc,itemParams);
    }
    //分页查询类模板信息

    @RequestMapping("item/param/list")
    @ResponseBody
    public EasyUIDataResult selectParamList(Integer page,Integer rows){
        return itemService.selectParamList(page,rows);

    }

    //按id查询分类模板
    @RequestMapping("/item/param/query/itemcatid/{id}")
    @ResponseBody
    public E3Result selectParamByCatId( @PathVariable Long id ){
        return itemService.selectParamByCatId(id);
    }

    //添加商品模板
    @RequestMapping("/item/param/save/{id}")
    @ResponseBody
    public E3Result saveParam(@PathVariable Long id, String paramData){
        return itemService.saveParam(id,paramData);
    }

        }
