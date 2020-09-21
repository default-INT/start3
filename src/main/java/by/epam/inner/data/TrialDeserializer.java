package by.epam.inner.data;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.LightTrial;
import by.epam.inner.beans.StrongTrial;
import by.epam.inner.beans.Trial;
import by.epam.inner.data.validators.ExtraTrialValidator;
import by.epam.inner.data.validators.TrialValidator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class TrialDeserializer implements JsonDeserializer<Trial> {

    private enum TrialKind {
        TRIAL(new TrialValidator(Trial.class)),
        LIGHT_TRIAL(new TrialValidator(LightTrial.class)),
        STRONG_TRIAL(new TrialValidator(StrongTrial.class)),
        EXTRA_TRIAL(new ExtraTrialValidator(ExtraTrial.class));

        private final TrialValidator validator;

        TrialKind(TrialValidator validator) {
            this.validator = validator;
        }

        public Trial getTrial(JsonElement element) {
            return validator.getValidTrial(element);
        }
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
