package com.cspider.crawler.hanatour.main;

import java.io.*;

/**
 * outer process
 * Created by kai_li on 2016/6/20.
 */
public class Outer {
    public static void main(String[] args) throws IOException, InterruptedException {
        //Process p = Runtime.getRuntime().exec("java CrawlerMain");
        Process p = Runtime.getRuntime().exec("java Inner");

        OutputStream pOutputStream = p.getOutputStream();
        InputStream pInputStream = p.getInputStream();

        Reader r = new InputStreamReader(pInputStream);
        Writer w = new OutputStreamWriter(pOutputStream);

        BufferedReader pReader = new BufferedReader(r);
        BufferedWriter pWriter = new BufferedWriter(w);

        OutputStream stdout = System.out;
        //InputStream stdin = Outer.class.getResourceAsStream("/failResource/hanatour_1466411713296.txt");//System.in;
        InputStream stdin = System.in;

        Reader reader = new InputStreamReader(stdin);
        BufferedReader stdReader = new BufferedReader(reader);

        Writer writer = new OutputStreamWriter(stdout);
        BufferedWriter stdWriter = new BufferedWriter(writer);

        String line;
        while ( (line=stdReader.readLine()) != null){
            pWriter.write(line);
            pWriter.newLine();
            pWriter.flush();
            //Thread.sleep(1000);
            String s = pReader.readLine();
            stdWriter.write(s);
            stdWriter.flush();
        }
    }
}
