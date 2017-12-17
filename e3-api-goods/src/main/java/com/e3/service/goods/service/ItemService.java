package com.e3.service.goods.service;

import com.e3.service.goods.pojo.TbItem;
import com.e3.service.goods.pojo.TbItemCat;
import com.e3.service.goods.pojo.TbItemDesc;
import com.e3.utils.E3Result;
import com.e3.utils.EasyUIDataResult;

import java.util.List;


public interface ItemService {

    public TbItem selectTbItemById(Long id);
   public EasyUIDataResult selectTbItem(Integer page, Integer rows);

    List<TbItemCat> selectItemCatList(Long id);



    EasyUIDataResult selectParamList(Integer page, Integer rows);

    E3Result selectParamByCatId(Long id);

    E3Result saveParam(Long id, String paramData);

    E3Result saveItem(TbItem tbItem, String desc, String itemParams);

    TbItem selectItemById(Long id);

    TbItemDesc selectItemDescById(Long id);
}
