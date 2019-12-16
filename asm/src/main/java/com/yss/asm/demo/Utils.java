package com.yss.asm.demo;

import java.io.*;

public class Utils {

    public static byte[] readBytesFile(String filePath) throws IOException {
        int bytesRead = 0;
        byte[] allClassBytes = null;
        byte[] buffer = new byte[1024];

        try (InputStream input = new BufferedInputStream(new FileInputStream(filePath))){
            while ((bytesRead = input.read(buffer)) != -1) {
                int length = allClassBytes == null ? bytesRead : allClassBytes.length + bytesRead;
                byte[] newAll = new byte[length];
                if(allClassBytes != null){
                    System.arraycopy(allClassBytes, 0, newAll, 0, allClassBytes.length);
                    System.arraycopy(buffer, 0, newAll, allClassBytes.length, bytesRead);
                }else {
                    System.arraycopy(buffer, 0, newAll, 0, bytesRead);
                }
                allClassBytes = newAll;
            }
        }

        return allClassBytes;
    }

    public static void writeByte2File(String filePath, byte[] bytes){
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
