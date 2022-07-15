package com.example.ct_wechat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Utils {


    public static final String TOKEN="aabbccdd";    //TOKEN就是自己填写的

    /*
     * 验证签名
     * */
    public static boolean check(String timestamp, String nonce, String signature) {

        //1、将token、timestamp、nonce进行字典排序
        String[] strs = new String[]{TOKEN, timestamp, nonce};
        Arrays.sort(strs);

        //2、将三个参数字符串拼接成一个字符串进行sha1加密
        String str = strs[0] + strs[1] + strs[2];
        String mysignature = sha1(str);

        //返回对比结果
        return mysignature.equalsIgnoreCase(signature);
    }

    /*
     * 加密方法
     * */
    private static String sha1(String src) {

        try {
            //获取一个加密对象
            MessageDigest md = MessageDigest.getInstance("sha1");
            //加密
            byte[] digest = md.digest(src.getBytes());
            char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            StringBuffer sb = new StringBuffer();
            //处理结果集
            for (byte b : digest) {
                sb.append(chars[(b >> 4) & 15]);
                sb.append(chars[b & 15]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}