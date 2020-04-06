package com.even.lucene.analyzer;

import com.even.lucene.ik.IKAnalyzer7x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * Project Name: Web_App
 * Des: lucene自带的分词器
 * Created by Even on 2019/1/14
 */
public class StAnalyzer {
    private static String strCh = "中华人民共和国，简称中国，是一个有13亿人口的国家";
    private static String strEn = "Dogs can not achieve a place, eyes can reach;";

    public static void main(String[] args) throws IOException {
        System.out.println("StandardAnalyzer对中文分词：");
        stdAnalyzer(strCh);
        System.out.println("StandardAnalyzer对英文分词：");
        stdAnalyzer(strEn);

        /*1，各类分词器比较*/
        Analyzer analyzer = null;
        analyzer = new StandardAnalyzer();
        System.out.println("标准分词：" + analyzer.getClass());
        printAnalyzer(analyzer);

        analyzer = new WhitespaceAnalyzer();
        System.out.println("空格分词：" + analyzer.getClass());
        printAnalyzer(analyzer);

        analyzer = new SimpleAnalyzer();
        System.out.println("简单分词：" + analyzer.getClass());
        printAnalyzer(analyzer);

        analyzer = new CJKAnalyzer();
        System.out.println("二分法分词：" + analyzer.getClass());
        printAnalyzer(analyzer);

        analyzer = new KeywordAnalyzer();
        System.out.println("关键词分词：" + analyzer.getClass());
        printAnalyzer(analyzer);

        analyzer = new StopAnalyzer();
        System.out.println("停用词分词：" + analyzer.getClass());
        printAnalyzer(analyzer);

        analyzer = new SmartChineseAnalyzer();
        System.out.println("中文智能分词：" + analyzer.getClass());
        printAnalyzer(analyzer);

        analyzer = new IKAnalyzer7x(true);//IK分词器的使用
        System.out.println("IK智能分词：" + analyzer.getClass());
        printAnalyzer(analyzer);

        /*2，智能中文分词器比较*/
        String string = "公路局正在治理解放大道路面积水问题。";
        compareAnalyzerforChinese(string);
        string = "IKAnalyzer 是一个开源的，基于java 语言开发\n" +
                "的轻量级的中文分词工具包。";
        compareAnalyzerforChinese(string);
    }

    public static void stdAnalyzer(String str) throws IOException {
        /*1，实例化 标准分词类对象*/
        Analyzer analyzer = new StandardAnalyzer();
        /*2，获取数据*/
        StringReader stringReader = new StringReader(str);
        /*3，处理字符串的字节流*/
        TokenStream tokenStream = analyzer.tokenStream(str, stringReader);
        tokenStream.reset();
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.print(attribute.toString() + "|");
        }
        System.out.println("");
        analyzer.close();
    }

    public static void printAnalyzer(Analyzer analyzer) throws IOException {
        /*1，获取Reader*/
        StringReader stringReader = new StringReader(strCh);
        /*2，获取tokerStream流*/
        TokenStream tokenStream = analyzer.tokenStream(strCh, stringReader);
        /*3，刷新一下流*/
        tokenStream.reset();
        /*4，获取字符词项*/
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        /*5，遍历输出*/
        while (tokenStream.incrementToken()) System.out.print(attribute.toString() + "|");
        System.out.println("");
        analyzer.close();
    }


    public static void printAnalyzer(Analyzer analyzer, String str) throws IOException {
        /*1，获取Reader*/
        StringReader stringReader = new StringReader(str);
        /*2，获取tokerStream流*/
        TokenStream tokenStream = analyzer.tokenStream(str, stringReader);
        /*3，刷新一下流*/
        tokenStream.reset();
        /*4，获取字符词项*/
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        /*5，遍历输出*/
        while (tokenStream.incrementToken())
            System.out.print(attribute.toString() + "|");
        System.out.println();
        analyzer.close();
    }

    public static void compareAnalyzerforChinese(String str) throws IOException {
        Analyzer analyzer = null;
        analyzer = new SmartChineseAnalyzer();
        System.out.println("中文智能分词：" + analyzer.getClass());
        printAnalyzer(analyzer, str);

        analyzer = new IKAnalyzer7x(true);//IK分词器的使用
        System.out.println("IK智能分词：" + analyzer.getClass());
        printAnalyzer(analyzer, str);
    }

}
