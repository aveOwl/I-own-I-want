package com.iowniwant.dao.implementation;

import com.iowniwant.model.User;
import com.iowniwant.util.exceptions.EntityConstructionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Fills the PreparedStatement with given
 *
 * @see User entity fields.
 */
public class UserDao extends AbstractDaoImpl<User> {

    /**
     * Fills the PreparedStatement with given User entity fields
     * to persist User in the DataBase.
     *
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity        user to be persisted.
     */
    @Override
    public void fillCreateStatement(PreparedStatement prepStatement, User entity) {
        try {
            prepStatement.setString(1, entity.getFirstName());
            prepStatement.setString(2, entity.getLastName());
            prepStatement.setString(3, entity.getUserName());
            prepStatement.setString(4, entity.getPassword());
            prepStatement.setString(5, entity.getEmail());
            prepStatement.setDouble(6,
                    entity.getMonthSalary() == null ? 0.0 : entity.getMonthSalary()); // need to fix this
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills the PreparedStatement with given User entity fields
     * to update User in the DataBase.
     *
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity        user to be updated.
     */
    @Override
    public void fillUpdateStatement(PreparedStatement prepStatement, User entity) {
        try {
            prepStatement.setString(1, entity.getFirstName());
            prepStatement.setString(2, entity.getLastName());
            prepStatement.setString(3, entity.getUserName());
            prepStatement.setString(4, entity.getPassword());
            prepStatement.setString(5, entity.getEmail());
            prepStatement.setDouble(6, entity.getMonthSalary());
            prepStatement.setLong(7, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates User entity by providing resultSet to
     *
     * @param resultSet a table of data representing a database result set.
     * @return User entity.
     * @see User class constructor.
     */
    @Override
    public User getEntity(ResultSet resultSet) {
        try {
            return new User(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns persistent object with given identifier.
     *
     * @param userName object identifier.
     * @return persistent User object with the given identifier or null if
     * there is no such persistent object.
     */
    public User getByUserName(String userName) {
        try (Connection connection = this.dbManager.getDbConnection();
             PreparedStatement ps = this.byUserNamePreparedStatement(connection, userName);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return getEntity(rs);
            }
        } catch (SQLException e) {
            throw new EntityConstructionException("Failed to fetch user entity by username: " + userName, e);
        }
        return null;
    }

    private PreparedStatement byUserNamePreparedStatement(Connection connection, String userName) throws SQLException {
        String query = this.getGetByUserNameQuery();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, userName);

        return ps;
    }

    /**
     * @return query to insert User into the DataBase.
     */
    @Override
    public String getCreateQuery() {
        return this.dbManager.getQuery("create.user");
    }

    /**
     * @return query to delete User from the DataBase.
     */
    @Override
    public String getDeleteQuery() {
        return this.dbManager.getQuery("delete.user.by.id");
    }

    /**
     * @return query to update User in the DataBase.
     */
    @Override
    public String getUpdateQuery() {
        return this.dbManager.getQuery("update.user");
    }

    /**
     * @return query to retrieve User from the DataBase using User's ID.
     */
    @Override
    public String getGetByIdQuery() {
        return this.dbManager.getQuery("get.user.by.id");
    }

    /**
     * @return query to retrieve all Users from the DataBase.
     */
    @Override
    public String getGetAllQuery() {
        return this.dbManager.getQuery("get.all.user");
    }

    /**
     * @return query to retrieve User from the DataBase using User's NickName.
     */
    private String getGetByUserNameQuery() {
        return this.dbManager.getQuery("get.user.by.user_name");
    }
}
