package cn.edu.gdmec.android.rss;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by asus on 2018/6/7.
 */

public class RssFeed_SAXParser {
    public RssFeed getFeed(String urlStr) throws ParserConfigurationException,SAXException,IOException{
        URL url = new URL(urlStr);
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();//构建SAX解析工厂
        SAXParser saxParser = saxParserFactory.newSAXParser();//解析工厂生产解析器
        XMLReader xmlReader = saxParser.getXMLReader();
        RssHandler rssHandler = new RssHandler();
        xmlReader.setContentHandler(rssHandler);
        //使用url打开流，并将流作为xmlReader解析的输入源并解析
        InputSource inputSource = new InputSource(url.openStream());
        xmlReader.parse(inputSource);
        return rssHandler.getRssFeed();
    }
}
