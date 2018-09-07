package com.vz.common.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间处理工具类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-05-21 11:53:01
 */
public final class DateUtil {
    /**
     * 获取YMMDDHHMMSS格式时间戳
     *
     * @return
     */
    public static String getTimestamp() {
        return getTimestamp(new Date());
    }

    /**
     * 获取YMMDDHHMMSS格式时间戳
     *
     * @param date 时间
     * @return 返回相应格式时间字符串
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
}
