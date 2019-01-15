package com.even.lucene.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * Project Name: Web_App
 * Des:使用IK分词需要修改IKAnalyzer，实例化IKAnalyzer后即可使用
 * Created by Even on 2019/1/14
 */
public class IKAnalyzer7x extends Analyzer {
    private boolean userSmart;

    public boolean isUserSmart() {
        return userSmart;
    }

    public void setUserSmart(boolean userSmart) {
        this.userSmart = userSmart;
    }

    public IKAnalyzer7x() {
        this(false);//默认细粒度切分算法
    }

    public IKAnalyzer7x(boolean userSmart) {
        super();
        this.userSmart = userSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer _IKTokenizer = new IKTokenizer7x(this.isUserSmart());
        return new TokenStreamComponents(_IKTokenizer);
    }

    private static String string = "厉害了我的哥！中国环保部门即将发布治理北京雾霾的方法!";

    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new IKAnalyzer7x(true);
        StringReader stringReader = new StringReader(string);
        TokenStream tokenStream = analyzer.tokenStream(string, stringReader);
        tokenStream.reset();
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken())
            System.out.print(attribute.toString() + "|");
        System.out.println("");
        analyzer.close();
    }
}
