package by.epam.inner.data.validators;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.Trial;
import by.epam.inner.exceptions.EmptyJsonPropertyException;
import by.epam.inner.exceptions.IncorrectMarkException;
import by.epam.inner.exceptions.TrialInitializeException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ExtraTrialValidator extends TrialValidator {

    private final ExtraTrial extraTrial;

    public ExtraTrialValidator(Class<ExtraTrial> trialClass) {
        super(trialClass);
        this.extraTrial = new ExtraTrial();
    }

    @Override
    protected void checkArgs(JsonElement element) {
        super.checkArgs(element);
        JsonObject jsonArgs = element.getAsJsonObject().get("args")
                .getAsJsonObject();
        int mark3 = Optional.ofNullable(jsonArgs.get("mark3"))
                .orElseThrow(() -> new EmptyJsonPropertyException("mark3"))
                .getAsInt();
        if (markCheck(mark3)) {
            throw new IncorrectMarkException();
        }
    }


    @Override
    protected void setArgs(JsonElement element) {
        super.setArgs(element);
        JsonObject argsJson = element.getAsJsonObject().get("args").getAsJsonObject();

        int mark3 = argsJson.get("mark3").getAsInt();

        getRowTicket().setMark3(mark3);
    }

    @Override
    protected ExtraTrial getRowTicket() {
        return extraTrial;
    }
}
