package utils;

import java.security.MessageDigest;

/**
 * 项目名称：HookTest
 * 包名：com.zhonghao.hooktest2
 * 创建人：小豪
 * 创建时间：2017/12/16 15:26
 * 类描述：
 */

public class MD5Util {
    //生成MD5
    public static String getKeyByMD5(String SharedUserId,String packageInfo,String RandomInfo) {
        String md5 = "";
        String message = SharedUserId+packageInfo+RandomInfo;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位
            md5 = bytesToHex(md5Byte);                            // 转换为16进制字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    // 二进制转十六进制
    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if(num < 0) {
                num += 256;
            }
            if(num < 16){
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}
