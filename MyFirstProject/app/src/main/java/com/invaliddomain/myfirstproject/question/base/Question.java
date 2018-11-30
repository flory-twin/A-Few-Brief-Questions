package com.invaliddomain.myfirstproject.question.base;

import java.util.ArrayList;

/**
 * Contains a text question, a uuid, and a text answer.
 * Provides listener functionality for changes to the answer.
 */
//TODO Neither the UUID system nor the question keying is enforced by this class!
// I need a different pattern to make this work...
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
        this(-1, question, "");
    }

    public Question(int uuidToSet, String question)
    {
        this(uuidToSet, question, "");
    }

    public Question(int uuid, String question, String answer)
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
            this.notifyAnswerChangeListeners();
        }
    }

    public void addAnswerChangeListener(AnswerUpdateListener l)
    {
        listeners.add(l);
    }

    protected void notifyAnswerChangeListeners()
    {
        for (AnswerUpdateListener l: listeners)
        {
            l.onAnswerUpdated();
        }
    }
}
