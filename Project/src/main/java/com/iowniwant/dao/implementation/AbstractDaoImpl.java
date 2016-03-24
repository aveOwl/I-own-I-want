package com.iowniwant.dao.implementation;

import com.iowniwant.dao.AbstractDAO;
import com.iowniwant.model.BaseEntity;
import com.iowniwant.util.DataBaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDaoImpl<T extends BaseEntity> implements AbstractDAO<T> {
    public static final Logger log = LoggerFactory.getLogger(AbstractDaoImpl.class);

    DataBaseManager dbManager = new DataBaseManager();

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
            log.debug("Is update executed ? : {}", i == 1);

            resultSet = prepStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generatedID = resultSet.getInt(1);
                log.debug("Creating entity with id: {}", generatedID);
                return getById(generatedID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException logOrIgnore) {}
            if (prepStatement != null)      try { prepStatement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }

        return null;
    }

    @Override
    public void delete(T entity) {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        try {
            connection = dbManager.getConnection();
            String query = getDeleteQuery();
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, entity.getId());
            log.debug("Deleting entity with id: {}", entity.getId());
            prepStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (prepStatement != null)      try { prepStatement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }
    }

    @Override
    public T update(T entity) {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        try {
            connection = dbManager.getConnection();
            String query = getUpdateQuery();
            prepStatement = connection.prepareStatement(query);
            fillUpdateStatement(prepStatement, entity);
            log.debug("Updating entity with id: {}", entity.getId());
            prepStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (prepStatement != null) try { prepStatement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }

        return null;
    }

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
                log.debug("Returning entity with id: {}", id);
                return getEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException logOrIgnore) {}
            if (prepStatement != null)  try { prepStatement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }

        return null;
    }

    @Override
    public T getByNick(String nickname) {
        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            String query = getGetByNickQuery();
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, nickname);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                log.debug("Returning entity with nickname: {}", nickname);
                return getEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException logOrIgnore) {}
            if (prepStatement != null)  try { prepStatement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }

        return null;
    }

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
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException logOrIgnore) {}
            if (prepStatement != null) try { prepStatement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
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
    public abstract String getGetByNickQuery();
    public abstract String getGetAllQuery();
}