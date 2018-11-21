package com.invaliddomain.myfirstproject.data;

import java.util.ArrayList;

public class QuestionsTemplate {
    //1. A question is keyed by its globally unique UUID.  *The UUIDS should be moved into an external data storage file to guarantee global uniqueness and identity over itme.*
    //2. Questions are ordered and possess an index; this is set by order of addition to storage.

    /*
     IMPORTANT: THIS IMPLEMENTATION DOES NOT CAPTURE THE REQUIREMENT THAT BOTH UUID AND QUESTION TEXT MUST BE UNIQUE TO A SINGLE QUESTION IN THE LIST.
     */

    private static ArrayList<QuestionTemplate> templates;

    static {
        try {
            templates = new ArrayList<QuestionTemplate>();
            templates.add(new QuestionTemplate(
                    1,
                    "When did you take your medications last night?",
                    "PM Meds",
                    Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion")));
            templates.add(new QuestionTemplate(
                    2,
                    "When did you fall asleep last night?",
                    "Asleep",
                    Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion")));
            templates.add(new QuestionTemplate(
                    3,
                    "When did you take your AM Ritalin at?",
                    "AM Rit",
                    Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion")));
            templates.add(new QuestionTemplate(
                    4,
                    "When did you become stably conscious?",
                    "Up",
                    Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion")));
            templates.add(new QuestionTemplate(
                    5,
                    "When did you finish dressing?",
                    "Dressed",
                    Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion")));
            templates.add(new QuestionTemplate(
                    6,
                    "When did you eat breakfast?",
                    "Breakfast",
                    Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion")));
        }
        catch(ClassNotFoundException e)
        {
            //No action.
        }
    }

    public static ArrayList<QuestionTemplate> getTemplates()
    {
        return templates;
    }

    public static int getUuid(String question)
    {
        for (QuestionTemplate qt: templates)
        {
            if (qt.getQuestion().equals(question))
            {
                return qt.getUuid();
            }
        }
        return -1;
    }

    public static int getIndex(String question)
        {
            for (int i = 0; i < templates.size(); i++)
            {
                if (templates.get(i).getQuestion().equals(question))
                {
                    return i;
                }
            }
            return -1;
    }

    public static String getQuestionByUuid(int uuid) {
        for (QuestionTemplate qt : templates) {
            if (qt.getUuid() == uuid) {
                return qt.getQuestion();
            }
        }
        //else
        return "";
    }

    public static int getIndex(int uuid)
    {
        for (int i = 0; i < templates.size(); i++)
        {
            if (templates.get(i).getUuid() == uuid)
            {
                return i;
            }
        }
        return -1;
    }

    public static class QuestionTemplate {
        private int uuid;
        private String question;
        private String headerName;
        private Class questionType;

        public QuestionTemplate(int uuidToSet, String questionToSet, String headerToSet, Class questionTypeToSet) {
            uuid = uuidToSet;
            question = questionToSet;
            headerName = headerToSet;
            questionType = questionTypeToSet;
        }

        public String getQuestion() {
            return question;
        }

        public String getHeaderName() {
            return headerName;
        }

        public Class getQuestionType() {
            return questionType;
        }

        public int getUuid() {
            return uuid;
        }
    }
}