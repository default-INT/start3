package by.epam.inner.data;

import by.epam.inner.beans.Trial;

<<<<<<< Updated upstream
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
=======
import java.lang.reflect.InvocationTargetException;
>>>>>>> Stashed changes
import java.util.regex.Pattern;

public abstract class TrialValidator {
    protected final static Pattern ACCOUNT_PATTERN = Pattern.compile("^[A-Za-z]([.A-Za-z0-9-]{1,18})([A-Za-z0-9])$");

<<<<<<< Updated upstream
    protected abstract Trial getRowTicket();
=======
    private final Trial trial;

    public TrialValidator(Class<? extends Trial> trialClass) {
        try {
            this.trial = trialClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new TrialInitializeException(trialClass);
        }
    }

    protected Trial getRowTrial() {
        return trial;
    }
>>>>>>> Stashed changes

    protected static boolean markIsValid(int mark) {
        return mark > 0 && mark < 100;
    }

    protected static boolean accountIsValid(String account) {
        return ACCOUNT_PATTERN.matcher(account).find();
    }
}
