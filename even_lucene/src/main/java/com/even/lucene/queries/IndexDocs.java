package com.even.lucene.queries;

import com.even.lucene.ik.IKAnalyzer7x;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Project Name: even_web
 * Des:
 * Created by Even on 2019/1/15
 */
public class IndexDocs {
    public static void main(String[] args) throws IOException {
        File news = new File("D:\\ideaWorkspace\\even_web\\even_lucene\\src\\main\\resources\\nba");
        String text = textToString(news);

        IKAnalyzer7x analyzer = new IKAnalyzer7x(true);

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        Directory directory = null;

        IndexWriter indexWriter = null;

        directory = FSDirectory.open(Paths.get("indexdir"));

        indexWriter = new IndexWriter(directory, indexWriterConfig);

        FieldType fieldType = new FieldType();

        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);

        fieldType.setStored(true);//原始字符串全部保存在索引中

        fieldType.setStoreTermVectors(true);//存储词项量


        Document document = new Document();

        document.add(new Field("content", text, fieldType));
        document.add(new Field("title", title, fieldType));

        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();

    }

    private static String title = "单核哈登疯狂砍57分 残阵火箭大胜灰熊止颓势单核哈登疯狂砍57分 残阵火箭大胜灰熊止颓势";

    /*把txt文件转成字符串*/
    public static String textToString(File file) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                result.append(System.lineSeparator()).append(str.trim());
            }
            bufferedReader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
