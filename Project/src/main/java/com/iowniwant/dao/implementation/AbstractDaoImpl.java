package com.iowniwant.dao.implementation;

import com.iowniwant.dao.AbstractDAO;
import com.iowniwant.util.DataBaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements basic CRUD operations using
 * @see DataBaseManager to get connection to the DataBase.
 * @param <T> type of object that implements
 * @see Serializable interface.
 */

abstract class AbstractDaoImpl<T extends Serializable> implements AbstractDAO<T> {
    private static final Logger log = LoggerFactory.getLogger(AbstractDaoImpl.class);
    DataBaseManager dbManager = DataBaseManager.getInstance();

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(T entity) {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            String query = getCreateQuery();
            prepStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            fillCreateStatement(prepStatement, entity);
            int i = prepStatement.executeUpdate();
            log.debug("is creation executed : {}", i == 1);

            resultSet = prepStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generatedID = resultSet.getInt(1);
                log.debug("creating entity with id: {}", generatedID);
                return getById(generatedID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException ignored) {}
            if (prepStatement != null)      try { prepStatement.close(); } catch (SQLException ignored) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        try {
            connection = dbManager.getConnection();
            String query = getDeleteQuery();
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, id);
            log.debug("deleting entity with id: {}", id);
            prepStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (prepStatement != null)      try { prepStatement.close(); } catch (SQLException ignored) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T entity) {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        try {
            connection = dbManager.getConnection();
            String query = getUpdateQuery();
            prepStatement = connection.prepareStatement(query);
            fillUpdateStatement(prepStatement, entity);
            prepStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (prepStatement != null) try { prepStatement.close(); } catch (SQLException ignored) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getById(Integer id) {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            String query = getGetByIdQuery();
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, id);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                return getEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException ignored) {}
            if (prepStatement != null)  try { prepStatement.close(); } catch (SQLException ignored) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll() {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();
        try {
            connection = dbManager.getConnection();
            String query = getGetAllQuery();
            prepStatement = connection.prepareStatement(query);
            resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                list.add(getEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException ignored) {}
            if (prepStatement != null) try { prepStatement.close(); } catch (SQLException ignored) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
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
