package by.epam.inner.data.csv;

import by.epam.inner.beans.Trial;
import by.epam.inner.data.ConfigurationManager;
import by.epam.inner.data.Resource;
import by.epam.inner.data.TrailWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class CsvConsumer implements Runnable {
    private final static Logger logger = LogManager.getLogger();

    private final Resource resource;
    private final String fileName;

    public CsvConsumer(Resource resource) {
        this.resource = resource;
        this.fileName = ConfigurationManager.getInstance()
                .getProperty("csv.path")
                .orElse("trials.csv");
    }

    @Override
    public void run() {
        try (OutputStream out = new FileOutputStream(fileName)) {
            while (!resource.isDone()) {
                Trial trial = resource.take();
                TrailWriter.toCsv(trial, out);
            }
        } catch (IOException e) {
            logger.error("File not found " + e);
        }
    }
}
