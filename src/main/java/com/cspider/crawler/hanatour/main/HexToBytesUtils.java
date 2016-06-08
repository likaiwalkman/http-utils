package com.cspider.crawler.hanatour.main;

import java.io.ByteArrayOutputStream;

public class HexToBytesUtils {
    public static byte[] toByteArray(String hex, String delimiter) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String[] toks = hex.split(delimiter);
        for (String tok : toks) baos.write(Integer.parseInt(tok, 16));
        return baos.toByteArray();
    }
}
