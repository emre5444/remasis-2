package com.ronin.utils;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 18.07.2014
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */
public final class DateUtils {

    public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";
    public static final String DEFAULT_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    private DateUtils() {
    }

    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    public static Date getToday() {
        return resetTime(getNow());
    }

    public static Date getLateNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date getDate(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date addDays(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    public static Date addHours(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, amount);
        return cal.getTime();
    }

    public static Date addMinutes(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, amount);
        return cal.getTime();
    }

    public static Date addMonth(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }

    public static Date setDateAndTime(Date date, int hour, int minute, int second, int milisecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, milisecond);
        return cal.getTime();
    }

    public static Date resetTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date timestamp2Date(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    public static Timestamp date2Timestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Date calendar2Date(Calendar calendar) {
        return calendar.getTime();
    }

    public static java.sql.Date date2SQLDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static int getDateDiff(Date date1, Date date2) {
        long diff = Math.abs(date1.getTime() - date2.getTime());
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    public static int getDateDiff(Calendar date1, Calendar date2) {
        return getDateDiff(date1.getTime(), date2.getTime());
    }

    public static String getTerm(Date date) {
        return formatDate(date, "yyyyMM");
    }

    public static String getTermSeperated(Date date) {
        return formatDate(date, "yyyy/MM");
    }

    public static String getYearFromDate(Date date) {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
        return simpleDateformat.format(date);
    }

    public static int getHourOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int getMinutesOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    public static String getStringHourOfDate(Date date) {
        return getHourOfDate(date) + ":00";
    }

    public static Boolean isSaturday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return true;
        }
        return false;
    }

    public static Boolean isSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public static Boolean isWeekend(Date date) {
        return isSaturday(date) || isSunday(date);
    }

    public static Date getBusinessDateForWeekend(Date date) {
        if (isSaturday(date)) {
            return DateUtils.addDays(date, 2);
        } else if (isSunday(date)) {
            return DateUtils.addDays(date, 1);
        }
        return date;
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    public static String formatDateForHat(Date date) {
        return formatDate(date, "dd.MM.yyyy");
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyyMMdd-HHmmss");
    }

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Integer convertDateToInt(Date date) {
        return convertDateToInt(date, "yyyyMMdd");
    }

    public static Integer convertDateToInt(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String stringDateFormat = simpleDateFormat.format(date);
        return Integer.valueOf(stringDateFormat);
    }

    public static Date convertIntToDate(Integer value) {
        if (value == null) {
            return null;
        }
        // Assuming format : YYYYMMDD
        String wsStringValue = value.toString();
        String year = wsStringValue.substring(0, 4);
        String month = wsStringValue.substring(4, 6);
        String dayofMonth = wsStringValue.substring(6, 8);
        return getDate(year + month + dayofMonth, "yyyyMMdd");
    }

    public static Date getLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return resetTime(c.getTime());
    }

    public static Date getNextMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        return resetTime(c.getTime());
    }

    public static Date getNextYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        return resetTime(c.getTime());
    }

    public static Date getYesterday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        return resetTime(c.getTime());
    }

    public static Date getTomorrow() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        return resetTime(c.getTime());
    }

    public static Date getDateOfNDaysBefore(int n) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, (-1 * n));
        return resetTime(c.getTime());
    }

    public static Date getYearBefore(int yearAmount) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1 * yearAmount);
        return resetTime(c.getTime());
    }

    public static Date getNDaysBeforeOfDate(Date date, int amount) {
        Date retDate = DateUtils.addDays(date, -1 * Math.abs(amount));
        return retDate;
    }

    public static Date getNDaysAfterOfDate(Date date, int amount) {
        return DateUtils.addDays(date, Math.abs(amount));
    }

    public static String getDayName(Date day, Locale locale) {
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] dayNames = symbols.getWeekdays();
        Calendar date2 = Calendar.getInstance();
        date2.setTime(day);
        return dayNames[date2.get(Calendar.DAY_OF_WEEK)];
    }

    public static long getDateDiffInTime(Date date1, Date date2) {
        return date2.getTime() - date1.getTime();
    }

    public static boolean isAfterToday(Date date) {
        return date.after(getToday()) || date.equals(getToday());
    }

    public static String getNowDateTimeFormatted() {
        return formatDate(getNow(), DEFAULT_DATE_TIME_FORMAT);
    }

    public static Date getFirstDayOfTheMonth() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();

        return firstDayOfMonth;
    }

    public static Date getFirstDayOfTheMonthWithoutTime() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();


        return getDate(formatDate(firstDayOfMonth,DEFAULT_DATE_FORMAT),DEFAULT_DATE_FORMAT);
    }


}
