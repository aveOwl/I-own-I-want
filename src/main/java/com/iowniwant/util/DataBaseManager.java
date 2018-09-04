package com.iowniwant.util;

import com.iowniwant.util.exceptions.ConnectionException;
import com.iowniwant.util.exceptions.PropertyAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Provides connection to the database, manages queries access.
 */
public class DataBaseManager {
    private static final Logger LOG = LoggerFactory.getLogger(DataBaseManager.class);
    private static final String JNDI_DATA_SOURCE = "java:/jdbc/data-postgres";
    private static final String QUERY_PROPERTIES_LOCATION = "/queries.properties";

    private static DataBaseManager instance;

    private Properties queries;

    private Connection dbConnection;

    /**
     * Provides DataBaseManager instance.
     * @return the same DataBaseManager object each time its invoked.
     */
    public static DataBaseManager getInstance() {
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }

    private DataBaseManager() {
        LOG.trace("Loading properties...");
        this.loadProperties();
    }

    /**
     * Establishes connection to the DataBase.
     * @return connection to the DataBase.
     */
    public Connection getDbConnection() {
        try {
            this.getConnectionFromContext();
        } catch (Exception e) {
            LOG.error("Failed to establish connection with the datasource {} Error: {}",
                    JNDI_DATA_SOURCE, e.getClass().getCanonicalName(), e.getMessage());
            throw new ConnectionException(
                    "Failed to establish connection with the datasource: " + JNDI_DATA_SOURCE, e);
        }
        return this.dbConnection;
    }

    private Connection getConnectionFromContext() throws Exception {
        Context context = new InitialContext();

        LOG.debug("Fetching DataSource by JNDI lookup: {}", JNDI_DATA_SOURCE);

        DataSource ds = (DataSource) context.lookup(JNDI_DATA_SOURCE);
        this.dbConnection = ds.getConnection();

        LOG.info("Establishing connection...");

        return this.dbConnection;
    }

    public String getQuery(String name) {
        LOG.debug("Requested query: {}", name);
        if (this.queries == null)
            loadProperties();
        return this.queries.getProperty(name);
    }

    /**
     * Provides ability to load SQL queries,
     * by reading it from properties file
     * and assigning it to {@link Properties} object.
     */
    private void loadProperties() {
        try (InputStream is = getClass().getResourceAsStream(QUERY_PROPERTIES_LOCATION)) {
            this.queries = new Properties();
            this.queries.load(is);
        } catch (IOException e) {
            LOG.error("Failed to access query properties from resource: {}", QUERY_PROPERTIES_LOCATION);
            throw new PropertyAccessException(
                    "Failed to access query properties from resource: " + QUERY_PROPERTIES_LOCATION, e);
        }
    }
}
