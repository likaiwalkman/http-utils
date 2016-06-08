package com.cspider.crawler.hanatour.main;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * test for XmlDatagramResolver
 * Created by kai_li on 2016/6/2.
 */
public class XmlDatagramResolverTest {
    @Test
    public void owSkdListGetterTest() throws ParserConfigurationException, XPathExpressionException, SAXException, IOException {

        try {
            InputStream contentStream = HttpDatagramSenderUtils.class.getResourceAsStream("/data.xml");
            Collection<Map<String, Object>> maps = XmlDatagramResolver.resolveOneWay(contentStream);
            System.out.println(maps);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
