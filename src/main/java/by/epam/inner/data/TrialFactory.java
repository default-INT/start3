package by.epam.inner.data;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.LightTrial;
import by.epam.inner.beans.StrongTrial;
import by.epam.inner.beans.Trial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.Optional;

public final class TrialFactory {

    private static final TrialDeserializer TRIAL_DESERIALIZER = new TrialDeserializer();

    private final static Logger logger = LogManager.getLogger();
    private final static Gson GSON = getGson();

    private static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Trial.class, TRIAL_DESERIALIZER)
                .registerTypeAdapter(LightTrial.class, TRIAL_DESERIALIZER)
                .registerTypeAdapter(StrongTrial.class, TRIAL_DESERIALIZER)
                .registerTypeAdapter(ExtraTrial.class, TRIAL_DESERIALIZER)
                .create();
    }

    private TrialFactory() {}

    public static Optional<Trial> getTrial(JsonObject trialJsonObj) {
        try {
            Type trialType = Class.forName("by.epam.inner.beans." + trialJsonObj.get("class").getAsString());
            return Optional.of(GSON.fromJson(trialJsonObj, trialType));
        } catch (JsonSyntaxException e) {
            logger.error("Json have incorrect syntax " + e + "| JSON object: " + trialJsonObj);
            return Optional.empty();
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error(e);
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            logger.error("Class not found. Message = " + e.getMessage());
            return Optional.empty();
        }
    }
}
