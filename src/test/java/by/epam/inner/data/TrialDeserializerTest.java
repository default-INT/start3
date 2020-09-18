package by.epam.inner.data;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.LightTrial;
import by.epam.inner.beans.StrongTrial;
import by.epam.inner.beans.Trial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrialDeserializerTest {

    private static final TrialDeserializer TRIAL_DESERIALIZER = new TrialDeserializer();
    private final static Gson GSON = getGson();
    private static final JsonObject TRIAL_JSON_CORRECT = getTrialJsonExampleCorrect();

    private static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Trial.class, TRIAL_DESERIALIZER)
                .registerTypeAdapter(LightTrial.class, TRIAL_DESERIALIZER)
                .registerTypeAdapter(StrongTrial.class, TRIAL_DESERIALIZER)
                .registerTypeAdapter(ExtraTrial.class, TRIAL_DESERIALIZER)
                .create();
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
    public void deserialize() {
        assertNotNull(GSON.fromJson(TRIAL_JSON_CORRECT, Trial.class));
    }
}