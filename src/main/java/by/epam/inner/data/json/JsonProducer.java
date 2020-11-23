package by.epam.inner.data.json;

import by.epam.inner.data.ConfigurationManager;
import by.epam.inner.data.Resource;
import by.epam.inner.data.TrialFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public final class JsonProducer implements Runnable {

    private final static Logger logger = LogManager.getLogger();

    private final Resource resource;
    private final String fileName;

    public JsonProducer(Resource resource) {
        this.resource = resource;
        this.fileName = ConfigurationManager.getInstance()
                .getProperty("json.path")
                .orElse("trials.json");
    }

    @Override
    public void run() {
        Type type = new TypeToken<List<JsonObject>>(){}.getType();
        try (Reader reader = new FileReader(fileName)) {
            List<JsonObject> jsonObjects = new Gson().fromJson(reader, type);
            jsonObjects.stream()
                    .map(TrialFactory::getTrial)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(resource::put);
            resource.setStatus(true);
        } catch (IOException e) {
            logger.error("File not found " + e);
        }
    }
}
