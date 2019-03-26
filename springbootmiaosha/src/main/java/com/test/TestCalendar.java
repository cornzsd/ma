package com.test;

import java.util.Calendar;

public class TestCalendar {
    public static void main(String[] args) {
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        System.out.println(calendar.get(Calendar.YEAR)+"-"+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE) );
        int value = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, value);
        System.out.println(calendar.get(Calendar.YEAR)+"-"+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE) );
    }
}
