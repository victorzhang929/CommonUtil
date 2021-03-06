package com.vz.common.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * DES对称加密
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-04 15:19:16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DesUtil {

    private static final String DES = "DES";
    /**
     * 生成密钥
     * @return 密钥
     * @throws Exception 抛出异常信息
     */
    public static byte[] initKey() throws Exception {
        //密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
        //初始化密钥生成器
        keyGenerator.init(56);
        //生成密钥
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * DES加密
     * @param source 待加密字节数组
     * @param key    密钥
     * @return DES加密字节数组
     * @throws Exception 抛出异常信息
     */
    public static byte[] encryptDes(byte[] source, byte[] key) throws Exception {
        //获取密钥
        SecretKey secretKey = new SecretKeySpec(key, DES);
        //Cipher指定算法
        Cipher cipher = Cipher.getInstance(DES);
        //初始化Cipher
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(source);
    }

    /**
     * DES解密
     * @param source 待解密字节数组
     * @param key    密钥
     * @return DES解密字节数组
     * @throws Exception 抛出异常信息
     */
    public static byte[] decryptDes(byte[] source, byte[] key) throws Exception {
        //获取密钥
        SecretKey secretKey = new SecretKeySpec(key, DES);
        //Cipher指定算法
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(source);
    }

}
