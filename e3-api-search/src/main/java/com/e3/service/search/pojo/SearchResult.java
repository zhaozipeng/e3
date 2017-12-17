package com.e3.service.search.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiyuan on 2017/11/28.
 */
public class SearchResult implements Serializable {

    private List<SearchPojo>  itemList;

    private Integer page;
    private Integer totalPage;
    private Integer recourdCount;
    private String query;

    public SearchResult() {
    }

    public List<SearchPojo> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchPojo> itemList) {
        this.itemList = itemList;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getRecourdCount() {
        return recourdCount;
    }

    public void setRecourdCount(Integer recourdCount) {
        this.recourdCount = recourdCount;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
