package com.cspider.crawler.hanatour.main;

import java.io.*;
//import java.util.concurrent.TimeUnit;

/**
 * inner process
 * Created by kai_li on 2016/6/20.
 */
public class Inner {
    public static void main(String[] args) throws IOException, InterruptedException {

        InputStream stdin = System.in;
        Reader reader = new InputStreamReader(stdin);
        BufferedReader stdReader = new BufferedReader(reader);

        OutputStream stdout = System.out;
        Writer writer = new OutputStreamWriter(stdout);
        BufferedWriter stdWriter = new BufferedWriter(writer);

        String line;
        while ( (line=stdReader.readLine()) != null){
            stdWriter.write(line);
            stdWriter.newLine();
            stdWriter.flush();
        }

    }
}
