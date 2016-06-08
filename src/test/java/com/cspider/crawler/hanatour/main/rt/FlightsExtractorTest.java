package com.cspider.crawler.hanatour.main.rt;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

/**
 * test case for FlightsExtractor
 * Created by kai_li on 2016/6/7.
 */
public class FlightsExtractorTest {

    @Test
    public void extract() throws Exception {
        SAXReader reader = new SAXReader();
        InputStream contentStream = FlightsExtractor.class.getResourceAsStream("/dataRt.xml");
        Document doc = reader.read(contentStream);
        Node root = doc.selectSingleNode("/ActionMessage");
        Map<String, Map<String, String>> result = FlightsExtractor.extract(root);
        String expectedResult = "{goFlightsGroup={1=CX:5319,CX:5620, 2=CX:5319,CX:5636, 3=CX:5319,CX:5622}, backFlightsGroup={1=CX:5627,CX:5318, 2=CX:5621,CX:5318, 3=CX:5637,CX:5318}}";
        System.out.println(result);
        Assert.assertEquals(result.toString(), expectedResult);
    }

}