package com.cspider.crawler.hanatour.main;

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
public class XmlDatagramResolver {
    public static Collection<Map<String, Object>> resolveOneWay(InputStream contentStream) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(contentStream);
        Node root = doc.selectSingleNode("/ActionMessage");

        Map<Integer, String> seqFlights = new HashMap<Integer, String>();
        //flights-related
        List skdList = root.selectNodes("//string[text()='skdList']/following-sibling::flex.messaging.io.ArrayCollection/object-array/ASObject");
        for (Object o : skdList) {
            Element e = (Element)o;
            Element seqElement = (Element) e.selectNodes("entry/string[text()='seq']/following-sibling::string").get(0);
            String seqNo = seqElement.getText();

            List cList = e.selectNodes("entry/string[text()='segList']/following-sibling::flex.messaging.io.ArrayCollection/object-array/ASObject");
            for (Object co : cList) {
                Element ce = (Element)co;
                Element airline = (Element)ce.selectNodes("entry/string[text()='mcr']/following-sibling::string").get(0);
                String airlineName = airline.getText();

                Element flight = (Element)ce.selectNodes("entry/string[text()='mflt']/following-sibling::string").get(0);
                String flightNo = flight.getText();

                seqFlights.put(Integer.parseInt(seqNo), airlineName+flightNo);

            }
        }

        Map<Integer, Map<String, Object>> seqPrices = new TreeMap<Integer, Map<String, Object>>();
        //price-related
        List<Element> fareList = root.selectNodes("//entry/string[text()='fareList']/following-sibling::flex.messaging.io.ArrayCollection/object-array/ASObject");
        for (Element itm : fareList) {
            Element adtTax = (Element) itm.selectNodes("entry/string[text()='adtTax']/following-sibling::string").get(0);
            String adtTaxStr =  adtTax.getText();

            Element dsf = (Element) itm.selectNodes("entry/string[text()='dsf']/following-sibling::string").get(0);
            String dsfStr =  dsf.getText();

            Element seq = (Element) itm.selectNodes("entry/string[text()='seq']/following-sibling::string").get(0);
            //TODO unique sequence no
            String seqNo =  seq.getText();

            Double literalPrice = Double.parseDouble(adtTaxStr)+Double.parseDouble(dsfStr);

            Map<String, Object> price = new HashMap<String, Object>();
            price.put("totalPrice", literalPrice);
            seqPrices.put(Integer.parseInt(seqNo), price);
        }

        Map<Integer, Set<Integer>> mappings = new HashMap<Integer, Set<Integer>>();
        //flight-price-mapping related
        List<Element> fsaLsit = root.selectNodes("//entry/string[text()='fsaList']/following-sibling::flex.messaging.io.ArrayCollection/object-array/ASObject");
        for (Element element : fsaLsit) {
            Element map = (Element)element.selectNodes("entry/string[text()='fslink']/following-sibling::string").get(0);
            String mapping = map.getText();
            mapping = mapping.substring(0, mapping.length() - 4);
            String[] priceToFlights = mapping.split("-");
            Integer priceSeq = Integer.parseInt(priceToFlights[0]);
            Integer flightSeq = Integer.parseInt(priceToFlights[1]);
            if (!mappings.containsKey(priceSeq)) {
                mappings.put(priceSeq, new LinkedHashSet<Integer>());
            }
            Set<Integer> priceFlights = mappings.get(priceSeq);
            priceFlights.add(flightSeq);
        }

        for (Integer priceSeq : seqPrices.keySet()) {
            Set<Integer> priceFlightSeqs = mappings.get(priceSeq);
            for (Integer priceFlightSeq : priceFlightSeqs) {
                String priceFlightNo = seqFlights.get(priceFlightSeq);
                Map<String, Object> seqPrice = seqPrices.get(priceSeq);
                if (!seqPrice.containsKey("flights")) {
                    seqPrice.put("flights", new LinkedList<String>());
                }
                List<String> flights = (List<String>) seqPrice.get("flights");
                flights.add(priceFlightNo);
            }
        }

        Collection<Map<String, Object>> values = seqPrices.values();
        return values;
    }
}
