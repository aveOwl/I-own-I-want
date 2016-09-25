package com.iowniwant.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Provides connection to the database, manages queries access.
 */
public class DataBaseManager {
    private static final Logger LOG = LoggerFactory.getLogger(DataBaseManager.class);

    private static final String JNDI_DATA_SOURCE = "java:/jdbc/data-postgres";

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
        LOG.trace("loading properties");
        loadProperties();
    }

    /**
     * Establishes dbConnection to the DataBase.
     * @return dbConnection to the DataBase.
     */
    public Connection getDbConnection() {
        try {
            Context context = new InitialContext();
            LOG.debug("Fetching DataSource by jndi lookup: {}", JNDI_DATA_SOURCE);
            DataSource ds = (DataSource) context.lookup(JNDI_DATA_SOURCE);
            this.dbConnection = ds.getConnection();
            LOG.debug("Establishing dbConnection ...");
        } catch (SQLException | NamingException e) {
            LOG.error("{}: {}", e.getClass().getCanonicalName(), e.getMessage());
            e.printStackTrace();
        }
        return this.dbConnection;
    }

    /**
     * @param name query name from the queries.properties.
     * @return query by the given identifier name.
     */
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
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream("/queries.properties");
            this.queries = new Properties();
            this.queries.load(is);
        } catch (IOException e) {
            LOG.error("{}: {}", e.getClass().getCanonicalName(), e.getMessage());
            e.printStackTrace();
        } finally {
            if (is != null)  try { is.close(); } catch (IOException ignored) {}
        }
    }
}
