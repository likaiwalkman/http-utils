package com.cspider.crawler.hanatour.main.rt;

import org.dom4j.Element;
import org.dom4j.Node;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * price extractor
 * Created by kai_li on 2016/6/7.
 */
public class PriceExtractor {
    /**
     * returned sample data (seq number as key, value is its corresponding price):
     * {
     *   14 : 469100.0,
     *   18 : 518600.0,
     *   21 : 568100.0,
     *   22 : 667100.0,
     *   23 : 766100.0
     * }
     * @param root
     * @return
     */
    public static Map<String, Map<String, Object>> extract(Node root){

        //price-related
        List<Element> prices = root.selectNodes("//entry/string[text()='fareList']/following-sibling::flex.messaging.io.ArrayCollection/object-array/ASObject");

        Map<String, Map<String, Object>> assembledPrices = new LinkedHashMap<String, Map<String, Object>>();
        if ( prices != null ) {
            for (Element price : prices) {

                Element seqText = (Element) price.selectNodes("entry/string[text()='seq']/following-sibling::string").get(0);
                String seq = seqText.getText();

                Element adtTaxBillText = (Element) price.selectNodes("entry/string[text()='adtTaxBill']/following-sibling::string").get(0);
                String adtTaxBill = adtTaxBillText.getText();

                Element dsfText = (Element) price.selectNodes("entry/string[text()='dsf']/following-sibling::string").get(0);
                String dsf = dsfText.getText();

                double totalPrice = Double.parseDouble(adtTaxBill) + Double.parseDouble(dsf);//Integer.parseInt(adtTaxBill) + Integer.parseInt(dsf);

                Map<String, Object> priceDetail = new HashMap<String, Object>();
                priceDetail.put("totalPrice", totalPrice);
                assembledPrices.put(seq, priceDetail);
            }
        }
        return assembledPrices;
    }
}
