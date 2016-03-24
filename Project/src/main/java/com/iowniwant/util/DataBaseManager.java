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

public class DataBaseManager {
    public static final Logger log = LoggerFactory.getLogger(DataBaseManager.class);

    Properties queries;

    public DataBaseManager() {
        log.trace("loading properties");
        loadProperties();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:/jbdc/data-postgres");
            connection = ds.getConnection();
            log.debug("getting connection: {}", connection);
        } catch (SQLException | NamingException e) {
            log.error("{}: {}", e.getClass().getCanonicalName(), e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public String getQuery(String name) {
        log.debug("Get query: {}", name);
        if (queries == null) {
            loadProperties();
        }
        String q = queries.getProperty(name);
        log.debug("q = {}", q);
        return q;
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
        }
    }
}