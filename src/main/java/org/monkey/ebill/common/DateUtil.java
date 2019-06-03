package org.monkey.ebill.common;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 时间校验工具类
 */
public class DateUtil extends DateUtils{

    // 日期规则定义
    public static final String[] DATE_PATTERN = new String[]{
            "yyyyMMdd", //编号0
            "yyyyMMddHHmmss", //编号1
            "yyyyMMddHHmm", //编号2
            "yyyy-MM-dd HH:mm:ss", //编号3
            "yyyyMMdd HH:mm", //编号4
            "yyyy-MM-dd", //编号5
            "mm:ss",//编号6
            "yyyy-MM-dd HH:mm"//编号7
    };

    // 日期格式定义
    private static final Map<Pattern, String> PATTERN_MAP = getPatternMap();

    /**
     * 将日期yyyy-MM-dd字符串解析为日期对象, 解析失败则返回null
     * 注意! 此方法会吞掉异常!
     *
     * @param date 日期字符串
     * @return 日期实例或null
     */
    public static Date parseDate(String date) {
        try {
            return parseDate(date, 5);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     *
     * @param date
     * @param index
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date, int index) throws ParseException {
        return parseDate(date, DATE_PATTERN[index]);
    }

    /**
     *
     * 获取指定日期时间的日期，不要时间. 如yyyy-MM-dd 00:00:00.000
     * @param date
     * @return yyyy-MM-dd 00:00:00.000
     */
    public static Date dateValue(Date date) {
        Calendar result = Calendar.getInstance();
        result.setTime(date);
        return getDateFromCalendar(result);
    }

    /**
     * 获取指定日期时间的日期，不要时间 如yyyy-MM-dd 00:00:00.000
     * @param time
     * @return yyyy-MM-dd 00:00:00.000
     */
    public static Date dateValue(long time) {
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(time);
        return getDateFromCalendar(result);
    }

    public static Date getDateFromCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }



    /**
     * 日期格式
     * @return Map<Pattern, String>
     */
    private static Map<Pattern, String> getPatternMap() {
        Map<Pattern, String> map = new ConcurrentHashMap<Pattern, String>();

        map.put(Pattern.compile("^\\d{8}$"), "yyyyMMdd");
        map.put(Pattern.compile("^\\d{14}$"), "yyyyMMddHHmmss");
        map.put(Pattern.compile("^\\d{12}$"), "yyyyMMddHHmm");
        map.put(Pattern.compile("^\\d{10}$"), "yyyyMMddHH");
        map.put(Pattern.compile("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$"), "y-M-d H:m:s.S");
        map.put(Pattern.compile("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$"), "y-M-d H:m:s");
        map.put(Pattern.compile("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}$"), "y-M-d H:m");
        map.put(Pattern.compile("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}$"), "y-M-d H");
        map.put(Pattern.compile("^\\d{4}\\-\\d{1,2}\\-\\d{1,2}$"), "y-M-d");
        map.put(Pattern.compile("^\\d{2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$"), "H:m:s.S");
        map.put(Pattern.compile("^\\d{2}:\\d{1,2}:\\d{1,2}$"), "H:m:s");
        map.put(Pattern.compile("^\\d{2}:\\d{1,2}$"), "H:m");

        return map;
    }

    /**
     * 比较两个时间的日期大小
     * @param time1
     * @param time2
     * @return true: time1 > time2, others are false
     */
    public static boolean compareDate(long time1, long time2) {
        Date date1 = dateValue(time1);
        Date date2 = dateValue(time2);
        return compare(date1, date2);
    }

    /**
     * 比较两个日期对象的大小
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compare(Date date1, Date date2) {
        return date1.getTime() > date2.getTime();
    }
}
