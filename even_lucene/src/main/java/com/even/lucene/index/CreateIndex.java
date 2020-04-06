package com.even.lucene.index;

import com.even.lucene.ik.IKAnalyzer7x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * des:
 * 1，Lucene文档创建索引需要依靠一个IndexWriter对象，创建IndexWriter需要两个参数，一个是IndexWriterConfig对象，该对象可以设置创建索引使用哪种分词器，
 * 另一个是索引的保存路径。
 *
 * 2，IndexWriter对象的addDocument()方法用于添加文档，该方法的参数为Document对象，IndexWriter对象一次可以添加多个文档，最后调用commit()方法生成索引
 *
 * 3，本类主要是一个创建索引的例子，需求：创建3个News对象，然后把这3个News对象写入Lucene索引，IKAnalyzer对象用于指定创建索引时的分词器，作为IndexWriterConfig()方法的参数
 * IndexWriterConfig对象的setOpenMode()方法可以设置索引的打开方式，OpenMode.CREATE参数表示先清空索引再重新创建，CREATE_OR_APPEND参数表示如果索引不存在就新建，已存在则附加
 * Directory对象用于表示索引的位置，把索引路径和IndexWriterConfig对象传入IndexWriter()方法，实例化IndexWriter对象，再进行文档的操作。
 *
 * 4，文档是Lucene索引和搜索的基本单位，Document类表示文档，比文档要小的单位是域，也即字段。一个文档可以有多个域。
 * FieldType对象用于指定域的索引信息，例如是否解析、是否存储、是否保存词项频率、位移信息等。FieldType对象的setIndexOptions()方法可以设定域的索引选项。
 * 选项信息具体查看：《从Lucene到Elasticsearch全文检索实战》2.4节
 *
 * 5，关键字高亮，要想准确获取位置信息以及一些偏移量，就需要在创建索引的时候进行记录。可以使用FieldType对象提供的方法设置相对增量和位移信息。
 *
 * 6，最终生成的文件用Luke工具来查询
 *
 * 步骤：
 * 一，实例化分词器，分词器作为参数构造IndexWriterConfig对象，setOpenMode()设置打开方式，如OpenMode.CREATE。
 * 二，获取保存路径Directory directory = FSDirectory.open(Paths.get(pathStr))。
 * 三，实例化IndexWriter对象。
 * 四，创建FieldType对象，用于指定域的索引信息。如IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS：索引文档，词项频率，位移信息和偏移量都保存的意思
 * setStored()，true表示存储字段值；setTokenized()，true表示配置的分词器对字段值进行词条化；setStoreTermVectors()，true表示保存词向量；
 * setStoreTermVectorPositions()，true表示保存词项在词向量中的位移信息；setStoreTermVectorOffsets()，true表示保存词项在词向量中的偏移信息。
 * 五，新建Document对象，传入new Field(key,value,fieldType)。
 * 六，indexWriter.addDocument(document)。
 * 七，提交，indexWriter.commit(),indexWriter.close()
 * author: Even
 * create date:2019/1/15
 */
public class CreateIndex {
    public static void main(String[] args) throws IOException {
        News news1 = new News();
        news1.setId(1);
        news1.setTitle("习近平会见美国总统奥巴马，学习国外经验");
        news1.setContent("国家主席习近平9月3日在杭州西湖国宾馆会见前米出席二十国集团领导人杭州峰会的美国总统奥巴马．");
        news1.setReply(848);

        News news2 = new News();
        news2.setId(2);
        news2.setTitle("北大迎来4380名学生，农村学生700多人近年最多");
        news2.setContent("昨天，北京大学迎来4380名来自全国各地及数十个国家的本科学生。其中，农村学生共700余名，为近年最多...");
        news2.setReply(3443);

        News news3 = new News();
        news3.setId(3);
        news3.setTitle("特朗普宣誓(Donald Trump)就凭美国第45任总统");
        news3.setContent("当地时间1月20日，唐纳德·特朗普在美国国会宣誓就职，正式成为美国第45任总统。");
        news3.setReply(32);

        /*创建IK分词器*/
        Analyzer analyzer = new IKAnalyzer7x(true);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = null;
        Date startDate = new Date();
        Directory directory = null;
        Path indexdir = Paths.get("indexdir");
        directory = FSDirectory.open(indexdir);
        indexWriter = new IndexWriter(directory, indexWriterConfig);
        /*设置新闻ID索引并存储*/
        FieldType idType = new FieldType();
        idType.setIndexOptions(IndexOptions.DOCS);
        idType.setStored(true);
        /*设置标题索引文档，刻苦频率，位移信息和偏移量，存储并词*/
        FieldType titleType = new FieldType();
        titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        titleType.setStored(true);
        titleType.setTokenized(true);

        /*设置内容索引*/
        FieldType contentType = new FieldType();
        contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        contentType.setStored(true);
        contentType.setTokenized(true);
        contentType.setStoreTermVectors(true);
        contentType.setStoreTermVectorOffsets(true);
        contentType.setStoreTermVectorPayloads(true);
        contentType.setStoreTermVectorPositions(true);

        Document document1 = new Document();
        document1.add(new Field("id", String.valueOf(news1.getId()), idType));
        document1.add(new Field("title", news1.getTitle(), titleType));
        document1.add(new Field("content", news1.getContent(), contentType));
        document1.add(new IntPoint("reply", news1.getReply()));
        document1.add(new StoredField("reply_display", news1.getReply()));

        Document document2 = new Document();
        document2.add(new Field("id", String.valueOf(news2.getId()), idType));
        document2.add(new Field("title", news2.getTitle(), titleType));
        document2.add(new Field("content", news2.getContent(), contentType));
        document2.add(new IntPoint("reply", news2.getReply()));
        document2.add(new StoredField("reply_display", news2.getReply()));

        Document document3 = new Document();
        document3.add(new Field("id", String.valueOf(news3.getId()), idType));
        document3.add(new Field("title", news3.getTitle(), titleType));
        document3.add(new Field("content", news3.getContent(), contentType));
        document3.add(new IntPoint("reply", news3.getReply()));
        document3.add(new StoredField("reply_display", news3.getReply()));

        indexWriter.addDocument(document1);
        indexWriter.addDocument(document2);
        indexWriter.addDocument(document3);
        indexWriter.commit();
        indexWriter.close();
        directory.close();
        Date end = new Date();
        System.out.println("索引文档用时："+(end.getTime()-startDate.getTime())+" milliseconds");

    }
}
