package com.invaliddomain.myfirstproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeQuestion extends Question{
    private Date answerAsDate;

    public DateTimeQuestion(String questionText)
    {

        questionAsText = questionText;
        answerAsText = "    -  -     :  :  ";
    }

    public Date getAnswerAsDate()
    {
        return answerAsDate;
    }

    public void setAnswerToNow()
    {
        answerAsDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        answerAsText = format.format(answerAsDate);
    }

    public void setAnswerAsDate(Date date)
    {
        answerAsDate = date;
    }

    public void setAnswer(int year, int month, int day, int hours24, int minutes, int seconds)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year + 1900, month, day, hours24, minutes, seconds);
        answerAsDate = new Date(cal.getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("uuuu-MM-dd HH:mm:ss");
        answerAsText = format.format(answerAsDate);
    }
}
