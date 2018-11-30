package com.invaliddomain.myfirstproject.data;

import com.invaliddomain.myfirstproject.question.base.Question;
import com.invaliddomain.myfirstproject.question.datetime.DateTimeQuestion;
import com.invaliddomain.myfirstproject.question.datetime.DayDate;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class InMemoryDataRecordTest {
    final String c = ",";
    final String empty = "";

    @Test
    public void initializeRecordFromTemplate() {
    }

    @Test
    public void initializeQuestions() {
    }

    @Test
    public void getRecordDate() {
    }

    @Test
    public void serialize() {
    }

    @Test
    public void testDeserialize() {
        String identifier = "InMemoryDataRecordTest.testDeserialize.";

        InMemoryDataRecord record = new InMemoryDataRecord();
        record.initializeRecordFromTemplate(new DayDate(new Date()));
        int numberOfQuestions = record.getQuestions().size();
        ArrayList<String> testValues = new ArrayList<String>();

        final String validField = "2018-10-12 01:01:00";
        //n records, all valid dates
        for (int count = 0; count < numberOfQuestions; count++)
        {
            testValues.add(validField);
        }
        testDeserializationAgainstStringifiedAnswers(record,
                testValues,
                identifier + "1. ",
                "When a record is deserialized that only contains valid date answers," +
                        "the deserialized dates *should* be the same as the input record.");

        testValues = new ArrayList<String>();
        final String fieldWhichRoundsDown = "2018-10-12 01:01:01";
        //n records; last one is a date with seconds, which should round down
        for (int count = 0; count < numberOfQuestions; count++)
        {
            testValues.add(count, fieldWhichRoundsDown);
        }
        testDeserializationAgainstStringifiedAnswers(record,
                testValues,
                identifier + "2. ",
                "When a record is deserialized, and the dates contain seconds," +
                        " the seconds should round down during deserialization.");

        testValues = new ArrayList<String>();
        final String otherFieldWhichRoundsDown = "2018-10-12 01:01:59";
        //n records; last one is a date with seconds, which should round down
        for (int count = 0; count < numberOfQuestions; count++)
        {
            testValues.add(count, fieldWhichRoundsDown);
        }
        testDeserializationAgainstStringifiedAnswers(record,
                testValues,
                identifier + "3. ",
                "When a record is deserialized, and the dates contain seconds," +
                        " the seconds should round down during deserialization.");

        //Set the first test value to empty.
        testValues.remove(0);
        testValues.add(0, empty);
        testDeserializationAgainstStringifiedAnswers(record,
                testValues,
                identifier + "4. ",
                "When an empty value is deserialized, it should neither throw" +
                        "an exception, nor should the result (as a string) be anything other than the empty string.");
        Question firstQuestion = record.getQuestions().get(0);
        assertNull(
                identifier + "5. " + "When an empty value is deserialized, the result " +
                        "(as a date) should not be anything other than null.",
                ((DateTimeQuestion) firstQuestion).getAnswerAsDate());

        //too few records
        //too many records
    }

    private void testDeserializationAgainstStringifiedAnswers(InMemoryDataRecord record, ArrayList<String> valuesToTest, String identifier, String assertionFailureMessage)
    {
        String testString = "";
        for (String value: valuesToTest)
        {
            testString += value + c;
        }
        //Remove last comma
        testString = testString.substring(0, testString.length()-1);

        try {
            record.deserialize(testString);
        }
        catch (Exception e)
        {
            fail(identifier + "Unexpected exception thrown: " + e.getMessage());
        }

        boolean allAnswersMatchExpected = true;
        for (Question q: record.getQuestions())
        {
            int questionIndex = record.getQuestions().indexOf(q);
            String originalValue = valuesToTest.get(questionIndex);
            assertEquals(identifier + assertionFailureMessage,
                    originalValue,
                    q.getAnswerAsText());
        }

    }
}