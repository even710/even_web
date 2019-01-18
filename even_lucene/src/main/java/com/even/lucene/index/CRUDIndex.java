package com.even.lucene.index;

import com.even.lucene.ik.IKAnalyzer7x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Project Name: even_web
 * Des: 索引的删除和更新操作。通过IndexWriter对象来操作。
 * Created by Even on 2019/1/15
 */
public class CRUDIndex {

    public static void main(String[] args) {
//        deleteDoc("title", "美国");
        update(new News(2, "北京大学开学迎来4380名新生", "昨天，北京大学迎来4380名来自全国各地及数十个国家的本科新生。其中，农村学生共700余名，为近年最少...", 2312));
    }

    /**
     * 根据字段和关键字来删除索引
     *
     * @param field
     * @param key
     */
    public static void deleteDoc(String field, String key) {
        Analyzer analyzer = new IKAnalyzer7x();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        Path indexdir = Paths.get("indexdir");
        Directory directory;
        try {
            directory = FSDirectory.open(indexdir);
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

            indexWriter.deleteDocuments(new Term(field, key));
            /*只有在执行commit时才会被真正执行*/
            indexWriter.commit();
            indexWriter.close();
            System.out.println("删除完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void update(News news) {
        Analyzer analyzer = new IKAnalyzer7x(true);
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        Path path = Paths.get("indexdir");
        Directory directory;
        try {
            directory = FSDirectory.open(path);
            IndexWriter indexWriter = new IndexWriter(directory, iwc);
            Document document = new Document();
            document.add(new TextField("id", String.valueOf(news.getId()), Field.Store.YES));
            document.add(new TextField("title", news.getTitle(), Field.Store.YES));
            document.add(new TextField("content", news.getContent(), Field.Store.YES));
            indexWriter.updateDocument(new Term("title", "北大"), document);
            indexWriter.commit();
            indexWriter.close();
            System.out.println("更新完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
