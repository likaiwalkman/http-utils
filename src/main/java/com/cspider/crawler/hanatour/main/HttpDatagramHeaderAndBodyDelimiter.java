package com.cspider.crawler.hanatour.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * delimiter body from http protocol stream
 * Created by kai_li on 2016/5/20.
 */
public class HttpDatagramHeaderAndBodyDelimiter {

    public static byte[] truncateHeader(InputStream ins) throws IOException {
        BoundaryQueue<Integer> delimiter = new BoundaryQueue<Integer>(4);
        delimiter.addAll(Arrays.asList((Integer) (int)'\r', (Integer) (int) '\n', (Integer) (int) '\r', (Integer) (int) '\n'));

        BoundaryQueue<Integer> holder = new BoundaryQueue<Integer>(4);
        int bit;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        while ((bit= ins.read()) != -1) {
            baos.write(bit);
            holder.offer(bit);
            if (holder.equals(delimiter)) {
                break;
            }
        }
        return baos.toByteArray();
    }
}
