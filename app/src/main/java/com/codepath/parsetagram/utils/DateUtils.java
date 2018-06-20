package com.codepath.parsetagram.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    public static String getFormattedTime(Date date) {
        return new SimpleDateFormat("MM/dd/yyyy @ HH:mm z").format(date);
    }
}
