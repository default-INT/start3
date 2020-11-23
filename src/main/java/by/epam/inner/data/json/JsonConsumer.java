package by.epam.inner.data.json;

import by.epam.inner.beans.Trial;
import by.epam.inner.data.ConfigurationManager;
import by.epam.inner.data.Resource;
import by.epam.inner.data.TrailWriter;
import com.google.gson.JsonArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public final class JsonConsumer implements Runnable {
    private final static Logger logger = LogManager.getLogger();

    private final Resource resource;
    private final String fileName;

    public JsonConsumer(Resource resource) {
        this.resource = resource;
        this.fileName = ConfigurationManager.getInstance()
                .getProperty("json.path")
                .orElse("trials.json");
    }

    @Override
    public void run() {
        try (Writer out = new PrintWriter(fileName)) {
            JsonArray jsonArray = new JsonArray();
            while (!resource.isDone()) {
                Trial trial = resource.take();
                logger.info("write to json " + trial);
                TrailWriter.toJson(trial, jsonArray);
            }
            out.write(jsonArray.toString());
            out.flush();

        } catch (IOException e) {
            logger.error("File not found " + e);
        }
    }
}
