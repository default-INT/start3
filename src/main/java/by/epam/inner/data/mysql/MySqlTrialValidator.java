package by.epam.inner.data.mysql;

import by.epam.inner.beans.Trial;
import by.epam.inner.data.TrialValidator;
import by.epam.inner.exceptions.IncorrectAccountFormatException;
import by.epam.inner.exceptions.IncorrectMarkException;
import by.epam.inner.exceptions.SQLNotFoundPropertyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

class MySqlTrialValidator extends TrialValidator {
    private static final Logger logger = LogManager.getLogger() ;

    public MySqlTrialValidator(Class<? extends Trial> trialClass) {
        super(trialClass);
    }

    protected void checkArgs(ResultSet resultSet) {
        try {
            String account = resultSet.getString("account");
            int mark1 = resultSet.getInt("mark1");
            int mark2 = resultSet.getInt("mark2");

            if (!accountIsValid(account)) {
                throw new IncorrectAccountFormatException(account);
            }

            if (!markIsValid(mark1) || !markIsValid(mark2)) {
                throw new IncorrectMarkException();
            }
        } catch (SQLException e) {
            throw new SQLNotFoundPropertyException(e);
        }
    }

    protected void setArgs(ResultSet resultSet) {
        try {
            String account = resultSet.getString("account");
            int mark1 = resultSet.getInt("mark1");
            int mark2 = resultSet.getInt("mark2");

            getRowTrial().setAccount(account);
            getRowTrial().setMark1(mark1);
            getRowTrial().setMark2(mark2);
        } catch (SQLException e) {
            throw new SQLNotFoundPropertyException(e);
        }
    }

    public Trial getValidTrial(ResultSet resultSet) {
        checkArgs(resultSet);
        setArgs(resultSet);
        return getRowTrial();
    }
}
