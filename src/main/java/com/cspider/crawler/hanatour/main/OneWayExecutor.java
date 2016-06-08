package com.cspider.crawler.hanatour.main;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.jmeter.protocol.amf.util.AmfXmlConverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class OneWayExecutor {
    public static String getOneWayData(String depCity, String arrCity, String depDate) throws IOException {
        byte[] req = IOUtils.toByteArray(HttpDatagramSenderUtils.class.getResourceAsStream("/ed/req2"));
        BytesReplacer.replaceFirst(req, "SEL", depCity);
        BytesReplacer.replaceFirst(req, "SHA", arrCity);
        BytesReplacer.replaceFirst(req, "20160531", depDate);

        ByteArrayInputStream reqStream = new ByteArrayInputStream(req);
        byte[] reqHeaderBytes = HttpDatagramHeaderAndBodyDelimiter.truncateHeader(reqStream);
        byte[] reqBodyBytes = IOUtils.toByteArray(reqStream);
        req = ArrayUtils.addAll(reqHeaderBytes, reqBodyBytes);

        ByteArrayInputStream headerDataStream = new ByteArrayInputStream(reqHeaderBytes);
        ByteArrayInputStream fullDataStream = new ByteArrayInputStream(req);

        Socket socket = HttpDatagramSenderUtils.request(headerDataStream, fullDataStream);
        InputStream ins = socket.getInputStream();

        //truncate header part from input stream
        HttpDatagramHeaderAndBodyDelimiter.truncateHeader(ins);

        byte[] bodyBytes = IOUtils.toByteArray(ins);
        return AmfXmlConverter.convertAmfMessageToXml(bodyBytes, true);
    }
}
