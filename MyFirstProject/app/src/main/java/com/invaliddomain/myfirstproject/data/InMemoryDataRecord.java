package com.invaliddomain.myfirstproject.data;
import com.invaliddomain.myfirstproject.question.datetime.DayDate;
import com.invaliddomain.myfirstproject.question.datetime.DateTimeQuestion;
import com.invaliddomain.myfirstproject.question.base.Question;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InMemoryDataRecord {
    private ArrayList<Question> questions;
    private DayDate recordDate;

    public InMemoryDataRecord() {

        this(new DayDate(new Date()));
    }

    public InMemoryDataRecord(DayDate existingDate) {
        //This is the master spot which controls which questions appear on the screen and in the results file.
        questions = new ArrayList<Question>();
        initializeQuestions();
        recordDate = existingDate;
    }

    public InMemoryDataRecord(String recordAsString)
    {
        questions = new ArrayList<Question>();
        initializeQuestions();
        try {
            this.deserialize(recordAsString);

            int uuidToSearchFor = 3;
            int recordDateIndex = QuestionsTemplate.getIndex(uuidToSearchFor);
            this.recordDate = new DayDate(
                    ((DateTimeQuestion) questions.get(recordDateIndex))
                            .getAnswerAsDate());
        }
        catch (Exception e)
        {
            //Something's wrong with that record!  For now, just stick with the default values.
        }
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

    public void deserialize(String record) throws Exception{
        String[] fields = record.split(",");
        //Assume that the data from the record is in the order given by the standard question template.
        //Assume that this is not a header row.
        //Set up the record using the first timed question known to contain today's date.
        //TODO: Migrate questions to R.
        this.setRecordDateFromAnswers();
        //Now, deserialize the fields.  Since the Question arraylist was used to initialize the questions, these fields will be in the same order as in the questions.
        //Note that using the Question constructor won't activate the change listener, which is exactly how we want things.
        for (int i = 0; i < questions.size(); i++)
        {
            QuestionsTemplate.QuestionTemplate qt = QuestionsTemplate.getTemplates().get(i);
            Class castType = qt.getQuestionType();
            String questionText = qt.getQuestion();
            Question q = questions.get(i);
            if (castType.equals(DateTimeQuestion.class))
            {
                Question oldQuestion = questions.get(i);
                questions.remove(i);
                Date answerAsDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(fields[i]);
                questions.add(i,
                        new DateTimeQuestion(
                            questionText,
                            answerAsDate,
                            oldQuestion.getUuid())
                        );
            }
        }
    }
}