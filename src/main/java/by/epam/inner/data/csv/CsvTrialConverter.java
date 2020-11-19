package by.epam.inner.data.csv;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.LightTrial;
import by.epam.inner.beans.StrongTrial;
import by.epam.inner.beans.Trial;
import by.epam.inner.data.csv.validators.CsvExtraTrialValidator;
import by.epam.inner.data.csv.validators.CsvTrialValidator;

import java.lang.reflect.Type;

public class CsvTrialConverter {
    private enum TrialKind {
        TRIAL(new CsvTrialValidator(Trial.class)),
        LIGHT_TRIAL(new CsvTrialValidator(LightTrial.class)),
        STRONG_TRIAL(new CsvTrialValidator(StrongTrial.class)),
        EXTRA_TRIAL(new CsvExtraTrialValidator(ExtraTrial.class));

        private final CsvTrialValidator validator;

        TrialKind(CsvTrialValidator validator) {
            this.validator = validator;
        }

        public Trial getTrial(String csv) {
            return validator.getValidTrial(csv);
        }
    }

    //TODO: copy past
    public Trial fromCsv(String csvString, Type type) {
        String[] trialsPackage = type.getTypeName().split("\\.");
        String trialClass = trialsPackage[trialsPackage.length - 1];

        String trialKind = trialClass.replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .toUpperCase();
        return TrialKind.valueOf(trialKind).getTrial(csvString);
    }

    public String toCsv(Trial trial) {
        return trial.toCsv();
    }
}
