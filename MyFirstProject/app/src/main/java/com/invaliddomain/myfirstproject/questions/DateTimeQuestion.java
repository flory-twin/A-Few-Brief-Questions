package com.invaliddomain.myfirstproject.questions;

import com.invaliddomain.myfirstproject.questions.base.Question;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeQuestion extends Question {
    private Date answerAsDate;

    public DateTimeQuestion(String questionText)
    {
        super(questionText);
        answerAsDate = null;
    }

    public DateTimeQuestion(String questionText, Date answer, int uuid)
    {
        super(questionText,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(answer),
                uuid);
        answerAsDate = answer;
    }

    public Date getAnswerAsDate()
    {
        return answerAsDate;
    }

    public void setAnswerToNow()
    {
        this.setAnswerAsDate(new Date());
    }

    public void setAnswerAsDate(Date date)
    {
        answerAsDate = date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Note: This has to be super--otherwise the change listeners won't fire.
        super.setAnswerAsText(format.format(answerAsDate));
    }

    public void setAnswer(int year, int month, int day, int hours24, int minutes, int seconds)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year + 1900, month, day, hours24, minutes, seconds);
        this.setAnswerAsDate(new Date(cal.getTimeInMillis()));
    }
}
