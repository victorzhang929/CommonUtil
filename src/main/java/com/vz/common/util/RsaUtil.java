package com.vz.common.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

/**
 * Rsa非对称加密
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-04 15:41:19
 */
public final class RsaUtil {

    private static final String RSA = "RSA";

    /**
     * 初始化密钥
     *
     * @return 密钥对
     * @throws Exception 抛出异常信息
     */
    public static KeyPair initKey() throws Exception {
        //密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        //初始化
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 获取公钥
     *
     * @param keyPair 密钥对
     * @return 公钥
     */
    public static RSAPublicKey getPublicKey(KeyPair keyPair) {
        //RSA公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        return publicKey;
    }

    /**
     * 获取私钥
     *
     * @param keyPair 密钥对
     * @return 私钥
     */
    public static RSAPrivateKey getPrivateKey(KeyPair keyPair) {
        //RSA私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return privateKey;
    }

    /**
     * Rsa 加密
     *
     * @param source    待加密字节数组
     * @param publicKey 公钥
     * @return Rsa加密字节数组
     * @throws Exception 抛出异常信息
     */
    public static byte[] encryptRsa(byte[] source, RSAPublicKey publicKey) throws Exception {
        //Cipher指定算法
        Cipher cipher = Cipher.getInstance(RSA);
        //Cipher初始化
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(source);
    }

    /**
     * Rsa 解密
     *
     * @param source     待解密字节数组
     * @param privateKey 私钥
     * @return Rsa解密字节数组
     * @throws Exception 抛出异常信息
     */
    public static byte[] decryptRsa(byte[] source, RSAPrivateKey privateKey) throws Exception {
        //Cihper指定算法
        Cipher cipher = Cipher.getInstance(RSA);
        //Cipher初始化
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(source);
    }
}
