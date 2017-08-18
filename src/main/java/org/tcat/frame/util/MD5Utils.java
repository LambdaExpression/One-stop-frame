package org.tcat.frame.util;

import java.security.MessageDigest;

/**
 * MD5加密 工具
 *
 * @author liangzhicong
 */
public class MD5Utils {

    /**
     * 加密
     *
     * @param str 字符串
     * @return 加密结果
     */
    public static String md5(String str) {
        if (StringUtils.isEmptyByTrim(str))
            return "";

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
            return "";
        }

        byte[] bytes = messageDigest.digest();
        StringBuffer md5Bufffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            if (Integer.toHexString(0xFF & bytes[i]).length() == 1)
                md5Bufffer.append("0").append(Integer.toHexString(0xFF & bytes[i]));
            else
                md5Bufffer.append(Integer.toHexString(0xFF & bytes[i]));
        }
        return md5Bufffer.toString();
    }

    /**
     * 判断文本和加密的文本是否一致（2015-9-8 上午11:32:56，liangzhicong）
     *
     * @param rawCode 未加密字符串
     * @param enCode  加密后字符串
     * @return ture/false
     */
    public static boolean match(String rawCode, String enCode) {
        if (StringUtils.isEmptyByTrim(rawCode) || StringUtils.isEmptyByTrim(enCode)) {
            return enCode.equals(md5(rawCode));
        }
        return false;
    }

    public static void main(String[] args) {
        String str = MD5Utils.md5("123456");
        System.out.println("加密=" + str);
        System.out.println("判断=" + MD5Utils.match("123456", str));
    }
}
