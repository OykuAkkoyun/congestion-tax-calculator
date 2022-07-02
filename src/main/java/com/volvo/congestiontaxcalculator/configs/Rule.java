package com.volvo.congestiontaxcalculator.configs;

import com.volvo.congestiontaxcalculator.helpers.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@Component
@Getter
@Setter
public class Rule
{
    private String startTime;
    private String endTime;
    private int amount;

    public boolean isTimeBetweenRuleTimes(String dateTime)
    {
        Calendar startDate = getCalenderByGivenDateTime(this.startTime);
        Calendar endDate = getCalenderByGivenDateTime(this.endTime);
        Calendar requestingDate = getCalenderByGivenDateTime(dateTime);

        Date x = requestingDate.getTime();
        if ((x.after(startDate.getTime()) || x.equals(startDate.getTime()))
                && (x.before(endDate.getTime())) || x.equals(endDate.getTime()))
        {
            return true;
        }

        return false;
    }

    private Calendar getCalenderByGivenDateTime(String dateTime)
    {
        Calendar calendar = null;
        try {
            Date startDate = Constants.ruleDateFormat.parse(dateTime);
            calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, 1);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

}
