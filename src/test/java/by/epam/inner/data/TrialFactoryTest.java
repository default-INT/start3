package by.epam.inner.data;

import com.google.gson.JsonObject;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class TrialFactoryTest {

    private static final JsonObject TRIAL_JSON_CORRECT = getTrialJsonExampleCorrect();

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
    public void getTrial() {
        assertNotEquals(Optional.empty(), TRIAL_JSON_CORRECT);
    }
}