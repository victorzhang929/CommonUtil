package com.vz.common.util.encrypt;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Md5WithRsa数字签名
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-04 16:37:29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HashRsaSignatureUtil {

    private static final String RSA = "RSA";

    /**
     * 签发数字签名
     * @param algorithm  Hash加密算法（MD5/SHA1）
     * @param content    待加密内容
     * @param privateKey 私钥
     * @return 数字签名字节数组
     * @throws NoSuchAlgorithmException 找不到该加密算法
     * @throws NoSuchPaddingException 找不到加密长度
     * @throws InvalidKeyException 密钥不可用
     * @throws BadPaddingException 加密长度错误
     * @throws IllegalBlockSizeException 非法代码块大小
     */
    public static byte[] sign(String algorithm, byte[] content, PrivateKey privateKey) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        md5.update(content);
        byte[] bytes = md5.digest();

        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(bytes);
    }

    /**
     * 验证数字签名
     * @param algorithm Hash加密算法（MD5/SHA1）
     * @param content   待解密内容
     * @param sign      数字签名内容
     * @param publicKey 公钥
     * @return 验证是否成功
     * @throws NoSuchAlgorithmException 找不到该加密算法
     * @throws NoSuchPaddingException 找不到加密长度
     * @throws InvalidKeyException 密钥不可用
     * @throws BadPaddingException 加密长度错误
     * @throws IllegalBlockSizeException 非法代码块大小
     */
    public static boolean verify(String algorithm, byte[] content, byte[] sign, PublicKey publicKey) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        md5.update(content);
        byte[] bytes = md5.digest();

        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptBytes = cipher.doFinal(sign);

        if (Arrays.equals(bytes, decryptBytes)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 数字签名
     * @param algorithm  签名算法（MD5withRSA/SHA1withRSA）
     * @param content    待签名内容
     * @param privateKey 私钥
     * @return 数字签名字节数组
     * @throws NoSuchAlgorithmException 找不到该加密算法
     * @throws InvalidKeyException 密钥不可用
     * @throws SignatureException 签名异常
     */
    public static byte[] signWithSignature(String algorithm, byte[] content, PrivateKey privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //指定签名算法
        Signature signature = Signature.getInstance(algorithm);
        //私钥初始化签名
        signature.initSign(privateKey);
        signature.update(content);
        return signature.sign();
    }

    /**
     * 数字签名验证
     * @param algorithm 签名算法（MD5withRSA/SHA1withRSA）
     * @param content   待验证内容
     * @param sign      数字签名标识
     * @param publicKey 公钥
     * @return 验证结果（True/False）
     * @throws NoSuchAlgorithmException 找不到该加密算法
     * @throws InvalidKeyException 密钥不可用
     * @throws SignatureException 签名异常
     */
    public static boolean verifyWithSignature(String algorithm, byte[] content, byte[] sign, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //指定验证算法
        Signature signature = Signature.getInstance(algorithm);
        //公钥初始化签名
        signature.initVerify(publicKey);
        signature.verify(content);
        return signature.verify(sign);
    }
}
