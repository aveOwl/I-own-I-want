package com.iowniwant.dao.implementation;

import com.iowniwant.dao.AbstractDAO;
import com.iowniwant.util.DataBaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements basic CRUD operations using
 * @see DataBaseManager to get connection to the DataBase.
 * @param <T> type of object that implements
 * @see Serializable interface.
 */

abstract class AbstractDaoImpl<T extends Serializable> implements AbstractDAO<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDaoImpl.class);

    DataBaseManager dbManager = DataBaseManager.getInstance();

    private Connection dbConnection;

    private PreparedStatement prepStatement;

    private ResultSet resultSet;

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(T entity) {
        try {
            dbConnection = dbManager.getDbConnection();
            String query = getCreateQuery();
            prepStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            fillCreateStatement(prepStatement, entity);
            int i = prepStatement.executeUpdate();
            LOG.debug("is creation executed : {}", i == 1);

            resultSet = prepStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long generatedID = resultSet.getLong(1);
                LOG.debug("creating entity with id: {}", generatedID);
                return getById(generatedID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException ignored) {}
            if (prepStatement != null)      try { prepStatement.close(); } catch (SQLException ignored) {}
            if (dbConnection != null) try { dbConnection.close(); } catch (SQLException ignored) {}
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        try {
            dbConnection = dbManager.getDbConnection();
            String query = getDeleteQuery();
            prepStatement = dbConnection.prepareStatement(query);
            prepStatement.setLong(1, id);
            LOG.debug("deleting entity with id: {}", id);
            prepStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (prepStatement != null)      try { prepStatement.close(); } catch (SQLException ignored) {}
            if (dbConnection != null) try { dbConnection.close(); } catch (SQLException ignored) {}
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T entity) {
        try {
            dbConnection = dbManager.getDbConnection();
            String query = getUpdateQuery();
            prepStatement = dbConnection.prepareStatement(query);
            fillUpdateStatement(prepStatement, entity);
            prepStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (prepStatement != null) try { prepStatement.close(); } catch (SQLException ignored) {}
            if (dbConnection != null) try { dbConnection.close(); } catch (SQLException ignored) {}
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getById(Long id) {
        try {
            dbConnection = dbManager.getDbConnection();
            String query = getGetByIdQuery();
            prepStatement = dbConnection.prepareStatement(query);
            prepStatement.setLong(1, id);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                return getEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException ignored) {}
            if (prepStatement != null)  try { prepStatement.close(); } catch (SQLException ignored) {}
            if (dbConnection != null) try { dbConnection.close(); } catch (SQLException ignored) {}
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        try {
            dbConnection = dbManager.getDbConnection();
            String query = getGetAllQuery();
            prepStatement = dbConnection.prepareStatement(query);
            resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                list.add(getEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException ignored) {}
            if (prepStatement != null) try { prepStatement.close(); } catch (SQLException ignored) {}
            if (dbConnection != null) try { dbConnection.close(); } catch (SQLException ignored) {}
        }
        return list;
    }

    public abstract void fillCreateStatement(PreparedStatement prepStatement, T entity);
    public abstract void fillUpdateStatement(PreparedStatement prepStatement, T entity);
    public abstract T getEntity(ResultSet resultSet);

    public abstract String getCreateQuery();
    public abstract String getDeleteQuery();
    public abstract String getUpdateQuery();
    public abstract String getGetByIdQuery();
    public abstract String getGetAllQuery();
}
