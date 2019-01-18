package com.even.lucene.queries;

import com.even.lucene.ik.IKAnalyzer7x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Project Name: even_web
 * Des: 步骤：
 * 一，遍历文件，获取指定字段的词频：Terms terms = reader.getTermVector(doc,"content")
 * 二，遍历词项，获取词项频率termsEnum.totoalTermFreq()
 * 三，对词频进行排序
 * Created by Even on 2019/1/15
 */
public class GetTopTerms {
    public static void main(String[] args) throws IOException, ParseException {
        Directory directory = FSDirectory.open(Paths.get("indexdir"));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new IKAnalyzer7x(true);
        QueryParser queryParser = new QueryParser("content", analyzer);
        Query query = queryParser.parse("哈登");
//        Query termQuery = new TermQuery(new Term("content", "哈登"));
        TopDocs search = searcher.search(query, 10);
        for (ScoreDoc sd : search.scoreDocs) {
          /*因为只索引一个文档，所以DocId为0？？？*/
        /*通过getTermVector获取content字段的词频*/
            Terms terms = reader.getTermVector(sd.doc, "content");

        /*遍历词项*/
            TermsEnum termsEnum = terms.iterator();
            Map<String, Integer> map = new HashMap<>();
            BytesRef thisTerm;
            while ((thisTerm = termsEnum.next()) != null) {
                String termText = thisTerm.utf8ToString();
            /*totalTermFreq方法获取词项频率*/
                map.put(termText, (int) termsEnum.totalTermFreq());
            }
        /*按value排序*/
            List<Map.Entry<String, Integer>> sortedMap = new ArrayList<>(map.entrySet());
            sortedMap.sort((o1, o2) -> {
            /*大的往前排，小的往后排*/
                return o2.getValue() - o1.getValue();
            });
            getTopN(sortedMap, 10);
        }
    }

    public static void getTopN(List<Map.Entry<String, Integer>> sortMap, int N) {
        for (int i = 0; i < N; i++) {
            System.out.println(sortMap.get(i).getKey() + ":" + sortMap.get(i).getValue());
        }
        System.out.println("--------------------------------");
    }
}
