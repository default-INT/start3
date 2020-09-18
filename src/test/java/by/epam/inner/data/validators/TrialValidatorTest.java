package by.epam.inner.data.validators;

import by.epam.inner.beans.Trial;
import by.epam.inner.exceptions.IncorrectAccountFormatException;
import by.epam.inner.exceptions.IncorrectMarkException;
import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrialValidatorTest {
    private static final JsonObject TRIAL_JSON_ACCOUNT_EXCEPTION = getTrialJsonExampleLoginError();
    private static final JsonObject TRIAL_JSON_MARK_EXCEPTION = getTrialJsonExampleMarkError();
    private static final JsonObject TRIAL_JSON_CORRECT = getTrialJsonExampleCorrect();

    private static JsonObject getTrialJsonExampleLoginError() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("class", "Trial");

        JsonObject argsJson = new JsonObject();
        argsJson.addProperty("account", "445435");
        argsJson.addProperty("mark1", 60);
        argsJson.addProperty("mark2", 71);

        jsonObject.add("args", argsJson);
        return jsonObject;
    }

    private static JsonObject getTrialJsonExampleMarkError() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("class", "Trial");

        JsonObject argsJson = new JsonObject();
        argsJson.addProperty("account", "correct.account");
        argsJson.addProperty("mark1", -54);
        argsJson.addProperty("mark2", 714);

        jsonObject.add("args", argsJson);
        return jsonObject;
    }

    private static JsonObject getTrialJsonExampleCorrect() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("class", "Trial");

        JsonObject argsJson = new JsonObject();
        argsJson.addProperty("account", "evgeniy.trofimov");
        argsJson.addProperty("mark1", 54);
        argsJson.addProperty("mark2", 14);

        jsonObject.add("args", argsJson);
        return jsonObject;
    }

    @Test
    public void testMarkCheck() {
        assertFalse(TrialValidator.markCheck(10));
    }

    @Test
    public void testMarkCheckNegative() {
        assertTrue(TrialValidator.markCheck(-10));
    }

    @Test
    public void testMarkCheckOutOfRange() {
        assertTrue(TrialValidator.markCheck(1000));
    }

    @Test
    public void testCheckArgsSuccessful() {
        TrialValidator trialValidator = new TrialValidator(Trial.class);
        trialValidator.checkArgs(TRIAL_JSON_CORRECT);
        assertTrue(true);
    }

    @Test
    public void testCheckArgsLoginException() {
        assertThrows(IncorrectAccountFormatException.class, () -> {
            TrialValidator trialValidator = new TrialValidator(Trial.class);
            trialValidator.checkArgs(TRIAL_JSON_ACCOUNT_EXCEPTION);
        });
    }

    @Test
    public void testCheckArgsMarkException() {
        assertThrows(IncorrectMarkException.class, () -> {
            TrialValidator trialValidator = new TrialValidator(Trial.class);
            trialValidator.checkArgs(TRIAL_JSON_MARK_EXCEPTION);
        });
    }


    @Test
    public void testGetValidTrial() {
        TrialValidator trialValidator = new TrialValidator(Trial.class);
        assertNotNull(trialValidator.getValidTrial(TRIAL_JSON_CORRECT));

    }
}