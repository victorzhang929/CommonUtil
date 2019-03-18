package com.vz.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 时间处理工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-05-21 11:53:01
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtil {

    /**
     * 获取当前YYYY格式年份
     * @return 年份
     */
    public static String getYear() {
        return getYear(new Date());
    }

    /**
     * 获取YYYY格式年份
     * @param date 日期
     * @return 年份
     */
    public static String getYear(Date date) {
        return formatDate(date, "yyyy");
    }

    /**
     * 获取YYYY-MM-DD日期
     * @return yyyy-MM-dd日期
     */
    public static String getDay() {
        return getDay(new Date());
    }

    /**
     * 获取YYYY-MM-DD日期
     * @param date 日期
     * @return yyyy-MM-dd日期
     */
    public static String getDay(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     *  获取yyyyMMdd日期
     * @return yyyyMMdd日期
     */
    public static String getDays() {
        return getDays(new Date());
    }

    /**
     * 获取yyyyMMdd日期
     * @param date 日期
     * @return yyyyMMdd日期
     */
    public static String getDays(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * @return YYYY-MM-DD HH:mm:ss
     */
    public static String getTime() {
        return getTime(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * @param date 日期
     * @return YYYY-MM-DD HH:mm:ss
     */
    public static String getTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取YMMDDHHMMSS格式时间戳
     * @return YMMDDHHMMSS
     */
    public static String getTimestamp() {
        return getTimestamp(new Date());
    }

    /**
     * 获取YMMDDHHMMSS格式时间戳
     * @param date 日期
     * @return YMMDDHHMMSS
     */
    public static String getTimestamp(Date date) {
        return formatDate(date, "yyyyMMddHHmmss");
    }

    private static String formatDate(Date date, String pattern) {
        String formatDate;
        if (StringUtils.isNotBlank(pattern)) {
            formatDate = DateFormatUtils.format(date, pattern);
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 日期比较，如果oneDate>=anotherDate，返回true,否则false
     * @param oneDate 第一个日期
     * @param anotherDate 另一个日期
     * @return 第一个日期是否大于另一个日期
     */
    public static boolean compareDate(String oneDate, String anotherDate) {
        if (null == parseDate(oneDate) || null == parseDate(anotherDate)) {
            return false;
        }
        return parseDate(oneDate).getTime() >= parseDate(anotherDate).getTime();
    }

    /**
     * 格式化日期yyyy-MM-dd
     * @param date 日期
     * @return yyyy-MM-dd
     */
    public static Date parseDate(String date) {
        return parse(date, "yyyy-MM-dd");
    }

    /**
     * 格式化日期yyyy-MM-dd HH:mm:ss
     * @param date 日期
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static Date parseTime(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    private static Date parse(String date, String pattern) {
        try {
            return DateUtils.parseDate(date,pattern);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按照pattern进行格式化日期
     * @param date 日期
     * @param pattern 格式
     * @return pattern格式日期
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 日期格式转换为Timestamp
     * @param date 日期
     * @return Timestamp
     */
    public static Timestamp format(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 验证日期是否合法
     * @param date 日期
     * @return 是否合法
     */
    public static boolean isValidDate(String date) {
        return isValidDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 验证日期是否合法
     * @param date 日期
     * @param pattern 格式
     * @return 格式日期是否合法
     */
    public static boolean isValidDate(String date, String pattern) {
        return parse(date, pattern) != null;
    }
}
