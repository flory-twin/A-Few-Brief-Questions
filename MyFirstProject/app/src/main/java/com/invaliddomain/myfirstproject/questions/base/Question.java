package com.invaliddomain.myfirstproject.questions.base;

public class Question {
    public interface AnswerUpdateListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void onAnswerUpdated();
    }
    protected String questionAsText;
    protected String answerAsText;
    private AnswerUpdateListener listener;

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
            this.triggerListener();
        }
    }
    public void setListener(AnswerUpdateListener newListener)
    {
        listener = newListener;
    }
    private void triggerListener()
    {
        listener.onAnswerUpdated();
    }
}
