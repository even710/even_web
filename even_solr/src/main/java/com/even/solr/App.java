package com.even.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String serverUrl = (args != null && args.length > 0) ? args[0] : "http://localhost:8983/solr";
        String coreName = (args != null && args.length > 1) ? args[1] : "even";
        //even是我创建的内核名
        HttpSolrClient solrClient = new HttpSolrClient.Builder(serverUrl).withConnectionTimeout(10000).withSocketTimeout(60000).build();
        Info info = new Info("4", "lala", "post", "2019-01-22T15:22:00Z/HOUR", "cn", "16542314560", 154354,
                "关于Solr的一些基础知识，我已写到CSDN博客中，有兴趣可以来了解一下。地址： https://blog.csdn.net/weixin_37581297");
//        SolrInputDocument doc = new SolrInputDocument();
//        doc.addField("id", "5");
//        doc.addField("screen_name", "elina");
//        doc.addField("type", "post");
//        doc.addField("timestamp", "2019-01-22T15:22:00Z/HOUR");
//        doc.addField("lang", "cn");
//        doc.addField("user_id", "1534512541");
//        doc.addField("favorites_count", "6513515321");
//        doc.addField("text", "关于Solr的一些基础知识，我已写到CSDN博客中，有兴趣可以来了解一下。地址： https://blog.csdn.net/weixin_37581297");
        try {
            solrClient.addBean(coreName, info);
            solrClient.commit(coreName, true, true);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("q", "*:*");
        queryParamMap.put("fl", "id,screen_name");
        queryParamMap.put("sort", "id asc");
        try {
            queryIndex(solrClient, queryParamMap, coreName);
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
    }

    public static class Info {
        @Field
        private String id;
        @Field
        private String screen_name;
        @Field
        private String type;
        @Field
        private String timestamp;
        @Field
        private String lang;
        @Field
        private String user_id;
        @Field
        private int favorites_count;
        @Field
        private String text;

        public Info(String id, String screen_name, String type, String timestamp, String lang, String user_id, int favorites_count, String text) {
            this.id = id;
            this.screen_name = screen_name;
            this.type = type;
            this.timestamp = timestamp;
            this.lang = lang;
            this.user_id = user_id;
            this.favorites_count = favorites_count;
            this.text = text;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getFavorites_count() {
            return favorites_count;
        }

        public void setFavorites_count(int favorites_count) {
            this.favorites_count = favorites_count;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static void queryIndex(SolrClient solrClient, Map<String, String> queryParamMap, String coreName) throws IOException, SolrServerException {
//        MapSolrParams solrParams = new MapSolrParams(queryParamMap);
        SolrQuery solrParams = new SolrQuery("*:*");
        solrParams.addField("id");
        solrParams.addField("screen_name");
        solrParams.addSort("id", SolrQuery.ORDER.asc);
        solrParams.setRows(10);//返回的数量

        QueryResponse response = solrClient.query(coreName, solrParams);
        List<Info> beans = response.getBeans(Info.class);

        SolrDocumentList results = response.getResults();
        System.out.println("查询到" + results.getNumFound() + "个文档！");
        for (SolrDocument document : results) {
            String id = (String) document.getFirstValue("id");
            String screen_name = (String) document.getFirstValue("screen_name");
            System.out.println("id:" + id + ",name:" + screen_name);
        }

    }
}
