package com.test;

public class TestString {
    public static void main(String[] args) {
         String sb="123456654%s";
       /* System.out.println(sb.charAt(2));
        System.out.println(sb.isEmpty()+";"+"".isEmpty());
        System.out.println(sb.replace("5","a"));
        char[] chars = sb.toCharArray();
        char[] chars2 = sb.toCharArray();
        System.arraycopy(chars,0,chars2,0,chars.length);
        System.out.println(chars);*/
        String s = String.format(sb, "哈哈哈");
        System.out.println(s);

    }
}
