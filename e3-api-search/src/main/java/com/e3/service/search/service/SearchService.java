package com.e3.service.search.service;

import com.e3.service.search.pojo.SearchResult;
import com.e3.utils.E3Result;

import java.io.IOException;


public interface SearchService {
    //从数据库中查询出商品信息，平添加到索引库中
    public E3Result addToSolr();
    SearchResult selectItems(String keyword, Integer page);
    public void updateSolr(Long id);
}
