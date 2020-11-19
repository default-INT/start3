package by.epam.inner.data.csv.validators;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.Trial;
import by.epam.inner.exceptions.EmptyJsonPropertyException;
import by.epam.inner.exceptions.IncorrectMarkException;

import java.util.Optional;

public class CsvExtraTrialValidator extends CsvTrialValidator {

    private final ExtraTrial extraTrial;

    public CsvExtraTrialValidator(Class<? extends Trial> trialClass) {
        super(trialClass);
        extraTrial = new ExtraTrial();
    }

    @Override
    protected void checkSizeArgs(String csv) {
        checkSizeArgs(csv, 5);
    }

    @Override
    protected void checkArgs(String csv) {
        super.checkArgs(csv);
        int mark3 = Integer.parseInt(Optional.ofNullable(csv.split(DELIMITER)[4])
                .orElseThrow(() -> new EmptyJsonPropertyException("mark3")));
        if (!markIsValid(mark3)) {
            throw new IncorrectMarkException();
        }
    }

    @Override
    protected void setArgs(String csv) {
        super.setArgs(csv);
        int mark3 = Integer.parseInt(csv.split(DELIMITER)[4]);

        getRowTicket().setMark3(mark3);
    }

    @Override
    protected ExtraTrial getRowTicket() {
        return extraTrial;
    }
}
