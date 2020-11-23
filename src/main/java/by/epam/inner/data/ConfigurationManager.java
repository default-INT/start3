package by.epam.inner.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Optional;
import java.util.Properties;

public class ConfigurationManager {
    private static final Logger logger = LogManager.getLogger();
    private static final Properties PROP = getProperties();

    private static String propsPath = "application.properties";
    private static ConfigurationManager instance;

    private static Properties getProperties() {
        return new Properties();
    }

    private ConfigurationManager() {
    }

    public void setPropsPath(String path) {
        propsPath = path;
    }

    public synchronized static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public Optional<String> getProperty(String key) {
        try (InputStream in = new FileInputStream(propsPath)) {
            PROP.load(in);
            String value = PROP.getProperty(key);
            if (value == null  || value.trim().isEmpty()) {
                logger.warn("Property '" + key + "' not found");
                return Optional.empty();
            }
            return Optional.of(value);
        } catch (IOException e) {
            logger.error("Properties file not found " + e.getMessage());
            return Optional.empty();
        }
    }
}
