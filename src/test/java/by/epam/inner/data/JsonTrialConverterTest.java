package by.epam.inner.data;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.LightTrial;
import by.epam.inner.beans.StrongTrial;
import by.epam.inner.beans.Trial;
import by.epam.inner.data.json.JsonTrialConverter;
import com.google.gson.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonTrialConverterTest {

    private static final JsonTrialConverter TRIAL_DESERIALIZER = new JsonTrialConverter();
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
    public void serialize() {
        ExtraTrial trial = new ExtraTrial();
        trial.setAccount("default");
        trial.setMark1(43);
        trial.setMark2(76);
        trial.setMark3(54);
        assertNotNull(GSON.toJson(trial, Trial.class));
    }


    @Test
    public void deserialize() {
        assertNotNull(GSON.fromJson(TRIAL_JSON_CORRECT, Trial.class));
    }
}