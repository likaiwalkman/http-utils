package com.cspider.crawler.hanatour.main;

import com.cspider.crawler.hanatour.main.rt.PriceFlightMapperProcessor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.*;

/**
 * resolver for Xml one way data
 * Created by kai_li on 2016/6/3.
 */
public class RtXmlDatagramResolver {
    public static Map<String, String> resolveData(InputStream contentStream) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(contentStream);
        Node root = doc.selectSingleNode("/ActionMessage");


        Map<String, String> map = PriceFlightMapperProcessor.map(root);

        //Collection<Map<String, Object>> values = seqPrices.values();
        return map;
    }
}
