package org.tcat.frame.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lin on 2017/8/30.
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * Cron表达式 格式
     */
    public static final String CRON_FORMAT = "ss mm HH dd MM ?";

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_CN = "yyyy年MM月dd日 HH:mm:ss";
    public static final String FULL_TIMESTAMP_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String TIME_PATTERN = "HH:mm";

    private DateUtils() {
    }

    /**
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null || StringUtils.isEmptyByTrim(pattern)) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    public static void main(String[] args) {
        System.out.println(format(addDays(new Date(), 1), CRON_FORMAT));
    }

}
