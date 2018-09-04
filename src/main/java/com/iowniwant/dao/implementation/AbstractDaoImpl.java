package com.iowniwant.dao.implementation;

import com.iowniwant.dao.AbstractDAO;
import com.iowniwant.util.DataBaseManager;
import com.iowniwant.util.exceptions.EntityConstructionException;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(T entity) {
        try (Connection connection = this.dbManager.getDbConnection();
             PreparedStatement ps = this.createPreparedStatement(connection, entity);
             ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                Long generatedID = rs.getLong(1);
                LOG.debug("Creating entity with id: {}", generatedID);
                return this.getById(generatedID);
            }
        } catch (SQLException e) {
            throw new EntityConstructionException("Failed to create entity: " + entity, e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        try (Connection connection = this.dbManager.getDbConnection();
             PreparedStatement ps = this.deletePreparedStatement(connection, id)) {
            ps.execute();
        } catch (SQLException e) {
            throw new EntityConstructionException("Failed to delete entity with id: " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T entity) {
        try (Connection connection = this.dbManager.getDbConnection();
             PreparedStatement ps = this.updatePreparedStatement(connection, entity)) {
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new EntityConstructionException("Failed to update entity: " + entity, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getById(Long id) {
        try (Connection connection = this.dbManager.getDbConnection();
             PreparedStatement ps = this.getByIdPreparedStatement(connection, id);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return getEntity(rs);
            }
        } catch (SQLException e) {
            throw new EntityConstructionException("Failed to fetch entity by id: " + id, e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        try (Connection connection = this.dbManager.getDbConnection();
             PreparedStatement ps = this.getAllPreparedStatement(connection);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(getEntity(rs));
            }
            LOG.debug("Fetched: {} entities", list.size());
        } catch (SQLException e) {
            throw new EntityConstructionException("Failed to fetch entities", e);
        }
        return list;
    }

    private PreparedStatement createPreparedStatement(Connection connection, T entity) throws SQLException {
        String query = this.getCreateQuery();
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        this.fillCreateStatement(ps, entity);
        int i = ps.executeUpdate();
        LOG.debug("Creation status : {}", i == 1 ? "Success" : "Fail");

        return ps;
    }

    private PreparedStatement deletePreparedStatement(Connection connection, Long id) throws SQLException {
        String query = this.getDeleteQuery();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, id);
        LOG.debug("Deleting entity with id: {}", id);

        return ps;
    }

    private PreparedStatement updatePreparedStatement(Connection connection, T entity) throws SQLException {
        String query = this.getUpdateQuery();
        PreparedStatement ps = connection.prepareStatement(query);
        this.fillUpdateStatement(ps, entity);
        LOG.debug("Updating entity: {}", entity);

        return ps;
    }

    private PreparedStatement getByIdPreparedStatement(Connection connection, Long id) throws SQLException {
        String query = this.getGetByIdQuery();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, id);
        LOG.debug("Fetching entity with id: {}", id);

        return ps;
    }

    private PreparedStatement getAllPreparedStatement(Connection connection) throws SQLException {
        String query = this.getGetAllQuery();
        PreparedStatement ps = connection.prepareStatement(query);

        return ps;
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
