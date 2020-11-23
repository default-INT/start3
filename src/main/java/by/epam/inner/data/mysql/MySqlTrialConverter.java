package by.epam.inner.data.mysql;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.LightTrial;
import by.epam.inner.beans.StrongTrial;
import by.epam.inner.beans.Trial;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.function.Supplier;

public class MySqlTrialConverter {
    private enum TrialKind {
        TRIAL(() -> new MySqlTrialValidator(Trial.class)),
        LIGHT_TRIAL(() -> new MySqlTrialValidator(LightTrial.class)),
        STRONG_TRIAL(() -> new MySqlTrialValidator(StrongTrial.class)),
        EXTRA_TRIAL(() -> new MySqlExtraTrialValidator(ExtraTrial.class));

        private final Supplier<MySqlTrialValidator> validator;

        TrialKind(Supplier<MySqlTrialValidator> validator) {
            this.validator = validator;
        }

        public Trial getTrial(ResultSet resultSet) {
            return validator.get().getValidTrial(resultSet);
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
