package com.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
    private final static Pattern MOBILE_PATTERN=Pattern.compile("1\\d{10}");

    public  static boolean validator(String mobile){
        if (StringUtils.isBlank(mobile)){
            return  false;
        }
        Matcher matcher = MOBILE_PATTERN.matcher(mobile);
        return  matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(validator("18923412343"));
    }
}
