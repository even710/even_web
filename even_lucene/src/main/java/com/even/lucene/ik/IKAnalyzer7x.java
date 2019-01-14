package com.even.lucene.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * des:
 * author: Even
 * create date:2019/1/14
 */
public class IKAnalyzer7x extends Analyzer {

    private boolean useSmart;

    public boolean isUseSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public IKAnalyzer7x() {
        this(false);
    }

    public IKAnalyzer7x(boolean useSmart) {
        super();
        this.useSmart = useSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {

        Tokenizer tokenizer = new IKTokenizer7x(this.isUseSmart());
        return new TokenStreamComponents(tokenizer);
    }

    private static String string = "厉害了我的哥！中国环保部门即将发布治理北京雾霾的方法！";

    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new IKAnalyzer7x(true);
        StringReader stringReader = new StringReader(string);
        TokenStream tokenStream = analyzer.tokenStream(string, stringReader);
        tokenStream.reset();
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        System.out.println("分词结果：");
        while (tokenStream.incrementToken())
            System.out.print(attribute.toString() + "|");
        System.out.println();
        analyzer.close();
    }
}
