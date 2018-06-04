package com.victorzhang.common.util;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;

import javax.crypto.Cipher;

import static com.victorzhang.common.constant.EncryptConstant.RSA;

/**
 * Md5WithRsa数字签名
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-04 16:37:29
 */
public class HashSignatureUtil {

    /**
     * 数字签名
     *
     * @param algorithm  Hash加密算法（MD5/SHA1）
     * @param content    待加密内容
     * @param privateKey 私钥
     * @return 数字签名字节数组
     * @throws Exception
     */
    public static byte[] sign(String algorithm, byte[] content, PrivateKey privateKey) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        md5.update(content);
        byte[] bytes = md5.digest();

        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(bytes);
    }

    /**
     * 验证数字签名
     *
     * @param algorithm Hash加密算法（MD5/SHA1）
     * @param content   待解密内容
     * @param sign      数字签名内容
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static boolean verify(String algorithm, byte[] content, byte[] sign, PublicKey publicKey) throws Exception {
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
     *
     * @param algorithm  签名算法（MD5withRSA/SHA1withRSA）
     * @param content    待签名内容
     * @param privateKey 私钥
     * @return 数字签名字节数组
     * @throws Exception
     */
    public static byte[] signWithSignature(String algorithm, byte[] content, PrivateKey privateKey) throws Exception {
        //指定签名算法
        Signature signature = Signature.getInstance(algorithm);
        //私钥初始化签名
        signature.initSign(privateKey);
        signature.update(content);
        return signature.sign();
    }

    /**
     * 数字签名验证
     *
     * @param algorithm 签名算法（MD5withRSA/SHA1withRSA）
     * @param content   待验证内容
     * @param sign      数字签名标识
     * @param publicKey 公钥
     * @return 验证结果（True/False）
     * @throws Exception
     */
    public static boolean verifyWithSignature(String algorithm, byte[] content, byte[] sign, PublicKey publicKey) throws Exception {
        //指定验证算法
        Signature signature = Signature.getInstance(algorithm);
        //公钥初始化签名
        signature.initVerify(publicKey);
        signature.verify(content);
        return signature.verify(sign);
    }
}
