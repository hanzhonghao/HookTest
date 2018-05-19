package com.zhonghao.encrycapability.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class fileOperation {
    public static boolean exist(String file) {
        return (new File(file)).exists();
    }

    public static int writeString(String file, String str, int mode) {
        //mode:0:create
        //1:overwrite
        File outputFile = new File(file);
        if (outputFile.exists() && mode == 0) {
            return -1;
        }
        java.io.PrintWriter output;
        try {
            output = new java.io.PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            return -2;
        }
        output.print(str);
        output.close();
        return 0;
    }

    public static String readString(String file) {
        String inputStr = "";

        File inputFile = new File(file);
        ByteArrayOutputStream bout;
        FileInputStream in;
        bout = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int length;
        try {
            in = new FileInputStream(inputFile); //获得输入流
            while ((length = in.read(buf)) != -1) {
                bout.write(buf, 0, length);
            }
            byte[] content = bout.toByteArray();

            inputStr = new String(content);
            System.out.println("content is :" + new String(content, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(inputStr);
    }

    public static int writeBytes(String file, byte[] bytes) {
        FileOutputStream out;
        try {
            out = new FileOutputStream(
                    new File(file));
            try {
                out.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static byte[] readBytes(String file) {
        byte[] r = null;
        java.io.ObjectInputStream in;
        try {
            in = new java.io.ObjectInputStream(
                    new java.io.FileInputStream(file));
            r = (byte[]) in.readObject();
            in.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return r;
    }

    //删除文件
    public static void delFile(String file) {
        File fileChild = new File(file);
        if (fileChild.isFile()) {
            fileChild.delete();
        }
    }
}
