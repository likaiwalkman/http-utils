package com.cspider.crawler.hanatour.main.rt;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * test case for MappingExtractor
 * Created by kai_li on 2016/6/6.
 */
public class MappingExtractorTest {

    @Test
    public void extract() throws Exception {
        SAXReader reader = new SAXReader();
        InputStream contentStream = FlightsExtractor.class.getResourceAsStream("/dataRt.xml");
        Document doc = reader.read(contentStream);
        Node root = doc.selectSingleNode("/ActionMessage");
        Map<String, List<String[]>> mappings = MappingExtractor.extract(root);

        StringBuilder builder = new StringBuilder();
        if (mappings != null) {
            for (String priceSeqNo : mappings.keySet()) {
                List<String[]> assembles = mappings.get(priceSeqNo);
                for (String[] assemble : assembles) {
                    StringBuilder innerBuilder = new StringBuilder();
                    for (String oneStop : assemble) {
                        innerBuilder.append(oneStop + "-");
                    }
                    innerBuilder.deleteCharAt(innerBuilder.length()-1);
                    builder.append( priceSeqNo + "-" + innerBuilder.toString() + ", ");
                }
            }
        }

        builder.delete(builder.length()-2, builder.length());

        String factualResult = "[" + builder + "]";

        String expectedResult = "[14-1-1, 14-1-2, 14-1-3, 14-2-1, 14-2-2, 14-2-3, 14-3-1, 14-3-2, 14-3-3, 18-1-1, 18-1-2, 18-1-3, 18-2-1, 18-2-2, 18-2-3, 18-3-1, 18-3-2, 18-3-3, 21-1-1, 21-1-2, 21-1-3, 21-2-1, 21-2-2, 21-2-3, 21-3-1, 21-3-2, 21-3-3, 22-1-1, 22-1-2, 22-1-3, 22-2-1, 22-2-2, 22-2-3, 22-3-1, 22-3-2, 22-3-3, 23-1-1, 23-1-2, 23-1-3, 23-2-1, 23-2-2, 23-2-3, 23-3-1, 23-3-2, 23-3-3]";
        Assert.assertEquals(expectedResult, factualResult);
    }

}