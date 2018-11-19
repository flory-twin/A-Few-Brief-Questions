package com.invaliddomain.myfirstproject.data;
import com.invaliddomain.myfirstproject.questions.DateTimeQuestion;
import com.invaliddomain.myfirstproject.questions.base.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class InMemoryDataRecord {
    private HashMap<String, Question> questions;
    private Date recordDate;

    public InMemoryDataRecord()
    {
        this(new Date());
    }

    public InMemoryDataRecord(Date existingDate)
    {
        //This is the master spot which controls which questions appear on the screen and in the results file.
        questions = new HashMap<String, Question>();
        initializeQuestions();
        recordDate = existingDate;
    }

    public void initializeQuestions()
    {
        HashMap<String, Class> template = QuestionsTemplate.getTemplate();
        for (String questionText: template.keySet())
        {
            Class c = template.get(questionText);
            if (c.getSimpleName().equals("DateTimeQuestion"))
            {
                questions.put(
                        questionText,
                        new DateTimeQuestion(questionText));
            }
        }
    }


    public Question getQuestion(String questionText)
    {
        if (questions.containsKey(questionText))
        {
            return questions.get(questionText);
        } else {
            return null;
        }
    }

    public HashMap<String, Question> getQuestionMap()
    {
        return questions;
    }

    public ArrayList<Question> getQuestions()
    {
        ArrayList<Question> returnList = new ArrayList<Question>();
        for (String s: questions.keySet())
        {
            returnList.add(questions.get(s));
        }
        return returnList;
    }

    public Date getRecordDate()
    {
        return recordDate;
    }

}