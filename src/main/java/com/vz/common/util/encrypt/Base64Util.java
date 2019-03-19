package com.vz.common.util.encrypt;

import java.io.IOException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64算法实现加解密
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-04 15:14:07
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Base64Util {

    /**
     * BASE64 加密
     * @param data 待加密字节数组
     * @return BASE64加密字符串
     */
    public static String encrypt(byte[] data) {
        return new BASE64Encoder().encode(data);
    }

    /**
     * BASE64 解密
     * @param data 待解密字符串
     * @return BASE64解密字符串
     * @throws IOException 流异常
     */
    public static String decrypt(String data) throws IOException {
        byte[] resultBytes = new BASE64Decoder().decodeBuffer(data);
        return new String(resultBytes);
    }
}
