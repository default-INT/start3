package by.epam.inner.data;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.LightTrial;
import by.epam.inner.beans.StrongTrial;
import by.epam.inner.beans.Trial;
import by.epam.inner.data.csv.CsvTrialConverter;
import by.epam.inner.data.json.JsonTrialConverter;
import by.epam.inner.exceptions.EmptyCsvPropertyException;
import by.epam.inner.exceptions.EmptyJsonPropertyException;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.Optional;

public final class TrialFactory {

    private static final JsonTrialConverter TRIAL_DESERIALIZER = new JsonTrialConverter();
    private static final String PACKAGE_NAME = "by.epam.inner.beans.";
    private static final String DELIMITER = ";";

    private final static Logger logger = LogManager.getLogger();

    private final static Gson GSON = getGson();
    private static final CsvTrialConverter CSV = getCsv();

    private static CsvTrialConverter getCsv() {
        return new CsvTrialConverter();
    }
    private static Gson getGson() {
        return new GsonBuilder ()
                .registerTypeAdapter(Trial.class, TRIAL_DESERIALIZER)
                .registerTypeAdapter(LightTrial.class, TRIAL_DESERIALIZER)
                .registerTypeAdapter(StrongTrial.class, TRIAL_DESERIALIZER)
                .registerTypeAdapter(ExtraTrial.class, TRIAL_DESERIALIZER)
                .create();
    }

    private TrialFactory() {}

    public static Optional<Trial> getTrial(JsonObject trialJsonObj) {
        try {
            JsonElement classProp = Optional.ofNullable(trialJsonObj.get("class"))
                    .orElseThrow(() -> new EmptyJsonPropertyException("class"));
            Type trialType = Class.forName(PACKAGE_NAME + classProp.getAsString());
            return Optional.of(GSON.fromJson(trialJsonObj, trialType));
        } catch (JsonSyntaxException e) {
            logger.error("Json have incorrect syntax " + e + "| JSON object: " + trialJsonObj);
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            logger.error(e);
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            logger.error("Class not found. Message = " + e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<Trial> getTrial(String csvTrial) {
        try {
            String classProp = Optional.ofNullable(csvTrial.split(DELIMITER, 1)[0])
                    .orElseThrow(() -> new EmptyCsvPropertyException("class"));
            Type trialType = Class.forName(PACKAGE_NAME + classProp);
            return Optional.of(CSV.fromCsv(csvTrial, trialType));
        } catch (ClassNotFoundException e) {
            logger.error("Class not found. Message = " + e.getMessage());
            return Optional.empty();
        }
    }
}