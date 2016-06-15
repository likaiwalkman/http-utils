package com.cspider.crawler.hanatour.main;

/*import java.util.LinkedList;
import java.util.List;*/

/**
 * matcher helper
 * Created by kai_li on 2016/6/15.
 */
public class MatcherHelper {

    public int[] match(byte[] target, byte[] pattern) {
        int m, n;
        if (target == null || pattern == null || (m = target.length) < (n = pattern.length) ) {
            return null;
        }

        //TODO collect features from pattern to use it to improve comparing efficiency later
        //Still undo

        //List<Integer> set = new LinkedList<Integer>();

        //TODO for index i: aggregator step size not must be 1
        for (int i = 0; i < m - n; i++) {

            //TODO for index i: start index not must be 0,
            //TODO for index j: aggregator step size not must be 1
            for (int j = 0; j < n; j++) {
                if (target[i+j] != pattern[j]) {
                    // encountered inequivalent element
                    //TODO push index i to move forward as far as possible
                } else {
                    if (j == n-1){
                        System.out.println(i);
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        MatcherHelper matcherHelper = new MatcherHelper();
        matcherHelper.match(new byte[]{0,1,2,3,4,5,6}, new byte[]{2,3,4});
    }
}
