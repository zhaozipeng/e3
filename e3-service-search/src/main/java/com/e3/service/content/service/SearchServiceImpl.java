package com.e3.service.content.service;


 import com.e3.service.goods.dao.SearchDao;
import com.e3.service.goods.mapper.TbItemCatMapper;
import com.e3.service.search.pojo.SearchPojo;
import com.e3.service.search.pojo.SearchResult;

 import com.e3.service.search.service.SearchService;
 import com.e3.utils.E3Result;
import com.e3.utils.TextUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
 import org.springframework.stereotype.Service;

 import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    TbItemCatMapper tbItemCatMapper;
    @Resource
    SolrServer httpSolrServer;

    @Resource
    SearchDao searchDao;

    private Integer pageSize = 60;

    @Override
    public E3Result addToSolr() {
        //1-查询数据库中所有的商品信息
        List<SearchPojo> searchPojos = tbItemCatMapper.selectAllItems();
        for (SearchPojo searchPojo : searchPojos
                ) {
            //添加到solr的索引库中
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", searchPojo.getId());
            document.addField("item_title", searchPojo.getTitle());
            document.addField("item_sell_point", searchPojo.getSell_point());
            document.addField("item_price", searchPojo.getPrice());
            document.addField("item_image", searchPojo.getImage());
            document.addField("item_category_name", searchPojo.getCatName());
            document.addField("item_desc", searchPojo.getItem_desc());
            try {
                httpSolrServer.add(document);
                httpSolrServer.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return E3Result.ok(null);
    }

    @Override
    public SearchResult selectItems(String keyword, Integer page) {
        //1--封装solrQuery
        SolrQuery solrQuery = new SolrQuery();
        //1.1 设置关键词的查询条件
        if (TextUtils.isEmpty(keyword)) {
            solrQuery.setQuery("*:*");
        } else {
            solrQuery.setQuery(keyword);
        }
        //1.2设置查询域--copyField
        solrQuery.set("df", "item_keywords");

        //1.111 先执行查询，查询出总条目个数
        int totalCount = searchDao.selectTotalCount(solrQuery);
        int totalPage=0;
        if (totalCount % pageSize == 0) {
            totalPage =totalCount/pageSize;
        } else {
            totalPage =totalCount/pageSize+1;
        }
        //1.3 设置页码查询条件
        if (page <= 0) {
            page = 1;
        }else if(page>=totalPage){
            page=totalPage;
        }
        //1.4 计算出条目起始值
        Integer start = (page - 1) * pageSize;
        //1.5 设置开始
        solrQuery.setStart(start);
        //1.6 设置当前每页展示个数
        solrQuery.setRows(pageSize);
        //1.7 设置高亮显示条件
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        solrQuery.addHighlightField("item_title");
        //2 执行查询
        SearchResult searchResult = searchDao.selectItems(solrQuery);
        //3 计算出总页码数
        searchResult.setTotalPage(totalPage);
        //判断当前页码是否超过最大值
        searchResult.setPage(page);
        searchResult.setQuery(keyword);
        return searchResult;
    }

    @Override
    public void updateSolr(Long id) {
    //查询商品
        SearchPojo searchPojo = tbItemCatMapper.selectItemById(id);
        //添加到索引库中
        //添加到solr的索引库中
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", searchPojo.getId());
        document.addField("item_title", searchPojo.getTitle());
        document.addField("item_sell_point", searchPojo.getSell_point());
        document.addField("item_price", searchPojo.getPrice());
        document.addField("item_image", searchPojo.getImage());
        document.addField("item_category_name", searchPojo.getCatName());
        document.addField("item_desc", searchPojo.getItem_desc());
        try {
            httpSolrServer.add(document);
            httpSolrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
