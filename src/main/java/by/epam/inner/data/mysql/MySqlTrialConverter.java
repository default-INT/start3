package by.epam.inner.data.mysql;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.LightTrial;
import by.epam.inner.beans.StrongTrial;
import by.epam.inner.beans.Trial;

import java.lang.reflect.Type;
import java.sql.ResultSet;

public class MySqlTrialConverter {
    private enum TrialKind {
        TRIAL(new MySqlTrialValidator(Trial.class)),
        LIGHT_TRIAL(new MySqlTrialValidator(LightTrial.class)),
        STRONG_TRIAL(new MySqlTrialValidator(StrongTrial.class)),
        EXTRA_TRIAL(new MySqlTrialValidator(ExtraTrial.class));

        private final MySqlTrialValidator validator;

        TrialKind(MySqlTrialValidator validator) {
            this.validator = validator;
        }

        public Trial getTrial(ResultSet resultSet) {
            return validator.getValidTrial(resultSet);
        }
    }

    public Trial fromDb(ResultSet resultSet, Type type) {
        String[] trialsPackage = type.getTypeName().split("\\.");
        String trialClass = trialsPackage[trialsPackage.length - 1];

        String trialKind = trialClass.replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .toUpperCase();
        return TrialKind.valueOf(trialKind).getTrial(resultSet);
    }
}
