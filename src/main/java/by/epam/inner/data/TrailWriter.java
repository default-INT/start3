package by.epam.inner.data;

import by.epam.inner.beans.ExtraTrial;
import by.epam.inner.beans.LightTrial;
import by.epam.inner.beans.StrongTrial;
import by.epam.inner.beans.Trial;
import by.epam.inner.data.csv.CsvTrialConverter;
import by.epam.inner.data.json.JsonTrialConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.OutputStream;
import java.io.PrintWriter;

public final class TrailWriter {
    private final static Logger logger = LogManager.getLogger();
    private static final JsonTrialConverter TRIAL_CONVERTER = new JsonTrialConverter();

    private static final Gson GSON = getGson();
    private static final CsvTrialConverter CSV = getCsv();

    private static CsvTrialConverter getCsv() {
        return new CsvTrialConverter();
    }
    private static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Trial.class, TRIAL_CONVERTER)
                .registerTypeAdapter(LightTrial.class, TRIAL_CONVERTER)
                .registerTypeAdapter(StrongTrial.class, TRIAL_CONVERTER)
                .registerTypeAdapter(ExtraTrial.class, TRIAL_CONVERTER)
                .create();
    }

    private TrailWriter() {}

    public static void toJson(Trial trial, JsonArray array) {
        JsonElement jsonTrial = GSON.toJsonTree(trial, Trial.class);
        array.add(jsonTrial);
    }

    public static void toCsv(Trial trial, OutputStream out) {
        String csvLine = CSV.toCsv(trial);
        PrintWriter writer = new PrintWriter(out);
        writer.write(csvLine);
        writer.flush();
    }
}
