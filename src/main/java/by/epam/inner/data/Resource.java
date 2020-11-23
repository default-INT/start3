package by.epam.inner.data;

import by.epam.inner.beans.Trial;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Resource {
    private static final Logger logger = LogManager.getLogger();
    private Trial resource;
    private boolean status;
    private boolean empty = true;

    public boolean isDone() {return status;}
    public void setStatus(boolean status) {
        this.status = status;
    }

    public synchronized Trial take() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        logger.info("take resource " + resource);
        empty = true;
        notifyAll();
        return resource;
    }

    public synchronized void put(Trial trial) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        logger.info("put resource " + trial);
        empty = false;
        resource = trial;
        notifyAll();
    }
}
