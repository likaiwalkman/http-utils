package com.cspider.crawler.hanatour.main;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.jmeter.protocol.amf.util.AmfXmlConverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class RoundTripExecutor {
    public static String getRoundTripData(String depCity, String arrCity, String depDate, String rtnDate) throws IOException {

        //get headers in bytes
        InputStream reqStream = HttpDatagramSenderUtils.class.getResourceAsStream("/rt/ed/req2");
        byte[] reqHeaderBytes = HttpDatagramHeaderAndBodyDelimiter.truncateHeader(reqStream);

        //get body in bytes
        byte[] reqBodyBytes = IOUtils.toByteArray(reqStream);

        String hardCodedDepCity = "SEL";
        String hardCodedArrCity = "SHA";
        String hardCodedDepDate = "20160607";
        String hardCodedArrDate = "20160612";
        BytesReplacer.replaceFirst(reqBodyBytes, hardCodedDepDate, depDate);
        BytesReplacer.replaceFirst(reqBodyBytes, hardCodedArrDate, rtnDate);
        BytesReplacer.replaceFirst(reqBodyBytes, hardCodedDepCity, depCity);
        BytesReplacer.replaceFirst(reqBodyBytes, hardCodedArrCity, arrCity);

        byte[] httpData = ArrayUtils.addAll(reqHeaderBytes, reqBodyBytes);

        ByteArrayInputStream hostStream = new ByteArrayInputStream(reqHeaderBytes);
        ByteArrayInputStream dataStream = new ByteArrayInputStream(httpData);

        Socket socket = HttpDatagramSenderUtils.request(hostStream, dataStream);
        InputStream ins = socket.getInputStream();

        HttpDatagramHeaderAndBodyDelimiter.truncateHeader(ins);

        byte[] bodyBytes = IOUtils.toByteArray(ins);
        if (bodyBytes == null || bodyBytes.length == 0) {
            return null;
        } else {
            String readableBody = AmfXmlConverter.convertAmfMessageToXml(bodyBytes, false);
            return readableBody;
        }
    }
}
