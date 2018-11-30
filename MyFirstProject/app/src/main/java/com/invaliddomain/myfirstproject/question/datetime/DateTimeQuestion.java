package com.invaliddomain.myfirstproject.question.datetime;

import com.invaliddomain.myfirstproject.question.base.Question;

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

    public DateTimeQuestion(int uuid, String questionText)
    {
        super(uuid,
                questionText
        );
        answerAsDate = null;
    }

    public DateTimeQuestion(int uuid, String questionText, Date answer)
    {
        super(uuid,
                questionText,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(answer)
                );
        answerAsDate = answer;
    }

    /**
     *
     * @return The stored date, which may be null if the answer has not been set.
     */
    public Date getAnswerAsDate()
    {
        return answerAsDate;
    }

    public String getTimePartOfAnswerAsString()
    {
        String timePartAsString = "";
        if (answerAsDate != null) {
            timePartAsString = new TimePartOfDate(answerAsDate).toString();
        }
        else
        {
            timePartAsString = "  :  ";
        }
        return timePartAsString;
    }

    public String getDatePartOfAnswerAsString()
    {
        String datePartAsString = "";
        if (answerAsDate != null) {
            datePartAsString = new DayDate(answerAsDate).toString();
        }
        else
        {
            datePartAsString = "    -  -  ";
        }
        return datePartAsString;
    }

    public void setAnswerToNow()
    {
        this.setAnswerAsDate(new Date());
    }

    public void setAnswerAsDate(Date date)
    {
        answerAsDate = date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Note: This has to be super--otherwise the change listeners on Question won't fire.
        super.setAnswerAsText(format.format(answerAsDate));
    }

    public void setAnswer(int year, int month, int day, int hours24, int minutes, int seconds)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year + 1900, month, day, hours24, minutes, seconds);
        this.setAnswerAsDate(new Date(cal.getTimeInMillis()));
    }
}
