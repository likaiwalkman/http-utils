package com.cspider.crawler.hanatour.main.rt;

import org.dom4j.Element;
import org.dom4j.Node;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * as its name implies
 * Created by kai_li on 2016/6/6.
 */
public class FlightsExtractor {

    /**
     * returnedSampleData
     * {
     *   goFlightsGroup : {
     *     1 : CX:5319,CX:5620,
     *     2 : CX:5319,CX:5636,
     *     3 : CX:5319,CX:5622
     *   },
     *   backFlightsGroup : {
     *     1 : CX:5627,CX:5318,
     *     2 : CX:5621,CX:5318,
     *     3 : CX:5637,CX:5318
     *   }
     * }
     * @param root
     */
    public static Map<String, Map<String, String>> extract(Node root){

        //flights-related
        List<Element> flights = root.selectNodes("//string[text()='skdList']/following-sibling::flex.messaging.io.ArrayCollection/object-array/ASObject");

        Stack<String> allSeqStackerHelper = new Stack<String>();

        Map<String, Map<String, String>> rawFlights  = new LinkedHashMap<String, Map<String, String>>();
        Map<String, String> goFlightsGroup = new LinkedHashMap<String, String>();
        Map<String, String> backFlightsGroup = new LinkedHashMap<String, String>();

        rawFlights.put("goFlightsGroup", goFlightsGroup);
        rawFlights.put("backFlightsGroup", backFlightsGroup);

        boolean meetBack = false;

        if (flights != null) {
            for (Element flight : flights) {
                String flightSeqNo = ((Element) flight.selectNodes("entry/string[text()='seq']/following-sibling::string").get(0)).getTextTrim();

                if ( !meetBack  && allSeqStackerHelper.size() > 0 ){
                    String top = allSeqStackerHelper.peek();
                    if (Integer.parseInt(top) >= Integer.parseInt(flightSeqNo)) {
                        meetBack = true;
                    }
                }
                allSeqStackerHelper.push(flightSeqNo);

                List<Element> flightRoutes = flight.selectNodes("entry/string[text()='segList']/following-sibling::flex.messaging.io.ArrayCollection/object-array/ASObject");
                StringBuilder fullRoute = new StringBuilder();
                for (Element flightRoute : flightRoutes) {
                    Element oneStopAirline = (Element) flightRoute.selectNodes("entry/string[text()='mcr']/following-sibling::string").get(0);
                    Element oneStopAirlineNo = (Element) flightRoute.selectNodes("entry/string[text()='mflt']/following-sibling::string").get(0);
                    String airlineNo = oneStopAirline.getText() + ":" +  oneStopAirlineNo.getText();

                    fullRoute.append(airlineNo + ",");
                }
                fullRoute.deleteCharAt(fullRoute.length()-1);

                if (meetBack){
                    backFlightsGroup.put(flightSeqNo, fullRoute.toString());
                } else {
                    goFlightsGroup.put(flightSeqNo, fullRoute.toString());
                }
                //System.out.println(flightSeqNo);
            }
        }
        return rawFlights;
    }
}
