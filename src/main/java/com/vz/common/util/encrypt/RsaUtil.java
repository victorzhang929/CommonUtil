package com.vz.common.util.encrypt;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Rsa非对称加密
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-04 15:41:19
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RsaUtil {

    private static final String RSA = "RSA";

    /**
     * 初始化密钥
     * @return 密钥对，私钥公钥
     * @throws NoSuchAlgorithmException 找不到该加密算法
     */
    public static KeyPair initKey() throws NoSuchAlgorithmException {
        //密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        //初始化
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 获取公钥
     * @param keyPair 密钥对
     * @return 公钥
     */
    public static RSAPublicKey getPublicKey(KeyPair keyPair) {
        //RSA公钥
        return (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * 获取私钥
     * @param keyPair 密钥对
     * @return 私钥
     */
    public static RSAPrivateKey getPrivateKey(KeyPair keyPair) {
        //RSA私钥
        return (RSAPrivateKey) keyPair.getPrivate();
    }

    /**
     * Rsa 加密
     * @param source    待加密字节数组
     * @param publicKey 公钥
     * @return Rsa加密字节数组
     * @throws NoSuchAlgorithmException 找不到该加密算法
     * @throws NoSuchPaddingException 找不到加密长度
     * @throws InvalidKeyException 密钥不可用
     * @throws BadPaddingException 加密长度错误
     * @throws IllegalBlockSizeException 非法代码块大小
     */
    public static byte[] encrypt(byte[] source, RSAPublicKey publicKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //Cipher指定算法
        Cipher cipher = Cipher.getInstance(RSA);
        //Cipher初始化
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(source);
    }

    /**
     * Rsa 解密
     * @param source     待解密字节数组
     * @param privateKey 私钥
     * @return Rsa解密字节数组
     * @throws NoSuchAlgorithmException 找不到该加密算法
     * @throws NoSuchPaddingException 找不到加密长度
     * @throws InvalidKeyException 密钥不可用
     * @throws BadPaddingException 加密长度错误
     * @throws IllegalBlockSizeException 非法代码块大小
     */
    public static byte[] decrypt(byte[] source, RSAPrivateKey privateKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //Cihper指定算法
        Cipher cipher = Cipher.getInstance(RSA);
        //Cipher初始化
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(source);
    }
}
