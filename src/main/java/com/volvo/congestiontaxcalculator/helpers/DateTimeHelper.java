package com.volvo.congestiontaxcalculator.helpers;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class DateTimeHelper
{
    public static boolean checkDateBetweenConfigMinutes(LocalDateTime startDate, LocalDateTime endDate, int timeRange)
    {
        Duration duration = Duration.between(startDate, endDate);
        return duration.toMinutes() > timeRange;
    }

    public static boolean checkIsDateTaxFree(LocalDateTime date)
    {
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            return true;
        }

        if (date.getYear() == 2013)
        {
            if (month == Month.JANUARY && day == 1 ||
                    month == Month.MARCH && (day == 28 || day == 29) ||
                    month == Month.APRIL && (day == 1 || day == 30) ||
                    month == Month.MAY && (day == 1 || day == 8 || day == 9) ||
                    month == Month.JUNE && (day == 5 || day == 6 || day == 21) ||
                    month == Month.JULY ||
                    month == Month.NOVEMBER && day == 1 ||
                    month == Month.DECEMBER && (day == 24 || day == 25 || day == 26 || day == 31))
            {
                return true;
            }
        }
        return false;
    }
}
