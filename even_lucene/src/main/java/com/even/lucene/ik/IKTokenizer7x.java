package com.even.lucene.ik;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;

/**
 * des:
 * author: Even
 * create date:2019/1/14
 */
public class IKTokenizer7x extends Tokenizer {
    /*分词器的实现*/
    private IKSegmenter _IKSegmenter;
    /*词元文本属性*/
    private final CharTermAttribute charTermAttribute;
    /*词元位移属性*/
    private final OffsetAttribute offsetAttribute;
    /*词元类型属性*/
    private final TypeAttribute typeAttribute;
    /*最后一个词元的结束位置*/
    private int endPosition;

    public IKTokenizer7x(boolean useSmart) {
        this.charTermAttribute = addAttribute(CharTermAttribute.class);
        this.offsetAttribute = addAttribute(OffsetAttribute.class);
        this.typeAttribute = addAttribute(TypeAttribute.class);
        _IKSegmenter = new IKSegmenter(input, useSmart);
    }

    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();
        Lexeme lexeme = _IKSegmenter.next();
        if (null != lexeme) {
            charTermAttribute.append(lexeme.getLexemeText());
            charTermAttribute.setLength(lexeme.getLength());
            offsetAttribute.setOffset(lexeme.getBeginPosition(), lexeme.getEndPosition());
            endPosition = lexeme.getEndPosition();
            typeAttribute.setType(lexeme.getLexemeText());
            return true;
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
        int finalOffset = correctOffset(this.endPosition);
        offsetAttribute.setOffset(finalOffset, finalOffset);
    }
}
