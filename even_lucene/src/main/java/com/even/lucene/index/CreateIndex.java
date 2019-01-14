package com.even.lucene.index;

/**
 * des:
 * author: Even
 * create date:2019/1/15
 */
public class CreateIndex {
    public static void main(String[] args) {
        News news1 = new News();
        news1.setId(1);
        news1.setTitle("习近平会见美国总统奥巴马，学习国外经验");
        news1.setContent("国家主席习近平9月3日在杭州西湖国宾馆会见前米出席二十国集团领导人杭州峰会的美国总统奥巴马．");
        news1.setReply(848);
    }
}
