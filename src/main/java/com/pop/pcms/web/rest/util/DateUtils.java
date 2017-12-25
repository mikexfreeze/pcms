package com.pop.pcms.web.rest.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期工具类
 * Created by zhangjinye on 2017/3/7.
 */
public class DateUtils {

    /**
     * java.util.Date转为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDate dateToLocalDate(Date date) {
        return dateToLocalDateTime(date).toLocalDate();
    }

    /**
     * java.sql.Date转为LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate sqlDataToLocalDate(java.sql.Date date) {
        java.util.Date utilDate = new java.sql.Date(date.getTime());
        return DateUtils.dateToLocalDate(utilDate);
    }

    /**
     * java.sql.Date转为LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate asLocalDate(java.sql.Date date) {
        if (date == null) {
            return null;
        }
        ZoneId zone = ZoneId.systemDefault();
        return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
    }


}
