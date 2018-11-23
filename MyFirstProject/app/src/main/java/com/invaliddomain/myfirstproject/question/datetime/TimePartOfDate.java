package com.invaliddomain.myfirstproject.question.datetime;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * This trivial helper class allows me to easily use only day-level date information.
 */
public class TimePartOfDate implements Comparable<TimePartOfDate>{
    private int hourTwentyFour;
    private int minute;
    private int second;

    public TimePartOfDate(int hourTwentyFourToSet, int minuteToSet, int secondToSet)
    {
        hourTwentyFour = hourTwentyFourToSet;
        minute = minuteToSet;
        second = secondToSet;
    }
    public TimePartOfDate(Date date)
    {
        this(date.getHours(), date.getMinutes(), date.getSeconds());
    }
    public int getHourTwentyFour() {
        return hourTwentyFour;
    }

    public void setHourTwentyFour(int hourTwentyFour) {
        this.hourTwentyFour = hourTwentyFour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
    public String toString()
    {
        return String.format("%02d", hourTwentyFour) + ":" +
                String.format("%02d", minute);
    }

    @Override
    public int compareTo(@NonNull TimePartOfDate o) {
        if (hourTwentyFour > o.getHourTwentyFour())
        {
            return 1;
        }
        else if (hourTwentyFour < o.getHourTwentyFour())
        {
            return -1;
        }
        else
        {
            if (minute > o.getMinute())
            {
                return 1;
            }
            else if (minute < o.getMinute())
            {
                return -1;
            }
            else
            {
                if (second > o.getSecond())
                {
                    return 1;
                }
                else if (second < o.getSecond())
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        }
    }
}
