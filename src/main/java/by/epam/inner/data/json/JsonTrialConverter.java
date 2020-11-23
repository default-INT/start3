package by.epam.inner.data.json;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.LightTrial;
import by.epam.inner.beans.StrongTrial;
import by.epam.inner.beans.Trial;
import by.epam.inner.data.json.validators.JsonExtraTrialValidator;
import by.epam.inner.data.json.validators.JsonTrialValidator;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.function.Supplier;

public class JsonTrialConverter implements JsonDeserializer<Trial>, JsonSerializer<Trial> {

    private enum TrialKind {
        TRIAL(() -> new JsonTrialValidator(Trial.class)),
        LIGHT_TRIAL(() -> new JsonTrialValidator(LightTrial.class)),
        STRONG_TRIAL(() -> new JsonTrialValidator(StrongTrial.class)),
        EXTRA_TRIAL(() -> new JsonExtraTrialValidator(ExtraTrial.class));

        private final Supplier<JsonTrialValidator> validatorGetter;

        TrialKind(Supplier<JsonTrialValidator> validator) {
            this.validatorGetter = validator;
        }

        public Trial getTrial(JsonElement element) {
            return validatorGetter.get().getValidTrial(element);
        }
    }

    private final static Gson DEFAULT_GSON = new Gson();


    @Override
    public JsonElement serialize(Trial trial, Type type, JsonSerializationContext jsonSerializationContext) {
        String[] trialsPackage = type.getTypeName().split("\\.");
        String trialClass = trialsPackage[trialsPackage.length - 1];

        JsonObject trialJsonObject = new JsonObject();
        trialJsonObject.addProperty("class", trialClass);

        JsonElement argsJsonObject = DEFAULT_GSON.toJsonTree(trial, trial.getClass());
        trialJsonObject.add("args", argsJsonObject);

        return trialJsonObject;
    }

    @Override
    public Trial deserialize(JsonElement jsonElement, Type type,
                             JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        // method 1
        String[] trialsPackage = type.getTypeName().split("\\.");
        String trialClass = trialsPackage[trialsPackage.length - 1];

        String trialKind = trialClass.replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .toUpperCase();

        return TrialKind.valueOf(trialKind).getTrial(jsonElement);
    }

}
