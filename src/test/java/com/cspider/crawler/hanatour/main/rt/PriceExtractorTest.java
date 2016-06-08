package com.cspider.crawler.hanatour.main.rt;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

/**
 * test case for PriceExtractor
 * Created by kai_li on 2016/6/7.
 */
public class PriceExtractorTest {

    @Test
    public void extract() throws Exception {
        SAXReader reader = new SAXReader();
        InputStream contentStream = FlightsExtractor.class.getResourceAsStream("/dataRt.xml");
        Document doc = reader.read(contentStream);
        Node root = doc.selectSingleNode("/ActionMessage");

        Map<String, Map<String, Object>> result = PriceExtractor.extract(root);
        String expectedResult = "{14={totalPrice=469100.0}, 18={totalPrice=518600.0}, 21={totalPrice=568100.0}, 22={totalPrice=667100.0}, 23={totalPrice=766100.0}}";
        Assert.assertEquals(expectedResult, result.toString());
    }

}