package by.epam.inner.data.mysql;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.Trial;
import by.epam.inner.exceptions.IncorrectMarkException;
import by.epam.inner.exceptions.SQLNotFoundPropertyException;

import java.sql.ResultSet;
import java.sql.SQLException;


class MySqlExtraTrialValidator extends MySqlTrialValidator {
    private final ExtraTrial extraTrial;

    public MySqlExtraTrialValidator(Class<? extends Trial> trialClass) {
        super(trialClass);
        extraTrial = new ExtraTrial();
    }

    @Override
    protected ExtraTrial getRowTrial() {
        return extraTrial;
    }

    @Override
    protected void checkArgs(ResultSet resultSet) {
        super.checkArgs(resultSet);
        try {
            int mark3 = resultSet.getInt("mark3");

            if (!markIsValid(mark3)) {
                throw new IncorrectMarkException();
            }
        } catch (SQLException e) {
            throw new SQLNotFoundPropertyException(e);
        }
    }

    @Override
    protected void setArgs(ResultSet resultSet) {
        super.setArgs(resultSet);
        try {
            int mark3 = resultSet.getInt("mark3");

            getRowTrial().setMark3(mark3);
        } catch (SQLException e) {
            throw new SQLNotFoundPropertyException(e);
        }
    }
}
