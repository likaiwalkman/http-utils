package com.cspider.crawler.hanatour.main.rt;

import org.dom4j.Node;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * map price to flights
 * Created by kai_li on 2016/6/7.
 */
public class PriceFlightMapperProcessor {
    /**
     * map price to flight
     */
    public static Map<String, String> map(Node root){

        Map<String, String> result = new LinkedHashMap<String, String>();

        Map<String, Map<String, Object>> prices = PriceExtractor.extract(root);
        Map<String, Map<String, String>> flights = FlightsExtractor.extract(root);
        Map<String, List<String[]>> mappings = MappingExtractor.extract(root);

        Map<String, String> goRoutesMapper = flights.get("goFlightsGroup");
        Map<String, String> backRoutesMapper = flights.get("backFlightsGroup");

        // merge above three result to desired final data structure
        
        for (String priceSeq : prices.keySet()) {
            List<String[]> allRoutes = mappings.get(priceSeq);
            for (String[] fullRoute : allRoutes) {
                String goSeq = fullRoute[0];
                String backSeq = fullRoute[1];

                String goRoute = goRoutesMapper.get(goSeq);
                String backRoute = backRoutesMapper.get(backSeq);

                String fullRoutePath = goRoute + "~" + backRoute;

                Double totalPrice = (Double) prices.get(priceSeq).get("totalPrice");
                result.put(fullRoutePath, totalPrice.toString());
            }
        }

        return result;

    }
}
