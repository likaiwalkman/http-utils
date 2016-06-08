package com.cspider.crawler.hanatour.main;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * test for RtXmlDatagramResolver
 * Created by kai_li on 2016/6/6.
 */
public class RtXmlDatagramResolverTest {

    @Test
    public void resolveData() throws Exception {
        try {
            InputStream contentStream = HttpDatagramSenderUtils.class.getResourceAsStream("/dataRt.xml");
            Map<String, String> maps = RtXmlDatagramResolver.resolveData(contentStream);
            System.out.println(maps);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testXmlResolve(){
        try {
            InputStream contentStream = HttpDatagramSenderUtils.class.getResourceAsStream("/dataRt.xml");
            SAXReader reader = new SAXReader();
            Document doc = reader.read(contentStream);

            Node root = doc.selectSingleNode("ActionMessage");

            List<Element> mappings = root.selectNodes("//string[text()='fsaList']/following-sibling::flex.messaging.io.ArrayCollection/object-array/ASObject/entry/string[text()='fslink']/following-sibling::string");

            for (Element mapping : mappings) {
                System.out.println(mapping.getText());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}