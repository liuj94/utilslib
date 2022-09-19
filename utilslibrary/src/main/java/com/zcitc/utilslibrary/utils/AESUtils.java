package com.zcitc.utilslibrary.utils;

import android.util.Base64;

import com.blankj.utilcode.util.LogUtils;


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import javax.crypto.NoSuchPaddingException;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESUtils {

//    private static final String TAG = AESUtils.class.getSimpleName();

    /**
     * 采用AES加密算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 字符编码(用哪个都可以，要注意new String()默认使用UTF-8编码 getBytes()默认使用ISO8859-1编码)
     */
    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES 加密
     *
     * @param secretKey 加密密码，长度：16 或 32 个字符
     * @param data      待加密内容
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String secretKey, String data) {
        try {
            // 创建AES秘钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(CHARSET_UTF8), KEY_ALGORITHM);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化加密器
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptByte = cipher.doFinal(data.getBytes(CHARSET_UTF8));
            // 将加密以后的数据进行 Base64 编码
            return base64Encode(encryptByte);
        } catch (Exception e) {
            handleException("encrypt", e);
        }
        return null;
    }

    /**
     * AES 解密
     *
     * @param secretKey  解密的密钥，长度：16 或 32 个字符
     * @param base64Data 加密的密文 Base64 字符串
     */
    public static String decrypt(String secretKey, String base64Data) {
        try {
            byte[] data = base64Decode(base64Data);
            // 创建AES秘钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(CHARSET_UTF8), KEY_ALGORITHM);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化解密器
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            // 执行解密操作
            byte[] result = cipher.doFinal(data);
            return new String(result, CHARSET_UTF8);
        } catch (Exception e) {
            handleException("decrypt", e);
        }
        return null;
    }

    /**
     * 将 字节数组 转换成 Base64 编码
     * 用Base64.DEFAULT模式会导致加密的text下面多一行（在应用中显示是这样）
     */
    public static String base64Encode(byte[] data) {
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

    /**
     * 将 Base64 字符串 解码成 字节数组
     */
    public static byte[] base64Decode(String data) {
        return Base64.decode(data, Base64.NO_WRAP);
    }

    /**
     * 处理异常
     */
    private static void handleException(String methodName, Exception e) {
        e.printStackTrace();
    }


    private final static String IV = "CLOUfHOwSE"; //偏移量
    private  static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private  static final String AES = "AES";//AES 加密

    public static final String password = "CLOdDHOUSs"; //密钥

    /**
     * 加密
     * @param cleartext 需加密字段
     * @return
     */
    public static String encrypt(String cleartext) {
        return encrypt(cleartext.getBytes());
    }

    /**
     * 加密
     * @param cleartext 需加密字段
     * @return
     */
    public static String encrypt(byte[] cleartext){

//        String cryptoKey = Sha256.getSHA256(password);
//        String cryptoIV = getMD5(IV);
        IvParameterSpec zeroIv = new IvParameterSpec(getMD5(IV));
        SecretKeySpec key = new SecretKeySpec(getSHA256(password), AES);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(cleartext);
            return new String(Base64.encode(encryptedData,Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            LogUtils.e(e);
        } catch (NoSuchPaddingException e) {
            LogUtils.e(e);
        } catch (BadPaddingException e) {
            LogUtils.e(e);
        } catch (InvalidKeyException e) {
            LogUtils.e(e);
        } catch (IllegalBlockSizeException e) {
            LogUtils.e(e);
        } catch (InvalidAlgorithmParameterException e) {
            LogUtils.e(e);
        }
        return null;
    }
    public static byte[] getSHA256(String str){
        MessageDigest messageDigest;
        byte[] encodestr = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr =messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }
    public static byte[] getMD5(String message) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] input = message.getBytes();

            byte[] buff = md.digest(input);
            return buff;
//            md5str = bytesToHex(buff, upperCase);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param encrypted 密文
     * @return
     */
    public static String decrypt(String encrypted){
        byte[] byteMi = Base64.decode(encrypted,Base64.DEFAULT);
        IvParameterSpec zeroIv = new IvParameterSpec(IV.getBytes());
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte[] decryptedData = cipher.doFinal(byteMi);
            return new String(decryptedData);
        } catch (NoSuchAlgorithmException e) {
            LogUtils.e(e);
        } catch (NoSuchPaddingException e) {
            LogUtils.e(e);
        } catch (BadPaddingException e) {
            LogUtils.e(e);
        } catch (InvalidKeyException e) {
            LogUtils.e(e);
        } catch (IllegalBlockSizeException e) {
            LogUtils.e(e);
        } catch (InvalidAlgorithmParameterException e) {
            LogUtils.e(e);
        }
        return null;
    }



}
