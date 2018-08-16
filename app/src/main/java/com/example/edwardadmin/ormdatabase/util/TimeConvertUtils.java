package com.example.edwardadmin.ormdatabase.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间展示样式转换工具类
 *
 * @author xiaoming
 * @version 创建时间：2014-03-07
 */
@SuppressLint("SimpleDateFormat")
public class TimeConvertUtils {

    /**
     * 转换成昨天、几小时前等格式
     */
    public static String formatDate1(long t) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String systemTime = sdf.format(new Date());
        String time = sdf.format(t);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        String yesterday = sdf.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, -1);
        String yesterday_before = sdf.format(c.getTime());

        try {
            Date begin = sdf.parse(time);
            Date end = sdf.parse(systemTime);
            long between = (end.getTime() - begin.getTime()) / (1000 * 60);
            if (time.substring(0, 10).equals(yesterday.substring(0, 10))) {
                strDate = "昨天";
            } else if (time.substring(0, 10).equals(
                    yesterday_before.substring(0, 10))) {
                strDate = "前天";
            } else if (between <= 0) {
                strDate = "1分钟前";
            } else if (between < 60 && between > 0) {
                strDate = Math.round(between) + "分钟前";
            } else if (between >= 60 && between < 60 * 24) {
                strDate = Math.round(between / 60) + "小时前";
            } else {
                strDate = time.substring(0, 10);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 日期转换格式：yyyy-MM-dd
     *
     * @param t long型时间
     * @return 2014-03-10
     */
    public static String formatDate2(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(t);
    }

    /**
     * 日期转换格式：yyyy年MM月dd日
     *
     * @param t long型时间
     * @return 2014年03月10日
     */
    public static String formatDate3(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(t);
    }

    /**
     * 日期转换格式：yyyy-MM-dd HH:mm:ss
     *
     * @param t long型时间
     * @return 2014-03-10 09:01:01
     */
    public static String formatDate4(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(t);
    }

    /**
     * 日期转换格式：yyyy年MM月dd日 HH:mm
     *
     * @param t long型时间
     * @return 2014年03月10日11:57
     */
    public static String formatDate5(long t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sdf.format(t);
    }

    /**
     * 日期转换格式：yyyy-MM-dd HH:mm:ss
     *
     * @param time long型时间
     * @return 2014-03-10 09:01:01
     */
    public static String formatDate6(String time) {
        if(time.equals("null")||time.equals("")){
            time = Long.toString(new Date().getTime());
        }else{
            time = time + "000";
        }
        long t = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(t);
    }

    /**
     * string类型转换为date类型
     * <br>ps：strTime的时间格式必须是yyyy-MM-dd HH:mm:ss
     *
     * @param strTime 要转换的string类型的时间
     * @return date类型
     */
    public static Date stringToDate(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.parse(strTime);
    }

    /**
     * string类型转换为long类型
     * <br>ps：strTime的时间格式必须是yyyy-MM-dd HH:mm:ss
     *
     * @throws Exception
     */
    public static long stringToLong(String strTime) throws Exception {
        Date date = stringToDate(strTime); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            return dateToLong(date);
        }
    }

    /**
     * date类型转换为long类型
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 新浪微博日期转换为long
     *
     * @param strDate 时间字符串
     * @throws Exception
     */
    public static long formatTime(String strDate) throws Exception {
        long longTime = 0;
        if ("".equals(strDate)) {
            return longTime;
        }
        String year = "";
        String month = "";
        String day = "";
        String time = "";

        year = strDate.substring(strDate.length() - 4, strDate.length());
        month = strDate.substring(4, 7);
        month = formatMonth(month);

        day = strDate.substring(8, 10);
        time = strDate.substring(11, 19);

        String simpleDate = year + "-" + month + "-" + day + " " + time;
        longTime = stringToLong(simpleDate);
        return longTime;

    }

    /**
     * formatMonth:(这里用一句话描述这个方法的作用).
     *
     * @param month .
     * @return 设定参数 .
     */
    public static String formatMonth(String month) {
        if ("Jan".equals(month)) {
            month = "01";
        } else if ("Feb".equals(month)) {
            month = "02";
        } else if ("Mar".equals(month)) {
            month = "03";
        } else if ("Apr".equals(month)) {
            month = "04";
        } else if ("May".equals(month)) {
            month = "05";
        } else if ("Jun".equals(month)) {
            month = "06";
        } else if ("Jul".equals(month)) {
            month = "07";
        } else if ("Aug".equals(month)) {
            month = "08";
        } else if ("Sep".equals(month)) {
            month = "09";
        } else if ("Oct".equals(month)) {
            month = "10";
        } else if ("Nov".equals(month)) {
            month = "11";
        } else if ("Dec".equals(month)) {
            month = "12";
        }
        return month;
    }

}
