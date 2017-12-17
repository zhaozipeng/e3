//package com.e3;
//
//import com.mysql.jdbc.UpdatableResultSet;
//import org.apache.solr.client.solrj.SolrServer;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.client.solrj.response.UpdateResponse;
//import org.apache.solr.common.SolrInputDocument;
//import org.apache.solr.common.util.NamedList;
//import org.junit.Test;
//
//import java.io.IOException;
//
///**
// * Created by Administrator on 2017/11/28.
// */
//public class SolrTest {
//    @Test
//    public void test1() throws IOException, SolrServerException {
//        //链接solr服务
//        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
//        //添加数据
//        //创建一个文档对象
//        SolrInputDocument solrInputDocument=new SolrInputDocument();
//        //在文档中添加字段
//        //2.2往文档对象中添加字段
//       solrInputDocument.addField("id", 10001);
//       solrInputDocument.addField("product_name", "product_name苹果笔记本");
//        solrServer.add(solrInputDocument);
//         UpdateResponse commit = solrServer.commit();
//        NamedList<Object> response = commit.getResponse();
//        for (Object o : response
//                ) {
//            System.out.println(o);
//        }
//    }
//}
