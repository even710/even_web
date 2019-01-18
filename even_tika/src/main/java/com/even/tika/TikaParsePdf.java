package com.even.tika;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 提取pdf文件内容
 * 步骤：
 * 一，获取文件流FileInputStream，可使用TikaInputStream类支持随机访问读取文件，能更高效地处理各种类型的文件
 * 二，新建内容处理器对象，用来提取文件的文本内容
 * 三，创建Metadata对象用于获取文件属性
 * 四，创建一个解析上下文件的ParseContext对象
 * 五，实例化一个PDF解析器对象，然后调用PDF解析器对象的parse方法，传入上面的4个参数。
 * 六，解析完成后可通过内容处理器对象的toString()方法输出文件内容。
 */
public class TikaParsePdf {
    public static void main(String[] args) throws IOException, TikaException, SAXException {
        parse();
    }

    /**
     * 解析单种类型的文档
     *
     * @throws TikaException
     * @throws SAXException
     * @throws IOException
     */
    public static void parsePDF() throws TikaException, SAXException, IOException {
        /*1,文件路径*/
        String filePath = "D:\\ideaWorkspace\\even_web\\even_tika\\src\\main\\resources\\files\\解密搜索.pdf";
        /*2,读入文件*/
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
//        TikaInputStream tikaInputStream = TikaInputStream.get(file);
        /*3,内容处理器对象*/
        BodyContentHandler handler = new BodyContentHandler();
        /*4,创建元数据对象*/
        Metadata metadata = new Metadata();
        /*5,创建内容解析器对象*/
        ParseContext parseContext = new ParseContext();

        /*6,实例化PDFParser对象*/
        PDFParser parser = new PDFParser();
//        OOXMLParser parser = new OOXMLParser();//MS Office文档
//        TXTParser parser = new TXTParser();//文本文件
//        HtmlParser parser = new HtmlParser();//HTML文件
//        XMLParser parser = new XMLParser();//XML文件
//        ClassParser parser = new ClassParser();//Class文件

        /*7,调用parse方法解析文件*/
        parser.parse(fileInputStream, handler, metadata, parseContext);

        /*8,遍历元数据内容*/
        System.out.println("文件属性信息；");
        for (String name : metadata.names()) {
            System.out.println(name + ":" + metadata.get(name));
        }
        System.out.println("pdf文件中的内容：");
        System.out.println(handler.toString().trim());
    }

    /**
     * 动态解析文件，提取文件内容
     */
    public static void parse() throws IOException, TikaException {
        /*1,Tika使用自身的类型检测机制来检测文件类型*/
        /*2，通过解析器库文档类型自己选择合适的解析器*/
        /*3，解析完成后可进行文档内容提取，元数据提取*/
        Tika tika = new Tika();
        File fileDir = new File("D:\\ideaWorkspace\\even_web\\even_tika\\src\\main\\resources\\files");
        if (!fileDir.exists()) {
            System.out.println("文件夹不存在，请检查！");
            System.exit(0);
        }
        File[] files = fileDir.listFiles();
        String fileContent;
        for (File f : files) {
            fileContent = tika.parseToString(f);
            System.out.println("Extracted Content：" + fileContent);
        }
    }

    /**
     * 使用AutoDetectParser动态解析文件
     *
     * @throws IOException
     * @throws TikaException
     * @throws SAXException
     */
    public static void parseAuto() throws IOException, TikaException, SAXException {
        File fileDir = new File("D:\\ideaWorkspace\\even_web\\even_tika\\src\\main\\resources\\files");
        if (!fileDir.exists()) {
            System.out.println("文件夹不存在，请检查！");
            System.exit(0);
        }
        File[] files = fileDir.listFiles();

        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream fileInputStream = null;

        /*AutoDetectParser是CompositeParser的子类，能够自动检测文件类型，并使用相应的方法把接收到的文档自动发送给最接近的解析器类*/
        Parser parser = new AutoDetectParser();

        ParseContext parseContext = new ParseContext();

        for (File f : files != null ? files : new File[0]) {
            fileInputStream = new FileInputStream(f);
            parser.parse(fileInputStream, handler, metadata, parseContext);
            System.out.println(f.getName() + ":\n" + handler.toString());
        }

    }

    public String parseFile(File file) throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream fileInputStream = new FileInputStream(file);
        Parser parser = new AutoDetectParser();
        ParseContext parseContext = new ParseContext();
        parser.parse(fileInputStream, handler, metadata, parseContext);
        return handler.toString().trim();
    }
}
