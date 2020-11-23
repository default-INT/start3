package by.epam.inner.data.csv;

import by.epam.inner.data.ConfigurationManager;
import by.epam.inner.data.Resource;
import by.epam.inner.data.TrialFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CsvProducer implements Runnable {
    private final static Logger logger = LogManager.getLogger();

    private final Resource resource;
    private final String fileName;

    public CsvProducer(Resource resource) {
        this.resource = resource;
        this.fileName = ConfigurationManager.getInstance()
                .getProperty("csv.path")
                .orElse("trials.csv");
    }

    @Override
    public void run() {
        try (Stream<String> csvLines = Files.lines(Paths.get(fileName))) {
            csvLines.map(TrialFactory::getTrial)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet())
                    .forEach(resource::put);
            resource.setStatus(true);
        } catch (IOException e) {
            logger.error("File not found " + e);
        }
    }
}
