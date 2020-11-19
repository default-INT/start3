package by.epam.inner.data.csv.validators;

import by.epam.inner.beans.Trial;
import by.epam.inner.data.TrialValidator;
import by.epam.inner.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

public class CsvTrialValidator extends TrialValidator {
    private static final Logger logger = LogManager.getLogger();

    protected static final String DELIMITER = ";";

    private final Trial trial;

    public CsvTrialValidator(Class<? extends Trial> trialClass) {
        try {
            trial = trialClass.getConstructor().newInstance();
        }  catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new TrialInitializeException(trialClass);
        }
    }

    protected void checkSizeArgs(String csv, int maxSize) {
        String[] csvArgs = csv.split(DELIMITER);

        if (csvArgs.length < maxSize) {
            throw new IncorrectCsvFormatException(csv);
        }
        if (csvArgs.length > maxSize) {
            Arrays.stream(csvArgs).skip(maxSize).forEach(arg -> logger.warn("Csv has uninspected arg: " + arg));
        }
    }

    protected void checkSizeArgs(String csv) {
        checkSizeArgs(csv, 4);
    }

    protected void checkArgs(String csv) {
        String[] args = csv.split(DELIMITER);
        String account = Optional.ofNullable(args[1])
                .orElseThrow(() -> new EmptyJsonPropertyException("account"));

        if (!accountIsValid(account)) {
            throw new IncorrectAccountFormatException(account);
        }

        int mark1 = Integer.parseInt(Optional.ofNullable(args[2])
                .orElseThrow(() -> new EmptyJsonPropertyException("mark1")));

        int mark2 = Integer.parseInt(Optional.ofNullable(args[3])
                .orElseThrow(() -> new EmptyJsonPropertyException("mark2")));

        if (!markIsValid(mark1) || !markIsValid(mark2)) {
            throw new IncorrectMarkException();
        }

    }

    protected void setArgs(String csv) {
        String[] csvArgs = csv.split(DELIMITER);
        Trial trial = getRowTicket();

        String account = csvArgs[1];
        int mark1 = Integer.parseInt(csvArgs[2]);
        int mark2 = Integer.parseInt(csvArgs[3]);

        trial.setAccount(account);
        trial.setMark1(mark1);
        trial.setMark2(mark2);
    }

    public Trial getValidTrial(String csv) {
        checkSizeArgs(csv);
        checkArgs(csv);
        setArgs(csv);
        return trial;
    }

    @Override
    protected Trial getRowTicket() {
        return trial;
    }
}
