package com.cspider.crawler.hanatour.main.rt;

import org.dom4j.Element;
import org.dom4j.Node;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * mapping extractor
 * Created by kai_li on 2016/6/6.
 */
public class MappingExtractor {
    /**
     * returned sample data :
     * {
         14 = [[1,1],
               [1,2],
               [1,3],
               [2,1],
               [2,2],
               [2,3],
               [3,1],
               [3,2],
               [3,3]
              ],
         18 = [[1,1],
               [1,2],
               [1,3],
               [2,1],
               [2,2],
               [2,3],
               [3,1],
               [3,2],
               [3,3]
              ],
         21 = [[1,1],
               [1,2],
               [1,3],
               [2,1],
               [2,2],
               [2,3],
               [3,1],
               [3,2],
               [3,3]
              ]
       }
     * @param root
     * @return
     */
    public static Map<String, List<String[]>> extract(Node root){
        List<Element> mappings = root.selectNodes("//string[text()='fsaList']/following-sibling::flex.messaging.io.ArrayCollection/object-array/ASObject/entry/string[text()='fslink']/following-sibling::string");
        Map<String, List<String[]>> priceFlights = new LinkedHashMap<String, List<String[]>>();
        if (mappings!=null){

            for (int i = 0; i < mappings.size(); i++) {
                Element mapping = mappings.get(i);
                String mappingStr = mapping.getText();
                mappingStr = mappingStr.substring(0, mappingStr.length()-2);
                String[] tuple = mappingStr.split("-");
                if (priceFlights.get(tuple[0])==null){
                    priceFlights.put(tuple[0], new LinkedList<String[]>());
                }
                priceFlights.get(tuple[0]).add(new String[]{tuple[1], tuple[2]});
            }
        }
        return priceFlights;
    }
}
