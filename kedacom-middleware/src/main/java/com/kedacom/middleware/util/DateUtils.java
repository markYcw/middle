package com.kedacom.middleware.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;


/**
 * @author ycw
 * @date 2021/10/13 11:13
 */
public class DateUtils {

    // ==格式到年月日 时分秒==
    /**
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808
     */
    public final static String stringDate = "yyyyMMddHHmmss";

    public final static String defaultDate = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取精确到秒的时间戳
     *
     * @param date
     * @return
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    public static String getDateString(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(stringDate);
        return format.format(date);
    }

    public static String getDefaultDate(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(defaultDate);
        return format.format(date);
    }

    /**
     * 将2021-04-05T13:08:22+08:00转成date
     *
     * @param oldDateStr
     * @return
     */
    public static Date dealDateFormat(String oldDateStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        try {
            Date date = df.parse(oldDateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ISO8601格式的时间转普通时间
     *
     * @param ISOdate
     * @return
     */
    public static String getDateStrFromISO8601Timestamp(String ISOdate) {
        if (ISOdate.equals("")) {
            return "";
        }
        Date date = dealDateFormat(ISOdate);
        String defaultDate = getDefaultDate(date);
        return defaultDate;
    }

    /**
     * 普通时间转ISO8601格式的时间
     *
     * @param timestamp
     * @return
     */
    public static String getISO8601TimestampFromDateStr(String timestamp) {
        if (timestamp.equals("")) {
            return "";
        }
        java.time.format.DateTimeFormatter dtf1 = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(timestamp, dtf1);
        ZoneOffset offset = ZoneOffset.of("+08:00");
        OffsetDateTime date = OffsetDateTime.of(ldt, offset);
        java.time.format.DateTimeFormatter dtf2 = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return date.format(dtf2);
    }


}
