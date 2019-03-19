package com.vz.common.util.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Aes对称加密
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-04 15:32:12
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AesUtil {

    private static final String AES = "AES";
    /**
     * 初始化密钥生成器
     * @return 密钥
     * @throws NoSuchAlgorithmException 找不到该加密算法异常
     */
    public static byte[] initKey() throws NoSuchAlgorithmException {
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
     * @param source 待加密字节数组
     * @param key    密钥
     * @return Aes加密后字节数组
     * @throws NoSuchAlgorithmException 找不到该加密算法
     * @throws NoSuchPaddingException 找不到加密长度
     * @throws InvalidKeyException 密钥不可用
     * @throws IllegalBlockSizeException 非法代码块大小
     * @throws BadPaddingException 代码块错误
     */
    public static byte[] encrypt(byte[] source, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
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
     * @param source 待解密字节数组
     * @param key 密钥
     * @return Aes解密后字节数组
     * @throws NoSuchAlgorithmException 找不到该加密算法
     * @throws NoSuchPaddingException 找不到加密长度
     * @throws InvalidKeyException 密钥不可用
     * @throws IllegalBlockSizeException 非法代码块大小
     * @throws BadPaddingException 代码块错误
     */
    public static byte[] decrypt(byte[] source, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //恢复密钥
        SecretKey secretKey = new SecretKeySpec(key, AES);
        //Cipher指定算法
        Cipher cipher = Cipher.getInstance(AES);
        //Cipher初始化
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(source);
    }
}
