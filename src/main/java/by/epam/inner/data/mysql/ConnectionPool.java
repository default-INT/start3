package by.epam.inner.data.mysql;

import by.epam.inner.data.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();

    private static final String URL= getUrl();
    private static final String USER = getUser();
    private static final String PASSWORD = getPassword();

    private static final int POOL_SIZE = getPoolSize();
    private static ConnectionPool instance;
    private final Queue<Connection> connections;

    private static String getUrl() {
        return ConfigurationManager.getInstance().getProperty("db.url")
                .orElse("jdbc:mysql://localhost:3306/trials_db?&serverTimezone=UTC");
    }

    private static String getUser() {
        return ConfigurationManager.getInstance().getProperty("db.user")
                .orElse("root");
    }

    private static String getPassword() {
        return ConfigurationManager.getInstance().getProperty("db.password")
                .orElse("1806");
    }

    private static int getPoolSize() {
        return Integer.parseInt(ConfigurationManager.getInstance().getProperty("db.poolSize")
                .orElse("10"));
    }

    public synchronized static ConnectionPool getInstance() {
        if (instance == null) {
            logger.info("create connection pool");
            instance = new ConnectionPool();
        }
        return instance;
    }

    private ConnectionPool(){
        connections = new ArrayDeque<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(URL,  USER, PASSWORD);
                connections.offer(connection);
            } catch (SQLException | ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public synchronized Connection getConnection() {
        return connections.poll();
    }

    public synchronized void closeConnection(Connection connection) {
        if (connection != null){
            connections.offer(connection);
        }
    }
}
