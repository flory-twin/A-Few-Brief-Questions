package com.invaliddomain.myfirstproject.data;
import com.invaliddomain.myfirstproject.question.datetime.DayDate;
import com.invaliddomain.myfirstproject.question.datetime.DateTimeQuestion;
import com.invaliddomain.myfirstproject.question.base.Question;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Provides a master record for the questions present in a questionnaire.
 * Manages UUIDs and question content.
 * Does not provide push/pull or change listener functionality.
 */
public class InMemoryDataRecord {
    /**
     * -------------------------------------------------------------------------
     * Wraps Exception with a custom name/string.
     * -------------------------------------------------------------------------
     */
    public static class RecordCountException extends Exception
    {
        private String message;
        private int recordFieldCount;
        private int templateFieldCount;

        public RecordCountException()
        {
            super();
            recordFieldCount = -1;
            templateFieldCount = -1;
            message = "During data record unwrapping, the number of fields did not match the number of fields in the template.";

        }

        public RecordCountException(String message)
        {
            this();
            this.message = message;
        }

        public RecordCountException(int recordCount, int templateCount)
        {
            this();
            recordFieldCount = recordCount;
            templateFieldCount = templateCount;
            String whichCondition = ( recordFieldCount > templateFieldCount ? "more" : "fewer");
            message = "During data record unwrapping, there were " +
                    whichCondition + " fields in the unwrapped record than in the template.";
        }

        public RecordCountException(String message, Throwable cause)
        {
            super(message, cause);
            this.message = message;
        }

        protected RecordCountException(
                String message,
                Throwable cause,
                boolean enableSuppression,
                boolean writableStackTrace)
        {
            super(message, cause, enableSuppression, writableStackTrace);
            this.message = message;
        }

        protected RecordCountException(Throwable cause)
        {
            super (cause);
        }
    }
    /*
     * -------------------------------------------------------------------------
     */

    private ArrayList<Question> questions;
    private DayDate recordDate;

    /**
     * Sets up a valid--but empty--question list.
     */
    public InMemoryDataRecord() {
        questions = new ArrayList<Question>();
    }

    /**
     * Initializes a question list from the template.
     * @param dateToCreate
     */
    public void initializeRecordFromTemplate(DayDate dateToCreate) {
        //This is the master spot which controls which questions appear on the screen and in the results file.
        questions = new ArrayList<Question>();
        initializeQuestions();
        recordDate = dateToCreate;
    }

    public void initializeQuestions() {
        //Create empty questions.
        for (QuestionsTemplate.QuestionTemplate qt : QuestionsTemplate.getTemplates()) {
            if (qt.getQuestionType().getSimpleName().equals("DateTimeQuestion")) {
                questions.add(new DateTimeQuestion(qt.getQuestion()));
            }//else if other class....
        }
    }

    public ArrayList<Question> getQuestions()
    {
        return questions;
    }

    public DayDate getRecordDate()
    {
        return recordDate;
    }

    public void setRecordDateFromAnswers()
    {
        //Try to set the date from the first field we know to hold the current date.
        int uuidToSearchFor = 3;
        int recordDateIndex = QuestionsTemplate.getIndex(uuidToSearchFor);
        Date calculatedRecordDate = ((DateTimeQuestion) questions.get(recordDateIndex))
                .getAnswerAsDate();
        if (calculatedRecordDate == null) {
            this.recordDate = new DayDate(new Date());
        }
        else
        {
            this.recordDate = new DayDate(calculatedRecordDate);
        };
    }

    public Question getQuestionByUuid(int uuid)
    {
        String question = QuestionsTemplate.getQuestionByUuid(uuid);
        for (Question q : questions) {
            if (q.getQuestionAsText().equals(question)) {
                return q;
            }
        }
        return null;
    }

    public Question getQuestion(String questionText) {
        for (Question q : questions) {
            if (q.getQuestionAsText().equals(questionText)) {
                return q;
            }
        }
        return null;
    }

    /*
     * --------------------------------------------------------------------------------
     * String methods.
     * --------------------------------------------------------------------------------
     */
    public String serialize() {
        //We must assume that the column headers have remained in the same order over time.  -On any day that order changes, the header row order must be modified.-
        String row = "";
        for (int i = 0; i < questions.size(); i++)
        {
            if (i < questions.size()-1)
            {
                row += questions.get(i).getAnswerAsText() + ",";
            }
            else
            {
                //Note that BufferedWriter should add its own newlines at a higher level.
                row += questions.get(i).getAnswerAsText();
            }
        }
        return row;
    }

    /**
     *
     * @param record
     * @throws Exception
     */
    public void deserialize(String record) throws ParseException, RecordCountException{
        //Assume that the data from the record is in the order given by the standard question template.
        //Assume that this is not a header row.
        //Now, deserialize the fields.  Since the Question arraylist was used to initialize the questions, these fields will be in the same order as in the questions.
        //Note that using the Question constructor won't activate the change listener, which is exactly how we want things.
        ArrayList<QuestionsTemplate.QuestionTemplate> questionsTemplate = QuestionsTemplate.getTemplates();
        //String.split(regex) doesn't work on records like 'date,date,,,,'...so we need to bake our own here.
        //String[] fields = record.split(",");
        int numberOfQuestionsInTemplate = questionsTemplate.size();
        ArrayList<String> fields = new ArrayList<String>();
        while (!record.equals(""))
        {
            int nextComma = record.indexOf(",");
            if (nextComma > 0) {
                fields.add(record.substring(0, nextComma));
                record = record.substring(nextComma + 1, record.length());
            }
            else if (nextComma == 0)
            {
                fields.add("");
                record = record.substring(nextComma + 1, record.length());
                if (record.equals(""))
                {
                    //Special case--there's a blank record remaining after the last comma is stripped.
                    //Since the string we're pulling from is now empty, we need to add the blank record by hand.
                    fields.add("");
                }
            }
            else if (nextComma < 0 && record.length() > 0)
            {
                fields.add(record);
                //Check the special
                record = "";

            }
            else
            {
                //This code should never be active
                record = "";
            }
        }

        int numberOfQuestionsInRecord = fields.size();
        if (numberOfQuestionsInRecord != numberOfQuestionsInTemplate)
        {
            throw new RecordCountException(
                    numberOfQuestionsInRecord,
                    numberOfQuestionsInTemplate);
        }
        else
        {
            for (int i = 0; i < numberOfQuestionsInRecord; i++) {
                QuestionsTemplate.QuestionTemplate qt = questionsTemplate.get(i);
                Class castType = qt.getQuestionType();
                String questionText = qt.getQuestion();
                if (castType.equals(DateTimeQuestion.class)) {
                    Date answerAsDate = null;
                    if (questions.size() > i) {
                        //Before proceeding, remove the old question.
                        questions.remove(i);
                    }
                    //Add new questions.
                    if (fields.get(i).equals("")) {
                        //The empty string is used when an answer hasn't been set yet.  So,
                        // just initialize this question without an answer.
                        questions.add(i,
                                new DateTimeQuestion(
                                        qt.getUuid(),
                                        questionText)
                        );
                    }
                    else
                    {
                        //Try to parse the date.
                        answerAsDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(fields.get(i));
                        questions.add(i,
                                new DateTimeQuestion(
                                        qt.getUuid(),
                                        questionText,
                                        answerAsDate)
                        );
                    }
                }
            }
        }
        //Set up the record date using the first timed question known to contain today's date.
        this.setRecordDateFromAnswers();
    }
}