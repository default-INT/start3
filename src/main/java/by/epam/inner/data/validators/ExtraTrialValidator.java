package by.epam.inner.data.validators;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.Trial;
import by.epam.inner.exceptions.IncorrectMarkException;
import by.epam.inner.exceptions.TrialInitializeException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;

public class ExtraTrialValidator extends TrialValidator {

    private final ExtraTrial extraTrial;

    public ExtraTrialValidator(Class<ExtraTrial> trialClass) {
        super(trialClass);
        try {
            this.extraTrial = trialClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new TrialInitializeException(trialClass);
        }
    }

    @Override
    protected void checkArgs(JsonElement element) {
        super.checkArgs(element);
        int mark3 = element.getAsJsonObject().get("args")
                .getAsJsonObject().get("mark3").getAsInt();
        if (markCheck(mark3)) {
            throw new IncorrectMarkException();
        }
    }

    protected void setArgs(ExtraTrial trial, JsonElement element) {
        super.setArgs(trial, element);
        JsonObject args = element.getAsJsonObject().get("args").getAsJsonObject();
        extraTrial.setMark3(args.get("mark3").getAsInt());
    }

    @Override
    public ExtraTrial getValidTrial(JsonElement element) {
        super.checkSizeArgs(element);
        checkArgs(element);
        setArgs(extraTrial, element);
        return extraTrial;
    }
}
