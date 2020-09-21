package by.epam.inner.data.validators;

import by.epam.inner.beans.Trial;
import by.epam.inner.exceptions.EmptyJsonPropertyException;
import by.epam.inner.exceptions.IncorrectAccountFormatException;
import by.epam.inner.exceptions.IncorrectMarkException;
import by.epam.inner.exceptions.TrialInitializeException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Pattern;

public class TrialValidator {
    private static final Logger logger = LogManager.getLogger();
    private final static Pattern ACCOUNT_PATTERN = Pattern.compile("^[A-Za-z]([.A-Za-z0-9-]{1,18})([A-Za-z0-9])$");

    private static List<Field> getFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        if (!type.equals(Object.class)) {
            fields.addAll(Arrays.asList(type.getDeclaredFields()));
            fields.addAll(getFields(type.getSuperclass()));
        }
        return fields;
    }

    protected static boolean markCheck(int mark) {
        return mark < 0 || mark > 100;
    }

    private final Trial trial;

    public TrialValidator(Class<? extends Trial> trialClass) {
        try {
            this.trial = trialClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new TrialInitializeException(trialClass);
        }
    }

    private void propertyCheck(Map.Entry<String, JsonElement> entry, List<Field> fieldList) {
        if (fieldList.stream().noneMatch(f -> f.getName().equals(entry.getKey()))) {
            logger.warn("Uninspected arg: " + entry.getKey());
        } else if (entry.getValue().getAsString().isEmpty()) {
            logger.error("Value is empty. Key = " + entry.getKey());
            throw new NullPointerException("Value is empty. Key = " + entry.getKey());
        }
    }

    protected void checkSizeArgs(JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();
        if (jsonObject.size() < 2) {
            throw new IllegalArgumentException();
        } else if (jsonObject.size() > 2) {
            logger.warn("Json object has unexpected arguments.");
        }
        JsonObject argsJson = Optional.ofNullable(jsonObject.get("args"))
                .orElseThrow(() -> new JsonSyntaxException("Property 'args' not found."))
                .getAsJsonObject();

        List<Field> fieldList = getFields(trial.getClass());

        argsJson.entrySet().forEach(entry -> propertyCheck(entry, fieldList));
    }

    protected void checkArgs(JsonElement element) {
        JsonObject argsJson = element.getAsJsonObject().get("args").getAsJsonObject();
        String account = Optional.ofNullable(argsJson.get("account"))
                .orElseThrow(() -> new EmptyJsonPropertyException("account"))
                .getAsString();
        if (!ACCOUNT_PATTERN.matcher(account).find()) {
            throw new IncorrectAccountFormatException(account);
        }

        int mark1 = Optional.ofNullable(argsJson.get("mark1"))
                .orElseThrow(() -> new EmptyJsonPropertyException("mark1"))
                .getAsInt();
        int mark2 = Optional.ofNullable(argsJson.get("mark2"))
                .orElseThrow(() -> new EmptyJsonPropertyException("mark2"))
                .getAsInt();

        if (markCheck(mark1) || markCheck(mark2)) {
            throw new IncorrectMarkException();
        }
    }

    protected void setArgs(JsonElement element) {
        JsonObject argsJson = element.getAsJsonObject().get("args").getAsJsonObject();
        Trial rowTrial = getRowTicket();

        String account = argsJson.get("account").getAsString();
        int mark1 = argsJson.get("mark1").getAsInt();
        int mark2 = argsJson.get("mark2").getAsInt();

        rowTrial.setAccount(account);
        rowTrial.setMark1(mark1);
        rowTrial.setMark2(mark2);
    }

    public Trial getValidTrial(JsonElement element) {
        checkSizeArgs(element);
        checkArgs(element);
        setArgs(element);
        return getRowTicket();
    }

    protected Trial getRowTicket() {
        return trial;
    }
}
