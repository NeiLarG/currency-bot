package com.neilarg.currencybot.util;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.TimeZone;

public final class Constants {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(ZoneId.of("+03:00")));
    }

}
