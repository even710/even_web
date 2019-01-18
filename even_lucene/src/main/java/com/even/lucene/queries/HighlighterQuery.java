package com.even.lucene.queries;

import com.even.lucene.ik.IKAnalyzer7x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Project Name: even_web
 * Des: 本类实现查询高亮功能
 * 步骤：
 * 一，实例化IndexSearcher，用于搜索文档操作
 * 二，实例化查询解析器QueryParser，用于生成Query对象
 * 三，匹配关键词并打分QueryScorer scorer = new QueryScorer(query,field)
 * 四，定制高亮标签：SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style=\"color:red;\">","</span>"")
 * ，第一个参数是前标签，第一个是结束标签
 * 五，遍历结果文件，获取高亮的片段:
 *  TokenStream tokenStream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), sd.doc, field, analyzer);
    Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
    highlighter.setTextFragmenter(fragmenter);
    String str = highlighter.getBestFragment(tokenStream, doc.get(field));
 * Created by Even on 2019/1/15
 */
public class HighlighterQuery {
    public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
        String field = "content";
        Path path = Paths.get("indexdir");
        Directory dir = FSDirectory.open(path);

        DirectoryReader reader = DirectoryReader.open(dir);
        /*1，实例化IndexSearcher，用于执行搜索文档操作*/
        IndexSearcher searcher = new IndexSearcher(reader);

        Analyzer analyzer = new IKAnalyzer7x(true);
        /*2，实例化查询解析器，用于把关键词转换成Query*/
        QueryParser parser = new QueryParser(field, analyzer);
        /*3，获取query对象，此处query可通过用户输入来确定应该使用哪种query*/
        Query query = parser.parse("北京大学");
        System.out.println("Query：" + query);

        /*4，匹配关键词并打分*/
        QueryScorer scorer = new QueryScorer(query, field);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style=\"color:red;\">", "</span>");//定制高亮标签
        /*5，实例化高亮的主体，高亮分词器*/
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);

        TopDocs tds = searcher.search(query, 10);
        /*遍历结果输出文档信息*/
        for (ScoreDoc sd : tds.scoreDocs) {
            Document doc = searcher.doc(sd.doc);
            System.out.println("id:" + doc.get("id"));
            System.out.println("title:" + doc.get("title"));
            System.out.println("content:" + doc.get("content"));

            /*获取高亮的片段*/
            TokenStream tokenStream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), sd.doc, field, analyzer);
            Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
            highlighter.setTextFragmenter(fragmenter);
            String str = highlighter.getBestFragment(tokenStream, doc.get(field));
            System.out.println("高亮的片段：" + str);

        }
        reader.close();
        dir.close();

    }
}
