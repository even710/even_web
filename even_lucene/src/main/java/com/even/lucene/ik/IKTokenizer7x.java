package com.even.lucene.ik;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKTokenizer;

import java.io.IOException;
import java.io.Reader;

/**
 * Project Name: Web_App
 * Des: 使用IK分词需要修改IKTokenizer
 * Created by Even on 2019/1/14
 */
public class IKTokenizer7x extends Tokenizer {

    /*IK分词器实现*/
    private IKSegmenter _IKSegmenter;
    /*词元文本属性*/
    private final CharTermAttribute attribute;
    /*词元位移属性*/
    private final OffsetAttribute offsetAttribute;
    /*词元分类属性*/
    private final TypeAttribute typeAttribute;

    /*记录最后一个词的结束位置*/
    private int endPosition;

    public IKTokenizer7x(boolean userSmart) {
        super();
        this.attribute = addAttribute(CharTermAttribute.class);
        this.offsetAttribute = addAttribute(OffsetAttribute.class);
        this.typeAttribute = addAttribute(TypeAttribute.class);
        _IKSegmenter = new IKSegmenter(input, userSmart);
    }

    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();//清除所有的词元属性
        Lexeme next = _IKSegmenter.next();
        if (null != next) {
            /*将lexeme转成attribute*/
            attribute.append(next.getLexemeText());//设置词元文本
            attribute.setLength(next.getLength());//设置词元长度
            offsetAttribute.setOffset(next.getBeginPosition(), next.getEndPosition());//设置词元位移
            /*记录分词的最后位置*/
            endPosition = next.getEndPosition();
//            typeAttribute.setType(String.valueOf(next.getLexemeType()));
            typeAttribute.setType(next.getLexemeText());//记录词元分类
            return true;//返回true表示还有下一个词元
        }
        return false;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        _IKSegmenter.reset(input);
    }

    @Override
    public void end() throws IOException {
        int i = correctOffset(this.endPosition);
        offsetAttribute.setOffset(i, i);
    }
}
