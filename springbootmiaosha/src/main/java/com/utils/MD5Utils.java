package com.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    private final  static  String salt="1a2b3c4d";

    public  static  String md5(String str){
        return DigestUtils.md5Hex(str);
    }
    public  static  String inputPassFromPass(String inputPass){
     String str= ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }
    public  static  String fromPassToDbPass(String inputPass,String salt){
        String str= ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }


    public static void main(String[] args) {

        System.out.println(fromPassToDbPass(inputPassFromPass("123456"),"1a2b3c4d"));
    }

}
