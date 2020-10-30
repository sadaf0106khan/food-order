package com.food.order.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IdUtils {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddhhmmssMs");
    public static String getUserId(){
        Date date = new Date();
        return "U" + dateFormat.format(date);
    }
}
