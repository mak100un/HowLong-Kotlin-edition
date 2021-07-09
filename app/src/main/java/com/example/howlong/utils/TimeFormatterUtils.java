package com.example.howlong.utils;

import java.util.concurrent.TimeUnit;

public class TimeFormatterUtils {
    public static String ToHHmmssFormat(long timeInMillis){
        long hours = TimeUnit.MILLISECONDS.toHours(timeInMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) - minutes * 60;
        minutes -= hours * 60;
        return (hours > 0 ? Minimum2DigitsFormat(hours) + ":" : "") + Minimum2DigitsFormat(minutes)+ ":" + Minimum2DigitsFormat(seconds);
    }

    public static String ToHHmmFormat(long timeInMillis){
        long hours = TimeUnit.MILLISECONDS.toHours(timeInMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) - hours * 60;
        return Minimum2DigitsFormat(hours) + ":" + Minimum2DigitsFormat(minutes);
    }

    public static String TommssFormat(long timeInMillis){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) - minutes * 60;
        minutes -= TimeUnit.MILLISECONDS.toHours(timeInMillis) * 60;
        return Minimum2DigitsFormat(minutes) + ":" + Minimum2DigitsFormat(Math.abs(seconds));
    }

    public static String ToNoneZeroHWithPostfixFormat(long timeInMillis){
        long hours = TimeUnit.MILLISECONDS.toHours(timeInMillis) - TimeUnit.MILLISECONDS.toDays(timeInMillis) * 24;
        return hours != 0 ? hours + " ч" : "";
    }

    public static String ToNonZerodWithPostfixFormat(long timeInMillis){
        long days = TimeUnit.MILLISECONDS.toDays(timeInMillis);
        return days != 0 ? days + " д" : "";
    }

    public static String Minimum2DigitsFormat(long time){
        long absTime = Math.abs(time);
        return (time < 0 ? "-" : "") + (absTime >= 10 ? "" : "0") + absTime;
    }

    public static String GetMonthName(int month)
    {
        switch(month)
        {
            case 0:
                return "Январь";
            case 1:
                return "Февраль";
            case 2:
                return "Март";
            case 3:
                return "Апрель";
            case 4:
                return "Май";
            case 5:
                return "Июнь";
            case 6:
                return "Июль";
            case 7:
                return "Август";
            case 8:
                return "Сентябрь";
            case 9:
                return "Октябрь";
            case 10:
                return "Ноябрь";
            case 11:
                return "Декабрь";
            default:
                throw new UnsupportedOperationException("No such month");
        }
    }

    public static String GetDayOfWeekShortName(int dayOfWeek)
    {
        switch (dayOfWeek)
        {
            case 1:
                return "Вс";
            case 2:
                return"Пн";
            case 3:
                return "Вт";
            case 4:
                return "Ср";
            case 5:
                return "Чт";
            case 6:
                return "Пт";
            case 7:
                return "Сб";
            default:
                throw new UnsupportedOperationException("No such day");
        }
    }
}
