package com.iowniwant.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Provides connection to the DataBase, manages queries access.
 */
public class DataBaseManager {
    private static final Logger log = LoggerFactory.getLogger(DataBaseManager.class);
    private static DataBaseManager instance;
    private Properties queries;

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
        log.trace("loading properties");
        loadProperties();
    }

    /**
     * Establishes connection to the DataBase.
     * @return connection to the DataBase.
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:/jbdc/data-postgres");
            connection = ds.getConnection();
            log.debug("Establishing connection: {}", connection.getMetaData().getURL());
        } catch (SQLException | NamingException e) {
            log.error("{}: {}", e.getClass().getCanonicalName(), e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * @param name query name from the queries.properties.
     * @return query by the given identifier name.
     */
    public String getQuery(String name) {
        log.debug("Requested query: {}", name);
        if (queries == null)
            loadProperties();
        return queries.getProperty(name);
    }

    private void loadProperties() {
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream("/queries.properties");
            queries = new Properties();
            queries.load(is);
        } catch (IOException e) {
            log.error("{}: {}", e.getClass().getCanonicalName(), e.getMessage());
            e.printStackTrace();
        } finally {
            if (is != null)  try { is.close(); } catch (IOException ignored) {}
        }
    }
}
