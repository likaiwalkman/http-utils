package com.cspider.crawler.hanatour.main;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class BytesReplacerTest {

    @Test
    public void replaceFirst() throws Exception {
        byte[] bytes = new byte[]{49,50,51,52,53,54,55,56};
        String oldChars = new String(new byte[]{50,51});
        String newChars = new String(new byte[]{51,52});

        BytesReplacer.replaceFirst(bytes, oldChars, newChars);

        boolean rs = Arrays.equals(bytes, new byte[]{49,51,52,52,53,54,55,56});
        Assert.assertTrue(rs);
    }

}