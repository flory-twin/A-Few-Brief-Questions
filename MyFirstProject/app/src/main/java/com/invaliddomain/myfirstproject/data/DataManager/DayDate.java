package com.invaliddomain.myfirstproject.data.DataManager;

import java.util.Date;

/**
 * This trivial helper class allows me to easily use only day-level date information.
 */
public class DayDate {
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
}
