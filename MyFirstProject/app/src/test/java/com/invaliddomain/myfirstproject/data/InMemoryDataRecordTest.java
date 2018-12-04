package com.invaliddomain.myfirstproject.data;

import com.invaliddomain.myfirstproject.question.base.Question;
import com.invaliddomain.myfirstproject.question.datetime.DateTimeQuestion;
import com.invaliddomain.myfirstproject.question.datetime.DayDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.text.ParseException;
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

        final String validDateField = "2018-10-12 01:01:00";
        final String invalidDateField = "2018-10-12 yadda yadda";
        final String otherValidDateField = "2018-10-12 01:01:01";
        final String anotherValidDateField = "2018-10-12 01:01:59";

        //----------------------------------------------------------------------
        //n records, all valid dates
        for (int count = 0; count < numberOfQuestions; count++)
        {
            testValues.add(validDateField);
        }
        testDeserializationAgainstStringifiedAnswers(record,
                testValues,
                identifier + "1. ",
                "When a record is deserialized that only contains valid date answers," +
                        "the deserialized dates *should* be the same as the input record.");

        //----------------------------------------------------------------------
        //n records, all dates with seconds
        testValues = new ArrayList<String>();
        for (int count = 0; count < numberOfQuestions; count++)
        {
            testValues.add(count, otherValidDateField);
        }
        testDeserializationAgainstStringifiedAnswers(record,
                testValues,
                identifier + "2. ",
                "When a record is deserialized, and the dates contain seconds," +
                        " despite their displayed value in the layout views, the seconds in " +
                        "the record should not be rounded.");

        testValues = new ArrayList<String>();

        //----------------------------------------------------------------------
        //n records, not all the same, all valid dates
        for (int count = 0; count < numberOfQuestions; count++)
        {
            switch ((count + 1) % 3)
            {
                case 1:
                    testValues.add(count, validDateField);
                    break;
                case 2:
                    testValues.add(count, otherValidDateField);
                    break;
                case 0:
                    testValues.add(count, anotherValidDateField);
                    break;
            }
        }
        testDeserializationAgainstStringifiedAnswers(record,
                testValues,
                identifier + "3. ",
                "When a record is deserialized, and it contains several different" +
                        " date record values, they should all deserialize correctly.");

        //----------------------------------------------------------------------
        testValues.remove(0);
        testValues.add(0, empty);
        testDeserializationAgainstStringifiedAnswers(record,
                testValues,
                identifier + "4. ",
                "When an empty string is deserialized, it should neither throw" +
                        "an exception, nor should the result (as a string) be anything other than the empty string.");
        Question firstQuestion = record.getQuestions().get(0);
        assertNull(
                identifier + "5. " + "When an empty string is deserialized, the result " +
                        "(as a date) should not be anything other than null.",
                ((DateTimeQuestion) firstQuestion).getAnswerAsDate());

        //----------------------------------------------------------------------

        //Set all but the first two values to empty (a known failure case)
        testValues.remove(0);
        testValues.add(0, validDateField);
        testValues.remove(1);
        testValues.add(1, validDateField);
        for (int count = 2; count < numberOfQuestions; count++)
        {
            testValues.remove(count);
            testValues.add(count, empty);
        }
        testDeserializationAgainstStringifiedAnswers(record,
                testValues,
                identifier + "6. ",
                "When an empty string in the last position is deserialized, it should neither throw" +
                        "an exception, nor should the result (as a string) be anything other than the empty string.");
        Question lastQuestion = record.getQuestions().get(numberOfQuestions - 1);
        assertNull(
                identifier + "7. " + "When an empty string in the last position is deserialized, the result " +
                        "(as a date) should not be anything other than null.",
                ((DateTimeQuestion) firstQuestion).getAnswerAsDate());

        //----------------------------------------------------------------------
        //Set the first test value to not-a-date.
        testValues.remove(0);
        testValues.add(0, invalidDateField);
        testDeserializationAgainstStringifiedAnswersWithParseException(record,
                    testValues,
                    identifier + "8. ",
                    "When one of the fields is not a date, a parse exception should" +
                            "be thrown. ");

        //----------------------------------------------------------------------
        //Set the first test value back to a date.
        testValues.remove(0);
        testValues.add(0, validDateField);
        //Use too many records for the template (it's currently at the correct number).
        testValues.add(validDateField);
        testDeserializationAgainstStringifiedAnswersWithRecordCountException(record,
                testValues,
                identifier + "9. ",
                "When there are too many records for the template size, " +
                        "a RecordCountException should be thrown.");

        //----------------------------------------------------------------------
        //Even if the memory record has not been initialized, deserialization should still give us a valid record.
        record = new InMemoryDataRecord();
        testDeserializationAgainstStringifiedAnswersWithRecordCountException(record,
                testValues,
                identifier + "10. ",
                "Deserialization into a non-initialized record (question count 0) " +
                        "should not throw an exception and should give us a valid record.");
    }

    private void testDeserializationAgainstStringifiedAnswers(
            InMemoryDataRecord record,
            ArrayList<String> valuesToTest,
            String identifier,
            String assertionFailureMessage)
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

    private void testDeserializationAgainstStringifiedAnswersWithParseException(
            InMemoryDataRecord record,
            ArrayList<String> valuesToTest,
            String fullIdentifier,
            String assertionFailureMessage)
    {
        String testString = "";
        for (String value: valuesToTest)
        {
            testString += value + c;
        }
        //Remove last comma
        testString = testString.substring(0, testString.length()-1);

        Exception thrownException = new Exception();
        try {
            record.deserialize(testString);
        }
        catch (ParseException e)
        {
            thrownException = e;
        }
        catch (Exception e)
        {
            thrownException = e;
        }

        assertEquals(fullIdentifier + assertionFailureMessage +
                        "\r\n The thrown exception was a " +
                        thrownException.getClass() + " with message '" +
                        thrownException.getMessage() + "'",
                ParseException.class,
                thrownException.getClass()
        );
    }


    private void testDeserializationAgainstStringifiedAnswersWithRecordCountException(
            InMemoryDataRecord record,
            ArrayList<String> valuesToTest,
            String fullIdentifier,
            String assertionFailureMessage)
    {
        String testString = "";
        for (String value: valuesToTest)
        {
            testString += value + c;
        }
        //Remove last comma
        testString = testString.substring(0, testString.length()-1);

        Exception thrownException = new Exception();
        try {
            record.deserialize(testString);
        }
        catch (InMemoryDataRecord.RecordCountException e)
        {
            thrownException = e;
        }
        catch (Exception e)
        {
            thrownException = e;
        }

        assertEquals(fullIdentifier + assertionFailureMessage +
                        "\r\n The thrown exception was a " +
                        thrownException.getClass() + " with message '" +
                        thrownException.getMessage() + "'",
                InMemoryDataRecord.RecordCountException.class,
                thrownException.getClass()
        );
    }
}