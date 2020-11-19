package by.epam.inner.data.json.validators;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.exceptions.IncorrectMarkException;
import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonExtraTrialValidatorTest {

    private static final JsonObject TRIAL_JSON_MARK_EXCEPTION = getTrialJsonExampleMarkError();
    private static final JsonObject TRIAL_JSON_CORRECT = getTrialJsonExampleCorrect();

    private static JsonObject getTrialJsonExampleMarkError() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("class", "ExtraTrial");

        JsonObject argsJson = new JsonObject();
        argsJson.addProperty("account", "correct.account");
        argsJson.addProperty("mark1", 54);
        argsJson.addProperty("mark2", 14);
        argsJson.addProperty("mark3", 714);

        jsonObject.add("args", argsJson);
        return jsonObject;
    }

    private static JsonObject getTrialJsonExampleCorrect() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("class", "ExtraTrial");

        JsonObject argsJson = new JsonObject();
        argsJson.addProperty("account", "evgeniy.trofimov");
        argsJson.addProperty("mark1", 54);
        argsJson.addProperty("mark2", 14);
        argsJson.addProperty("mark3", 24);

        jsonObject.add("args", argsJson);
        return jsonObject;
    }

    @Test
    public void checkArgsIncorrectMark() {
        assertThrows(IncorrectMarkException.class, () -> {
            JsonExtraTrialValidator trialValidator = new JsonExtraTrialValidator(ExtraTrial.class);
            trialValidator.checkArgs(TRIAL_JSON_MARK_EXCEPTION);
        });
    }

    @Test
    public void checkArgs() {
        JsonExtraTrialValidator trialValidator = new JsonExtraTrialValidator(ExtraTrial.class);
        trialValidator.checkArgs(TRIAL_JSON_CORRECT);
    }

    @Test
    public void setArgs() {
        JsonExtraTrialValidator trialValidator = new JsonExtraTrialValidator(ExtraTrial.class);
        assertNotNull(trialValidator.getValidTrial(TRIAL_JSON_CORRECT));
    }
}