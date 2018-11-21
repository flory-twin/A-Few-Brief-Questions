package com.invaliddomain.myfirstproject.data.DataManager;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * This trivial helper class allows me to easily use only day-level date information.
 */
public class DayDate implements Comparable<DayDate>{
    private int year;
    private int monthOfYear;
    private int dayOfMonth;

    public DayDate(int year, int month, int day)
    {
        this.year = year;
        monthOfYear = month;
        dayOfMonth = day;
    }
    public DayDate(Date date)
    {
        this(date.getYear(), date.getMonth(), date.getDay());
    }
    public void setYear(int yearToSet)
    {
        year = yearToSet;
    }
    public void setMonth(int monthToSet)
    {
        monthOfYear = monthToSet;
    }
    public void setDay(int dayToSet)
    {
        dayOfMonth = dayToSet;
    }
    public int getYear()
    {
        return year;
    }
    public int getMonth()
    {
        return monthOfYear;
    }
    public int getDay()
    {
        return dayOfMonth;
    }

    public String toString()
    {
        return Integer.toString(year) + "-" + Integer.toString(monthOfYear) + "-" + Integer.toString(dayOfMonth);
    }

    @Override
    public int compareTo(@NonNull DayDate o) {
        if (this.getYear() > o.getYear())
        {
            return 1;
        }
        else if (this.getYear() < o.getYear())
        {
            return -1;
        }
        else
        {
            if (this.getMonth() > o.getMonth())
            {
                return 1;
            }
            else if (this.getMonth() < o.getMonth())
            {
                return -1;
            }
            else
            {
                if (this.getDay() > o.getDay())
                {
                    return 1;
                }
                else if (this.getDay() < o.getDay())
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
