package by.epam.inner;

import by.epam.inner.data.ConfigurationManager;
import by.epam.inner.data.Resource;
import by.epam.inner.data.csv.CsvConsumer;
import by.epam.inner.data.csv.CsvProducer;
import by.epam.inner.data.json.JsonConsumer;
import by.epam.inner.data.json.JsonProducer;
import by.epam.inner.data.mysql.MySqlProducer;

import java.lang.reflect.InvocationTargetException;

public class ThreadsRunner {

    private enum ConsumerType {
        JSON(JsonConsumer.class),
        CSV(CsvConsumer.class);
        private final Class<? extends Runnable> consumerClass;

        ConsumerType(Class<? extends Runnable> consumerClass) {
            this.consumerClass = consumerClass;
        }

        public Runnable getConsumer(Resource resource) {
            try {
                return consumerClass.getConstructor(resource.getClass())
                        .newInstance(resource);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new IllegalStateException();
            }
        }
    }

    private enum ProducerType {
        JSON(JsonProducer.class),
        CSV(CsvProducer.class),
        DB(MySqlProducer.class);
        private final Class<? extends Runnable> producerClass;

        ProducerType(Class<? extends Runnable> producerClass) {
            this.producerClass = producerClass;
        }

        public Runnable getProducer(Resource resource) {
            try {
                return producerClass.getConstructor(resource.getClass())
                        .newInstance(resource);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new IllegalStateException();
            }
        }
    }

    public static void main(String[] args) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        configurationManager.setPropsPath(args[0]);
        final Resource resource = new Resource();
        String consumerKind = configurationManager.getProperty("consumer")
                .orElse("json")
                .toUpperCase();
        String producerKind = configurationManager.getProperty("producer")
                .orElse("db")
                .toUpperCase();
        ProducerType producerType = ProducerType.valueOf(producerKind);
        ConsumerType consumerType = ConsumerType.valueOf(consumerKind);
        new Thread(consumerType.getConsumer(resource))
                .start();
        new Thread(producerType.getProducer(resource))
                .start();
    }
}
