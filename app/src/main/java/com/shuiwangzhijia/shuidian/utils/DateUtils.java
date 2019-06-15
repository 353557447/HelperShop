package com.shuiwangzhijia.shuidian.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangsuli on 2017/8/9.
 */

public class DateUtils {

    /**
     * 返回给定时间搓。
     *
     * @param date
     */
    public static Long getTime(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date da = null;
        try {
            da = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return da.getTime();
    }

    /**
     * 返回当前时间字符串。
     * <p>
     * 格式：yyyy-MM-dd
     *
     * @return String 指定格式的日期字符串.
     */
    public static String getCurrentDate() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 根据给定的格式与时间(Date类型的)，返回时间字符串<br>
     *
     * @param date   指定的日期
     * @param format 日期格式字符串
     * @return String 指定格式的日期字符串.
     */
    public static String getFormatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 根据给定的格式与时间(Date类型的)，返回时间字符串
     *
     * @param date 指定的日期
     * @return String 指定格式的日期字符串.
     */
    public static String getFormatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getFormatDateStr(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(time));
    }

    public static Date getDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date mDate = null;
        try {
            mDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }

    //获取 年月日
    public static String getYMDTime(long time) {
        String brith_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        brith_StrTime = sdf.format(new Date(time*1000));
        return brith_StrTime;
    }

    //获取 月日
    public static String getMDTime(long time) {
        String brith_StrTime = null;
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("MM-dd");
        brith_StrTime = sdf.format(new Date(time));
        return brith_StrTime;
    }

    /**
     * 获取month后秒值
     *
     * @param month
     * @return
     */
    public static long addMonthDateTime(long date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date));
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime().getTime();
    }

    public static long addHoursDateTime(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime().getTime();
    }

    /**
     * 增加days天
     *
     * @param days
     * @return
     */
    public static String addDaysDateTime(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return getFormatDateTime(calendar.getTime());
    }

    /**
     * 增加days天
     *
     * @param days
     * @return
     */
    public static Date getDateTime(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static String getWeekandTime(Date date) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        str = sdf.format(date);
        sdf = new SimpleDateFormat("HH:mm");
        str = str + "   " + sdf.format(date);
        return str;
    }

    /**
     * 获取星期的数字角标
     *
     * @return
     */
    public static int getWeekIndex() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取时间戳
     *
     * @param yymmdd
     * @param hhmm
     * @return
     */
    public static long getTimeRubbing(String yymmdd, String hhmm) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdr.parse(yymmdd + " " + hhmm);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 转化
     *
     * @param yymmdd
     * @param hhmm
     * @return
     */
    public static Date parseDate(String yymmdd, String hhmm) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdr.parse(yymmdd + " " + hhmm);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 返回文字描述的日期
     *
     * @param time 时间戳  秒
     * @return String类型的文字描述
     */
    public static String getTimeFormatText(Long time) {
        if (time == null) {
            return "";
        }
        final long second = 1;// 1秒
        final long minute = 60 * second;// 1分钟
        final long hour = 60 * minute;// 1小时
        final long day = 24 * hour;// 1天
        final long month = 31 * day;// 月
        final long year = 12 * month;// 年
        //当前时间
        long newTime = (long) (System.currentTimeMillis() / 1000);
        //相差时间
        long diff = newTime - time;
        if (diff >= hour && diff < day) {
            return (diff / hour) + "小时前";
        } else if (diff >= minute && diff < hour) {
            return (diff / minute) + "分钟前";
        } else if (diff >= day) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return formatter.format(Long.valueOf(time + "000"));
        }
        return "刚刚";
    }

    //获取 月日（周一）
    public static String getCustomDateWithWeek(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        long time = calendar.getTime().getTime();
        String dateStr = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        dateStr = sdf.format(new Date(time));
        return dateStr + "( 周" + getWeek(time) + " )";
    }

    public static String getWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        int weekId = calendar.get(Calendar.DAY_OF_WEEK);
        String week = "";
        switch (weekId) {
            case 1:
                week = "日";
                break;
            case 2:
                week = "一";
                break;
            case 3:
                week = "二";
                break;
            case 4:
                week = "三";
                break;
            case 5:
                week = "四";
                break;
            case 6:
                week = "五";
                break;
            case 7:
                week = "六";
                break;
        }
        return week;
    }

}
