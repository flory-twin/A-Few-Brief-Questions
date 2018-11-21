package com.invaliddomain.myfirstproject.questions.base;

import java.util.ArrayList;

public class Question {
    public interface AnswerUpdateListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void onAnswerUpdated();
    }
    protected int uuid;
    protected String questionAsText;
    protected String answerAsText;
    protected ArrayList<AnswerUpdateListener> listeners = new ArrayList<AnswerUpdateListener>();

    public Question(String question)
    {
        questionAsText = question;
        answerAsText = "    -  -     :  :  ";
        uuid = -1;
    }

    public Question(String question, String answer, int uuid)
    {
        questionAsText = question;
        answerAsText = answer;
        this.uuid = uuid;
    }
    public int getUuid() { return uuid; }
    public String getQuestionAsText()
    {
        return questionAsText;
    }
    public String getAnswerAsText()
    {
        return answerAsText;
    }
    protected void setQuestionAsText(String newQuestion)
    {
        questionAsText = newQuestion;
    }
    protected void setAnswerAsText(String newAnswer)
    {
        String oldAnswer = answerAsText;
        answerAsText = newAnswer;
        //Only trigger the listener if
        // 1. this is not the initialization phase for the answer, and
        // 2. the new answer differs from the old one.
        if (oldAnswer != null && !newAnswer.equals(oldAnswer)) {
            this.notifyListeners();
        }
    }

    public void addListener(AnswerUpdateListener l)
    {
        listeners.add(l);
    }

    protected void notifyListeners()
    {
        for (AnswerUpdateListener l: listeners)
        {
            l.onAnswerUpdated();
        }
    }
}
