package by.epam.inner.data.mysql;

import by.epam.inner.beans.Trial;
import by.epam.inner.data.Resource;
import by.epam.inner.data.TrialFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public final class MySqlProducer implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private static final String FIND_ALL_SQL = "SELECT * FROM trials";

    private final Resource resource;

    public MySqlProducer(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Optional<Trial> optionalTrial = TrialFactory.getTrial(resultSet);
                if (optionalTrial.isPresent()) {
                    Trial trial = optionalTrial.orElseThrow(IllegalStateException::new);
                    resource.put(trial);
                }
            }
            resource.setStatus(true);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
    }
}
