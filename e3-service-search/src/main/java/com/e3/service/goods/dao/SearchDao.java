package com.e3.service.goods.dao;


import com.e3.service.search.pojo.SearchPojo;
import com.e3.service.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {
    @Resource
    SolrServer solrServer;

    public SearchResult selectItems(SolrQuery solrQuery) {

        SearchResult searchResult = new SearchResult();
        List<SearchPojo> itemList = new ArrayList<>();
        try {
            //--使用solr服务执行查询
            QueryResponse queryResponse = solrServer.query(solrQuery);
            SolrDocumentList documentList = queryResponse.getResults();

            for (SolrDocument solrDocument : documentList
                    ) {
                SearchPojo searchPojo = new SearchPojo();
                String id = (String) solrDocument.get("id");
                Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
                List<String> list = highlighting.get(id).get("item_title");
                if (list != null && list.size() > 0) {
                    searchPojo.setTitle(list.get(0));
                } else {
                    searchPojo.setTitle((String) solrDocument.get("item_title"));
                }
                searchPojo.setId(id);
                searchPojo.setCatName((String) solrDocument.get("item_category_name"));
                searchPojo.setImage((String) solrDocument.get("item_image"));
                searchPojo.setItem_desc((String) solrDocument.get("item_desc"));
                searchPojo.setPrice((Long) solrDocument.get("item_price"));
                searchPojo.setSell_point((String) solrDocument.get("item_sell_point"));
                //添加到集合中
                itemList.add(searchPojo);
            }
            //将集合封装到对象
            searchResult.setItemList(itemList);
            searchResult.setRecourdCount((int) documentList.getNumFound());
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    public int selectTotalCount(SolrQuery solrQuery) {
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrServer.query(solrQuery);
            SolrDocumentList documentList = queryResponse.getResults();
            return (int) documentList.getNumFound();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
