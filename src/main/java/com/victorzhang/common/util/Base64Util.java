package com.victorzhang.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64算法实现加解密
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-04 15:14:07
 */
public class Base64Util {

    /**
     * BASE64 加密
     * @param data 待加密字节数组
     * @return BASE64加密字符串
     */
    public static String base64Encrypt(byte[] data) {
        String result = new BASE64Encoder().encode(data);
        return result;
    }

    /**
     * BASE64 解密
     * @param data 待解密字符串
     * @return BASE64解密字符串
     * @throws Exception
     */
    public static String base64Decrypt(String data) throws Exception {
        byte[] resultBytes = new BASE64Decoder().decodeBuffer(data);
        return new String(resultBytes);
    }
}
