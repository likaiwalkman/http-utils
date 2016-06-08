package com.cspider.crawler.hanatour.main;

public class BytesReplacer {
    public static void replaceFirst( byte[] bytes, String oldChars, String  newChars){
        if (bytes == null || bytes.length == 0){
            return;
        }
        if (oldChars!=null && oldChars.getBytes().length > bytes.length){
            return;
        }
        if (oldChars == null || oldChars.getBytes().length == 0){
            throw new IllegalArgumentException("old chars can not be null");
        }
        if (newChars == null || newChars.getBytes().length == 0){
            throw new IllegalArgumentException("new chars can not be null");
        }
        if (oldChars.getBytes().length != newChars.getBytes().length){
            throw new IllegalArgumentException("old chars' length is not equal to new chars");
        }
        byte[] oldCharsBytes = oldChars.getBytes();
        byte[] newCharsBytes = newChars.getBytes();

        BoundaryQueue<Byte> target = new BoundaryQueue<Byte>(oldCharsBytes.length);
        for (byte bit : oldCharsBytes) {
            target.offer(bit);
        }

        BoundaryQueue<Byte> helper = new BoundaryQueue<Byte>(oldCharsBytes.length);
        int pos = -1;
        for (byte bit : bytes) {
            helper.offer(bit);
            pos++;
            if (helper.equals(target)){
                break;
            }
        }

        for (int i = newCharsBytes.length; i > 0 ; i--) {
            bytes[pos--] = newCharsBytes[i-1];
        }
    }
}
