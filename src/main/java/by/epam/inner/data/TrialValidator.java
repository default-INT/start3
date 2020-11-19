package by.epam.inner.data;

import by.epam.inner.beans.Trial;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public abstract class TrialValidator {
    protected final static Pattern ACCOUNT_PATTERN = Pattern.compile("^[A-Za-z]([.A-Za-z0-9-]{1,18})([A-Za-z0-9])$");

    protected abstract Trial getRowTicket();

    protected static boolean markIsValid(int mark) {
        return mark > 0 && mark < 100;
    }

    protected static boolean accountIsValid(String account) {
        return ACCOUNT_PATTERN.matcher(account).find();
    }

    protected static List<Field> getFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        if (!type.equals(Object.class)) {
            fields.addAll(Arrays.asList(type.getDeclaredFields()));
            fields.addAll(getFields(type.getSuperclass()));
        }
        return fields;
    }
}
