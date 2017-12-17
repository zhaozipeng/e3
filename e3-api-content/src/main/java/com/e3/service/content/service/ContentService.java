package com.e3.service.content.service;

import com.e3.service.content.pojo.TbContent;
import com.e3.service.content.pojo.TbContentCategory;
import com.e3.utils.E3Result;

import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */
public interface ContentService {
    public List<TbContentCategory> selectCatoryList(Long id);

    E3Result create(Long parentId, String name);


    E3Result delete(Long id);

    E3Result save(TbContent tbContent);

    List<TbContent> selectContentList(long i);
}
