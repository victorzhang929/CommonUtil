package com.vz.common.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Aes对称加密
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-04 15:32:12
 */
public final class AesUtil {

    private static final String AES = "AES";
    /**
     * 初始化密钥生成器
     *
     * @return 密钥
     * @throws Exception 抛出异常信息
     */
    public static byte[] initKey() throws Exception {
        //密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        //初始化密钥生成器
        keyGenerator.init(128);
        //生成密钥
        SecretKey key = keyGenerator.generateKey();
        return key.getEncoded();
    }

    /**
     * Aes 加密
     *
     * @param source 待加密字节数组
     * @param key    密钥
     * @return Aes加密后字节数组
     * @throws Exception 抛出异常信息
     */
    public static byte[] encryptAes(byte[] source, byte[] key) throws Exception {
        //恢复密钥
        SecretKey secretKey = new SecretKeySpec(key, AES);
        //Cipher指定算法
        Cipher cipher = Cipher.getInstance(AES);
        //Cipher初始化
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(source);
    }

    /**
     * Aes 解密
     *
     * @param source 待解密字节数组
     * @param key    密钥
     * @return Aes解密后字节数组
     * @throws Exception 抛出异常信息
     */
    public static byte[] decryptAes(byte[] source, byte[] key) throws Exception {
        //恢复密钥
        SecretKey secretKey = new SecretKeySpec(key, AES);
        //Cipher指定算法
        Cipher cipher = Cipher.getInstance(AES);
        //Cipher初始化
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(source);
    }
}
