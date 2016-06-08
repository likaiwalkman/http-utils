package com.cspider.crawler.hanatour.main.rt;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

/**
 * test case for PriceFlightMapperProcessor
 * Created by kai_li on 2016/6/7.
 */
public class PriceFlightMapperProcessorTest {

    @Test
    public void map() throws Exception {
        SAXReader reader = new SAXReader();
        InputStream contentStream = FlightsExtractor.class.getResourceAsStream("/dataRt.xml");
        Document doc = reader.read(contentStream);
        Node root = doc.selectSingleNode("/ActionMessage");

        Map<String, String> result = PriceFlightMapperProcessor.map(root);
        String expectedResult = "{CX:5319,CX:5620~CX:5627,CX:5318=766100.0, CX:5319,CX:5620~CX:5621,CX:5318=766100.0, CX:5319,CX:5620~CX:5637,CX:5318=766100.0, CX:5319,CX:5636~CX:5627,CX:5318=766100.0, CX:5319,CX:5636~CX:5621,CX:5318=766100.0, CX:5319,CX:5636~CX:5637,CX:5318=766100.0, CX:5319,CX:5622~CX:5627,CX:5318=766100.0, CX:5319,CX:5622~CX:5621,CX:5318=766100.0, CX:5319,CX:5622~CX:5637,CX:5318=766100.0}";
        Assert.assertEquals(expectedResult, result.toString());
    }

}