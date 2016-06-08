import com.thoughtworks.xstream.XStream;
import flex.messaging.io.MessageIOConstants;
import flex.messaging.io.amf.ASObject;
import flex.messaging.io.amf.ActionMessage;
import flex.messaging.io.amf.MessageBody;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.messages.RemotingMessage;
import org.apache.jmeter.protocol.amf.util.AmfXmlConverter;
import org.apache.jmeter.protocol.amf.util.SampleRequestVO;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class ConverterTest {
    public static void main(String[] args) throws IOException {
//		runXmlAmfXmlTest();
        runXmlAmfXmlMessageTest();

//		runXmlAmfXmlMessageTest();

//		testASObjectConverter();
//      test();
    }
    public static void test() throws IOException{
        RandomAccessFile f = new RandomAccessFile("request.ser", "r");
        byte[] amf = new byte[(int)f.length()];
        f.read(amf);
        System.out.println("*** Request: \n " + AmfXmlConverter.convertAmfMessageToXml(amf, true));
        f = new RandomAccessFile("response.ser", "r");
        byte[]  resp = new byte[(int)f.length()];
        f.read(resp);
        System.out.println("*** Response: \n " + AmfXmlConverter.convertAmfMessageToXml(resp, true));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static RemotingMessage createTestObject() {
        RemotingMessage msg = new RemotingMessage();
        msg.setOperation("perform");

        Map headers = new HashMap();
        msg.setHeaders(headers);

        headers.put("DSid", "");
        headers.put("DSEndpoint", "");

        SampleRequestVO vo = new SampleRequestVO();

        msg.setBody(vo);

        return msg;
    }

    public static void runXmlAmfXmlMessageTest() {
        XStream xs = AmfXmlConverter.getXStream();

        ActionMessage msg = createTestMessage();

        String xmlIn = xs.toXML(msg);

        System.out.println("Original XML: \n"+xmlIn);

        byte[] amfIn = AmfXmlConverter.convertXmlToAmfMessage(xmlIn);

        String amfInStr = "";
        for (byte i : amfIn) {
            amfInStr += i + ", ";
        }
        System.out.println("Original AMF: \n"+amfInStr);

        String xmlOut = AmfXmlConverter.convertAmfMessageToXml(amfIn, false);

        System.out.println("Result XML: \n" + xmlOut);

        byte[] amfOut = AmfXmlConverter.convertXmlToAmfMessage(xmlIn);

        String amfOutStr = "";
        for (byte i : amfOut) {
            amfOutStr += i + ", ";
        }
        System.out.println("Result AMF: \n"+amfOutStr);

        System.out.println("Result AMF is " + (amfOut.length - amfIn.length) + " bytes longer");

        int bytesMismatch = 0;
        for (int i=0; i < Math.min(amfOut.length, amfIn.length); i++) {
            if (amfOut[i] != amfIn[i])
                bytesMismatch++;
        }
        System.out.println("Result and original AMF have " + bytesMismatch + " bytes different");
    }

    public static ActionMessage createTestMessage() {
        // Body
        AsyncMessage asMsg= new AsyncMessage();
        RemotingMessage msg = new RemotingMessage();
        msg.setOperation("perform");
        HashMap<String, String> headers = new HashMap<String,String>();
        msg.setHeaders(headers);
        headers.put("DSid", "");
        headers.put("DSEndpoint", "");

        SampleRequestVO vo = new SampleRequestVO();
        msg.setBody(vo);

        // Message
        ActionMessage requestMessage = new ActionMessage(MessageIOConstants.AMF3);

        // None required
        //MessageHeader header1 = new MessageHeader("someHeader", false, "someHeaderContentObject");
        //requestMessage.addHeader(header1);

        MessageBody body1 = new MessageBody("/2/OnResult", "", asMsg);
        asMsg.setBody(body1);

        requestMessage.addBody(body1);

        return requestMessage;
    }

    @SuppressWarnings("unchecked")
    public static void testASObjectConverter() {
        ASObject asObj = new ASObject();

        Object[] objArr = {"thing", "other thing"};

        asObj.setType("com.test.RequestVO");
        asObj.put("clientId", "abcd");
        asObj.put("args", objArr);

        XStream xs = AmfXmlConverter.getXStream();

        String xml = xs.toXML(asObj);

        System.out.println("Result: \n"+xml);

        ASObject newASObj = (ASObject) xs.fromXML(xml);

        System.out.print("Rebuilt: \nType: "+newASObj.getType()+"\n"+newASObj.toString());
    }
}
