package com.cspider.crawler.hanatour.main;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.jmeter.protocol.amf.util.AmfXmlConverter;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

public class HttpDatagramSenderUtilsTest {

    @Test
    public void testRoundTripGetter() throws Exception {
        String depCity = "SEL";
        String arrCity = "SHA";
        String depDate   = "20160610";
        String rtnDate   = "20160615";

        String rspBodyInXml = RoundTripExecutor.getRoundTripData(depCity, arrCity, depDate, rtnDate);

        if (rspBodyInXml.contains("ErrorMessage") ){
            throw new RuntimeException("server side error");
        } else {
            InputStream contentStream = new ByteArrayInputStream(rspBodyInXml.getBytes());
            System.out.println();
            Map<String, String> maps = RtXmlDatagramResolver.resolveData(contentStream);
            String result = JSON.toJSON(maps).toString();
            System.out.println(result);
        }
    }

    @Test
    public void testOneWayGetter() throws Exception {
        String depCity = "SEL";
        String arrCity = "SHA";
        String depDate = "20160610";

        String rspBodyInXml = OneWayExecutor.getOneWayData(depCity, arrCity, depDate);

        if ( ! rspBodyInXml.contains("ErrorMessage") ){
            InputStream contentStream = new ByteArrayInputStream(rspBodyInXml.getBytes());
            Collection<Map<String, Object>> maps = XmlDatagramResolver.resolveOneWay(contentStream);
            System.out.println(maps);
        } else {
            throw new RuntimeException("server side error");
        }
    }

    @Test
    public void testHexFileToBinary() throws IOException {
        InputStream req1 = HttpDatagramSenderUtils.class.getResourceAsStream("/raw/1Req");
        InputStream req2 = HttpDatagramSenderUtils.class.getResourceAsStream("/rt/raw/2Req");
        InputStream res1 = HttpDatagramSenderUtils.class.getResourceAsStream("/raw/1Res");
        InputStream res2 = HttpDatagramSenderUtils.class.getResourceAsStream("/raw/2Res");

        String q1 = IOUtils.toString(req1);
        String q2 = IOUtils.toString(req2);
        String s1 = IOUtils.toString(res1);
        String s2 = IOUtils.toString(res2);

        byte[] q1Bytes = HexToBytesUtils.toByteArray(q1, ", ");
        byte[] q2Bytes = HexToBytesUtils.toByteArray(q2, ", ");

        byte[] s1Bytes = HexToBytesUtils.toByteArray(s1, ", ");
        byte[] s2Bytes = HexToBytesUtils.toByteArray(s2, ", ");

        IOUtils.write(q1Bytes, new FileOutputStream("D:/ed/req1"));
        IOUtils.write(q2Bytes, new FileOutputStream("D:/ed/req2"));
        IOUtils.write(s1Bytes, new FileOutputStream("D:/ed/res1"));
        IOUtils.write(s2Bytes, new FileOutputStream("D:/ed/res2"));
    }

    @Test
    public void testExtractXml() throws IOException {
        InputStream req1 = HttpDatagramSenderUtils.class.getResourceAsStream("/ed/req1");
        InputStream req2 = HttpDatagramSenderUtils.class.getResourceAsStream("/ed/req2");
        InputStream res1 = HttpDatagramSenderUtils.class.getResourceAsStream("/ed/res1");
        InputStream res2 = HttpDatagramSenderUtils.class.getResourceAsStream("/ed/res2");

        HttpDatagramHeaderAndBodyDelimiter.truncateHeader(req1);
        HttpDatagramHeaderAndBodyDelimiter.truncateHeader(req2);
        HttpDatagramHeaderAndBodyDelimiter.truncateHeader(res1);
        HttpDatagramHeaderAndBodyDelimiter.truncateHeader(res2);

        byte[] req1BodyBytes = IOUtils.toByteArray(req1);
        byte[] req2BodyBytes = IOUtils.toByteArray(req2);
        byte[] res1BodyBytes = IOUtils.toByteArray(res1);
        byte[] res2BodyBytes = IOUtils.toByteArray(res2);

        String req1BodyXml = AmfXmlConverter.convertAmfMessageToXml(req1BodyBytes, true);
        String req2BodyXml = AmfXmlConverter.convertAmfMessageToXml(req2BodyBytes, true);
        String res1BodyXml = AmfXmlConverter.convertAmfMessageToXml(res1BodyBytes, true);
        String res2BodyXml = AmfXmlConverter.convertAmfMessageToXml(res2BodyBytes, true);

        IOUtils.write(req1BodyXml, new FileOutputStream("D:/ed/xml/req1.xml"));
        IOUtils.write(req2BodyXml, new FileOutputStream("D:/ed/xml/req2.xml"));
        IOUtils.write(res1BodyXml, new FileOutputStream("D:/ed/xml/res1.xml"));
        IOUtils.write(res2BodyXml, new FileOutputStream("D:/ed/xml/res2.xml"));
    }



}