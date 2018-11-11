package com.invaliddomain.myfirstproject.questions.base;

public class Question {
    protected String questionAsText;
    protected String answerAsText;

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
        answerAsText = newAnswer;
    }
}
