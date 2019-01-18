package com.even.lucene.queries;

import com.even.lucene.ik.IKAnalyzer7x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Project Name: even_web
 * Des:
 * 1，在用户输入关键词查询时，首先需要把关键字进行分析处理，才能提高检索效率。
 * 2，处理关键词就是构建Query对象的过程。Lucene搜索文档需要实例化一个IndexSearcher对象，IndexSearcher对象的search()方法完成搜索过程。Query对象作为search()方法的参数，
 * 会把搜索结果保存在一个TopDocs类型的文档集合中，遍历TopDocs集合输出文档信息。
 * 3，查询解析器QueryParser需要两个参数，field和analyzer，告诉QueryParser在哪个字段内查找该关键字信息以及搜索时使用什么样的分词器。设置Operator.AND是为了找到同时包含词项的文档。
 * 如，农村学生会被分成两个词项“农村”和“学生”，默认是找到“农村”或“学生”的文档，现在结果是同时包含两个词项的文档。
 * 4，QueryParser是用于搜索单个字段，而org.apache.lucene.queryparser.classic.MultiFieldQueryParser则可以查询多个字段，传入参数为String[]。
 * <p>
 * Query类型：
 * （1）TermQuery，词项搜索，最简单最常用的Query，可以在索引中搜索某一词条，词条就是一个key/value对，key是字段名，value表示字段中所包含的某个关键字。
 * （2）BooleanQuery，布尔搜索，一个组合Query，使用时把各种Query对象添加进去并标明它们之间的逻辑关系。
 * （3）RangeQuery，范围搜索，有时用户需要查找满足一定范围的文档，如某一时间段内的所有文档，回复条数在500条到1000条之前的文档等。查询时需要有“开始词条”和“结束词条”。
 * （4）PrefixQuery，前缀搜索，使用前缀来进行查找。首先定义一个词条Term，该词条包含要查找的字段名以及关键字的前缀，然后通过该词条构造一个PrefixQuery对象。
 * （5）PhraseQuery，多关键字搜索，针对用户输入多个不同的关键字，这些关键字之前要么紧密相连，要么还插有其他无关的内容，针对此种需求使用PhraseQuery。
 * （6）FuzzyQuery，模糊搜索，简单识别两个相近的词语。如因拼写错误把"Trump"拼成"Trmp"或"Tramp"，可以使用FuzzyQuery。
 * （7）WildcardQuery，通配符搜索。用？代替*
 *
 * 步骤：
 * 一，获取索引流:IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(pathStr)))
 * 二，创建IndexSearcher对象,IndexSearcher indexSearcher = new IndexSearcher(read)
 * 三，构造分词器
 * 四，构造查询解析器，以便获取query对象QueryParser parser = new QueryParser(key,analyzer)，设置解析器的操作Operator.AND
 * 五，获取Query对象:Query query = parser.parse(关键字)  //此处根据需求来实例化具体的query
 * 六，获取TopDocs类型文档集合，并遍历输出ScoreDoc。TopDocs topDocs  =indexSearcher.search(query,10)
 * 七，遍历获取doc，Document doc = indexSearcher.doc(top.doc);
 * Created by Even on 2019/1/15
 */
public class QueryParse {
    public static void main(String[] args) throws ParseException, IOException {
        String field = "title";
        Path path = Paths.get("indexdir");
        Directory dir = FSDirectory.open(path);
            /*获取索引流*/
        IndexReader directoryReader = DirectoryReader.open(dir);
            /*新建IndexSearcher对象*/
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
        Analyzer analyzer = new IKAnalyzer7x(true);
        /*查询解析器*/
        QueryParser parser = new QueryParser(field, analyzer);
        parser.setDefaultOperator(QueryParser.Operator.AND);
        Query query = parser.parse("农村学生");
        /*TermQuery*/
        Term term = new Term(field, "美国");
        Query termQuery = new TermQuery(term);

        /*BooleanQuery使用，查询title字段中包含关键词“美国”并且content字段不包含日本的文档*/
        Query query1 = new TermQuery(new Term(field, "美国"));
        Query query2 = new TermQuery(new Term("content", "日本"));
        BooleanClause bc1 = new BooleanClause(query1, BooleanClause.Occur.MUST);
        BooleanClause bc2 = new BooleanClause(query2, BooleanClause.Occur.MUST_NOT);
        BooleanQuery booleanQuery = new BooleanQuery.Builder().add(bc1).add(bc2).build();

        /*RangeQuery使用*/
        Query rangeQuery = IntPoint.newRangeQuery("reply", 500, 1000);

        /*PrefixQuery使用*/
        Query prefixQuery = new PrefixQuery(new Term(field, "学"));

        /*phraseQuery使用，使用add方法添加关键字，使用setSlop方法设定一个称之为“坡度”的变量来确定关键字之间是否允许或允许多少个无关词汇的存在*/
        PhraseQuery.Builder builder = new PhraseQuery.Builder();
        builder.add(new Term(field, "日本"), 2);
        builder.add(new Term(field, "美国"), 3);
        PhraseQuery phraseQuery = builder.build();

        /*FuzzyQuery使用*/
        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term(field, "Tramp"));

        /*WildcardQuery使用*/
        WildcardQuery wildcardQuery = new WildcardQuery(new Term(field,"学?"));

        /*关键词解析结果*/
        System.out.println("Query：" + query.toString());
        TopDocs search = indexSearcher.search(query, 10);
        for (ScoreDoc sd : search.scoreDocs) {
            Document doc = indexSearcher.doc(sd.doc);
            /*文档ID，Lucene为索引的每个文档做的标记*/
            System.out.println("DocID：" + sd.doc);
            /*文档内部的id字段*/
            System.out.println("id：" + doc.get("id"));
            System.out.println("title：" + doc.get(field));
            System.out.println("content：" + doc.get("content"));
            System.out.println("文档评分：" + sd.score);
        }
        directoryReader.close();
        dir.close();
    }

}
