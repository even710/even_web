package com.even.lucene.services;

import com.even.lucene.ik.IKAnalyzer7x;
import com.even.tika.TikaParsePdf;
import com.ssm.api.model.FileModel;
import com.ssm.api.service.CreateIndexService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Project Name: even_web
 * Des:
 * Created by Even on 2019/1/16
 */
public class CreateIndexServiceImpl implements CreateIndexService {
    public static List<FileModel> getFileModels(String dir) throws TikaException, IOException, SAXException {
        List<FileModel> fileModels = new ArrayList<>();
        File dirs = new File(dir);
        if (!dirs.exists()) {
            System.out.println("没有找到文件夹，请检查！");
            return null;
        }
        File[] files = dirs.listFiles();

        TikaParsePdf tikaParsePdf = new TikaParsePdf();
        if (files != null) {
            for (File file : files) {
                FileModel fileModel = new FileModel(file.getName(), tikaParsePdf.parseFile(file));
                fileModels.add(fileModel);
            }
        }
        return fileModels;
    }

    public static void main(String[] args) throws TikaException, IOException, SAXException {
        CreateIndexServiceImpl createIndexService = new CreateIndexServiceImpl();
        createIndexService.searchIndex("Dubbo");
//        createIndex();
    }

    public static void createIndex() throws TikaException, IOException, SAXException {
        Date start = new Date();
        Analyzer analyzer = new IKAnalyzer7x(true);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Path path = Paths.get("indexdir");
        if (!Files.isReadable(path)) {
            System.out.println("不存在或者不可读，请检查！");
            System.exit(1);
        }
        Directory directory = FSDirectory.open(path);

        IndexWriter indexWriter = new IndexWriter(directory, config);

        /*设置FieldType*/
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        fieldType.setStored(true);
        fieldType.setTokenized(true);
        fieldType.setStoreTermVectors(true);
        fieldType.setStoreTermVectorPositions(true);
        fieldType.setStoreTermVectorOffsets(true);
        List<FileModel> fileModels = getFileModels
                ("D:\\ideaWorkspace\\even_web\\even_search\\src\\main\\resources\\files");
        /*遍历生成index*/
        if (fileModels != null) {
            for (FileModel fileModel : fileModels) {
                Document doc = new Document();
                doc.add(new Field("title", fileModel.getTitle(), fieldType));
                doc.add(new Field("content", fileModel.getContent(), fieldType));
                indexWriter.addDocument(doc);
            }
        }
        indexWriter.commit();
        indexWriter.close();
        directory.close();
        Date end = new Date();

        System.out.println("生成索引文档完成，共耗时：" + (end.getTime() - start.getTime()) + "毫秒.");
    }

    @Override
    public List<FileModel> searchIndex(String key) throws IOException {
        /*一，获取索引流*/
        Path path = Paths.get("indexdir");
        Directory directory = FSDirectory.open(path);
        DirectoryReader reader = DirectoryReader.open(directory);
        /*二，创建IndexSearcher对象*/
        IndexSearcher searcher = new IndexSearcher(reader);
        /*三，构造分词器*/
        Analyzer analyzer = new IKAnalyzer7x();
        /*四，构造查询解析器*/
        QueryParser queryParser = new QueryParser("content", analyzer);
        /*五，获取query对象*/
        Query query = null;
        try {
            query = queryParser.parse(key);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("解析失败");
        }
        /*六，获取TopDocs类型文档集合*/
        TopDocs topDocs = searcher.search(query, 10);

        List<FileModel> fileModels = new ArrayList<>();
        int i = 0;
        for (ScoreDoc sd : topDocs.scoreDocs) {
            System.out.println("第" + i + 1 + "条记录结果");
            Document doc = searcher.doc(sd.doc);
            fileModels.add(new FileModel(doc.get("title"), doc.get("content")));
            System.out.println();
            System.out.println("title:" + doc.get("title"));
            System.out.println("content:" + doc.get("content"));
            System.out.println("文档评分：" + sd.score);
        }
        return fileModels;
    }
}
