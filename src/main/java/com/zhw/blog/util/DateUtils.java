package com.zhw.blog.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间工具类
 */
public class DateUtils {

    public static final String DEFAULT_START = "1999-01-01 00:00:00";
    public static final String DEFAULT_END = "2999-01-01 23:59:59";
    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_HM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_ = "yyyy-MM-dd";
    public static final String FORMAT_DATE_CN = "yyyy年MM月dd日";
    public static final String FORMAT_DATETIME_CN = "yyyy年MM月dd日 HH时mm分";
    public static final String FORMAT_FULL_CN = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String FORMAT_YMD = "yyyyMMdd";
    public static final String FORMAT_YMD_HM = "yyyyMMdd HH:mm";
    public static final String FORMAT_ONLY_HM = "HH:mm";
    public static final String FORMAT_ONLY_MM = "MM";
    public static final String FORMAT_ONLY_YYYY = "yyyy";
    public static final String FORMAT_ONLY_MMdd = "MM-dd";
    public static final String FORMAT_ONLY_HH = "HH";


    /**
     * 计算两个日期之间的差距天数
     *
     * @param a 开始时间
     * @param b 结束时间
     * @return 差距的天数
     */
    public static int cutTwoDateToDay(Date a, Date b) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int theday = 0;
        try {
            Date beginDate = format.parse(format.format(a));
            Date endDate = format.parse(format.format(b));

            calendar.setTime(beginDate);
            long begin = calendar.getTimeInMillis();
            calendar.setTime(endDate);
            long end = calendar.getTimeInMillis();

            theday = (int) ((end - begin) / (86400000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return theday;
    }

    /**
     * 计算两个日期之间的差距分钟数
     *
     * @param a 开始日期
     * @param b 结束日期
     * @return 差距的分钟数
     */
    public static int cutTwoDateToMinute(Date a, Date b) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        int theday = 0;
        try {
            Date beginDate = format.parse(format.format(a));
            Date endDate = format.parse(format.format(b));

            calendar.setTime(beginDate);
            long begin = calendar.getTimeInMillis();
            calendar.setTime(endDate);
            long end = calendar.getTimeInMillis();

            theday = (int) ((end - begin) / (1000 * 60));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return theday;
    }

    /**
     * 计算两个日期之间的差距分钟数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 差距的分钟数
     */
    public static int cutTwoDateToMonth(Date beginDate, Date endDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(beginDate);
        c2.setTime(endDate);

        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year2 - year1;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month2 < month1 || month2 == month1 && day2 < day1) yearInterval--;
        // 获取月数差值
        int monthInterval = (month2 + 12) - month1;
        if (day2 < day1) monthInterval--;
        monthInterval %= 12;

        return yearInterval * 12 + monthInterval;
    }

    /**
     * 将数字型的时间转为字符串（80 -> 01:20）
     *
     * @param time
     * @return
     */
    public static String intToTimeString(int time) {
        String hour = String.valueOf(time / 60);
        String minute = String.valueOf(time - time / 60 * 60);

        if (hour.length() < 2) {
            hour = "0" + hour;
        }
        if (minute.length() < 2) {
            minute = "0" + minute;
        }
        return hour + ":" + minute;
    }

    /**
     * 取出两个时间出较大的时间
     *
     * @param a
     * @param b
     * @return
     */
    public static Date MaxDate(Date a, Date b) {
        if (a.before(b)) {
            return b;
        } else {
            return a;
        }
    }

    /**
     * 取出两个时间出较小的时间
     *
     * @param a
     * @param b
     * @return
     */
    public static Date MinDate(Date a, Date b) {
        if (a.before(b)) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * 计算给定日期是星期几
     *
     * @param date
     * @return
     */
    public static int getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0)
            w = 7;
        return w;
    }

    /**
     * 计算给定日期是星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDateUpper(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0)
            w = 7;
        String u[] = {"一", "二", "三", "四", "五", "六", "七"};
        String str = "星期" + u[w - 1];
        return str;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 标准化格式化日期时间
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_FULL);
        return format.format(date);
    }

    /**
     * 根据对象格式化日期字符串
     *
     * @param obj
     * @param pattern
     * @return
     */
    public static String formatDateFromObject(Object obj, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if (obj == null || obj.toString() == "") {
            return "";
        } else if (obj instanceof Date) {
            return format.format((Date) obj);
        } else if (obj instanceof String) {
            return format.format(createDate(obj.toString(), pattern));
        }
        return null;
    }

    /**
     * 根据给定日期字符串和日期格式 创建日期
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date createDateWithUnknowFormat(String dateString) {
        Date result = null;
        String pattern = FORMAT_DATE_CN;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            result = format.parse(dateString);
        } catch (ParseException e) {
            try {
                pattern = "yyyy/M/d";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                result = format.parse(dateString);
            } catch (ParseException e1) {
                try {
                    pattern = "yyyy-MM-dd";
                    SimpleDateFormat format = new SimpleDateFormat(pattern);
                    result = format.parse(dateString);
                } catch (ParseException e2) {

                }
            }
        }
        return result;
    }

    /**
     * 根据给定日期字符串和日期格式 创建日期
     *
     * @param dateString
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date createDate(String dateString, String pattern) {
        Date result = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            result = format.parse(dateString);
        } catch (ParseException e) {
//			e.printStackTrace();
        }
        return result;
    }

    /**
     * 正则匹配时间字符串，转换为对应格式的date对象
     */
    public static Date createDate(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        Date date = matcher(dateString, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", FORMAT_FULL);
        if (date == null)
            date = matcher(dateString, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}", FORMAT_HM);
        if (date == null)
            date = matcher(dateString, "\\d{4}-\\d{2}-\\d{2}", FORMAT_);
        if (date == null)
            date = matcher(dateString, "\\d{4}\\d{2}\\d{2} \\d{2}:\\d{2}", FORMAT_YMD_HM);
        if (date == null)
            date = matcher(dateString, "\\d{4}\\d{2}\\d{2}", FORMAT_YMD);
        if (date == null)
            date = matcher(dateString, "\\d{4}年\\d{2}月\\d{2}日 \\d{2}时\\d{2}分\\d{2}秒", FORMAT_FULL_CN);
        if (date == null)
            date = matcher(dateString, "\\d{4}年\\d{2}月\\d{2}日 \\d{2}时\\d{2}分", FORMAT_DATETIME_CN);
        if (date == null)
            date = matcher(dateString, "\\d{4}年\\d{2}月\\d{2}日", FORMAT_DATE_CN);
        if (date == null)
            date = matcher(dateString, "\\d{4}", FORMAT_ONLY_YYYY);
        return date;
    }

    /**
     * 正则匹配时间字符串
     */
    private static Date matcher(String dateString, String regex, String format) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(dateString);
        if (m.find()) {
            return createDate(dateString, format);
        }
        return null;
    }

    /**
     * 验证月份和日期是否超出范围,包括月份的天数,平年闰年
     */
    public static boolean matcherDate(String dateString) {
        String regEx = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(dateString);
        if (!matcher.matches()) return false;
        return true;
    }

    public static Date addDay(Date date, int n) {
        return org.apache.commons.lang3.time.DateUtils.addDays(date, n);
    }


    /**
     * 获取日期的前后天数
     *
     * @param oldDate 日期格式 yyyy-MM-dd
     * @param num     负数代表前几天  正数代表后几天
     * @return yyyy-MM-dd
     * @throws ParseException
     */
    public static Date getBeforeOrAfterDay(Date oldDate, Integer num) {
        try {
            if (oldDate != null && num != null) {
                DateFormat dateFormat = new SimpleDateFormat(FORMAT_);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(oldDate);
                calendar.add(Calendar.DATE, num);
                String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                String day = String.valueOf(calendar.get(Calendar.DATE));
                if (calendar.get(Calendar.MONTH) < 10) {
                    month = "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
                }
                if (calendar.get(Calendar.DATE) < 10) {
                    day = "0" + String.valueOf(calendar.get(Calendar.DATE));
                }
                String newDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-" + month + "-" + day;
                return dateFormat.parse(newDate);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期格式化
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static Date getDateFormat(String date) {
        try {
            if (StringUtils.isBlank(date)) {
                return null;
            }
            DateFormat dateFormat = new SimpleDateFormat(FORMAT_);
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期时间格式化
     *
     * @param dateTime
     * @return
     */
    public static Date getDateTimeFormat(String dateTime) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(FORMAT_FULL);
            return dateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    public static Date getDate() {
        try {
            DateFormat dateFormat = new SimpleDateFormat(FORMAT_);
            Date nowDate = dateFormat.parse(dateFormat.format(new Date()));
            return nowDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static Date getDateTime() {
        try {
            DateFormat dateFormat = new SimpleDateFormat(FORMAT_FULL);
            Date nowDate = dateFormat.parse(dateFormat.format(new Date()));
            return nowDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前日期字符串
     *
     * @return
     */
    public static String getDateString() {
        try {
            return formatDate(getDate(), FORMAT_);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateTimeString() {
        try {
            return formatDate(getDateTime(), FORMAT_FULL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算当前日期距新日期天数
     *
     * @param newDate
     * @return
     */
    public static int countDistanceDate(Date newDate) {
        try {
            if (newDate != null) {
                DateFormat dateFormat = new SimpleDateFormat(FORMAT_);
                Date nowDate = dateFormat.parse(dateFormat.format(new Date()));
                int day = cutTwoDateToDay(nowDate, newDate);
                return day;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 日期转化为大小写
    public static String dataToUpper(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return numToUpper(year) + "年" + monthToUppder(month) + "月" + dayToUppder(day) + "日";
    }

    // 将数字转化为大写
    public static String numToUpper(int num) {
        //String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        String u[] = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    // 月转化为大写
    public static String monthToUppder(int month) {
        if (month < 10) {
            return numToUpper(month);
        } else if (month == 10) {
            return "十";
        } else {
            return "十" + numToUpper(month - 10);
        }
    }

    // 日转化为大写
    public static String dayToUppder(int day) {
        if (day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十";
            } else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十" + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }

    /**
     * 比较日期是否在start和end之间
     */
    public static boolean isBetween(Date date, Date start, Date end) {
        return start.before(date) && end.after(date);
    }

    /**
     * 获取当前年yyyy
     */
    public static int getCurrentYearOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取本年年份
     */
    public static int getCurrentYear() {
        return getCurrentYearOfDate(DateUtils.getDate());
    }

    // 获取本月月份
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getDate());
        return cal.get(Calendar.MONTH) + 1;
    }

    // 获取指定年月的第一天 YYYY-MM-DD HH:mm:dd
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        return formatDate(cal.getTime());
    }

    // 获取指定年月的最后一天 YYYY-MM-DD HH:mm:dd
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 23, 59, 59);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return formatDate(cal.getTime());
    }

    public static String getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        return DateUtils.getFirstDayOfMonth(year, month);
    }

    public static String getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        return DateUtils.getLastDayOfMonth(year, month);
    }

    // 获取date这一年的第一天 yyyy-MM-dd HH:mm:ss
    public static String getYearFirstDay(String date) {
        return getCurrentYearOfDate(createDate(date)) + "-01-01 00:00:00";
    }

    // 获取date这一年的最后一天 yyyy-MM-dd HH:mm:ss
    public static String getYearLastDay(String date) {
        return getCurrentYearOfDate(createDate(date)) + "-12-31 23:59:59";
    }

    // 默认开始日期
    public static Date getDefaultStart() {
        return createDate(DEFAULT_START);
    }

    // 默认结束日期
    public static Date getDefaultEnd() {
        return createDate(DEFAULT_END);
    }

    public static Date createDateTryUnknowPattern(String value) {
        Date retDate = null;
        if (retDate == null) {
            retDate = DateUtils.createDate(value, "yyyyMMdd");
        }
        if (retDate == null) {
            retDate = DateUtils.createDate(value, "yyyyMM");
        }
        if (retDate == null) {
            retDate = DateUtils.createDate(value, "yyyy-MM-dd");
        }
        if (retDate == null) {
            retDate = DateUtils.createDate(value, "yyyy-MM");
        }
        if (retDate == null) {
            retDate = DateUtils.createDate(value, "yyyy-M");
        }
        if (retDate == null) {
            retDate = DateUtils.createDate(value, "yyyy");
        }
        if (retDate == null) {
            retDate = DateUtils.createDate(value, "yyyy/MM/dd");
        }
        if (retDate == null) {
            retDate = DateUtils.createDate(value, "yyyy/MM");
        }
        if (retDate == null) {
            retDate = DateUtils.createDate(value, "yyyy/M");
        }
        return retDate;
    }

    // 获取今日开始时间
    public static Date getTodayStart() {
        return DateUtils.createDate(DateUtils.formatDate(DateUtils.getDate(), DateUtils.FORMAT_) + " 00:00:00");
    }

    // 获取今日结束时间
    public static Date getTodayEnd() {
        return DateUtils.createDate(DateUtils.formatDate(DateUtils.getDate(), DateUtils.FORMAT_) + " 23:59:59");
    }

    /**
     * 获取今日指定时刻
     *
     * @param suffixTime 时刻后缀
     * @return 今日指定时刻，日期类型返回
     * @author zenglin
     */
    public static Date getTodayTime(String suffixTime) {
        return DateUtils.createDate(DateUtils.formatDate(DateUtils.getDate(), DateUtils.FORMAT_) + " " + suffixTime);
    }

    /**
     * 获取昨日某时刻时间
     *
     * @param suffixTime 时刻后缀
     * @return 昨日指定时刻，日期类型返回
     * @auhtor zenglin
     */
    public static Date getLastDayTime(String suffixTime) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DATE, ca.get(Calendar.DAY_OF_MONTH) - 1);
        String lastDay = DateUtils.formatDate(ca.getTime(), DateUtils.FORMAT_) + " " + suffixTime;
        return DateUtils.createDate(lastDay);
    }


    public static void main(String[] args) {
        System.out.println(getCurrentMonth());

    }
}