package com.invaliddomain.myfirstproject.data;

import java.util.HashMap;

public class QuestionsTemplate {
    private static HashMap<String, Class> templates;
    private static HashMap<String, String> shortNames;
    static
    {
        templates = new HashMap<String, Class>();
        shortNames = new HashMap<String, String>();
        String question = "When did you take your medications last night?";
        try {
            templates.put(question, Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion"));
            shortNames.put(question, "PM Meds");
            question = "When did you fall asleep last night?";
            templates.put(question, Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion"));
            shortNames.put(question, "Asleep");
            question = "When did you take your AM Ritalin at?";
            templates.put(question, Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion"));
            shortNames.put(question, "AM Rit");
            question = "When did you become stably conscious?";
            templates.put(question, Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion"));
            shortNames.put(question, "Up");
            question = "When did you finish dressing?";
            templates.put(question, Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion"));
            shortNames.put(question, "Dressed");
            question = "When did you eat breakfast?";
            templates.put(question, Class.forName("com.invaliddomain.myfirstproject.questions.DateTimeQuestion"));
            shortNames.put(question, "Breakfast");
        }
        catch (ClassNotFoundException e)
        {
            //No action.
        }
    }
    public static HashMap<String, Class> getTemplate()
    {
        return  templates;
    }

    public static HashMap<String, String> getShortNames()
    {
        return shortNames;
    }

}