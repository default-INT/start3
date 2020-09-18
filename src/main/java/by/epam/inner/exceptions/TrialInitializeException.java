package by.epam.inner.exceptions;

import by.epam.inner.beans.Trial;

public class TrialInitializeException extends IllegalArgumentException {
    private final Class<? extends Trial> trial;

    public TrialInitializeException(Class<? extends Trial> trial) {
        this.trial = trial;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ". Trial class not initialize = " + trial;
    }

    public Class<? extends Trial> getTrial() {
        return trial;
    }
}
