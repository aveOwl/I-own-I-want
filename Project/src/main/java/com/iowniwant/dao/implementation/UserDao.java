package com.iowniwant.dao.implementation;

import com.iowniwant.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends AbstractDaoImpl<User> {

    @Override
    public void fillCreateStatement(PreparedStatement prepStatement, User entity) {
        try {
            prepStatement.setString(1, entity.getFirstName());
            prepStatement.setString(2, entity.getLastName());
            prepStatement.setString(3, entity.getNickName());
            prepStatement.setString(4, entity.getPassword());
            prepStatement.setString(5, entity.getEmail());
            prepStatement.setFloat(6, entity.getMonthSalary());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fillUpdateStatement(PreparedStatement prepStatement, User entity) {
        try {
            prepStatement.setString(1, entity.getFirstName());
            prepStatement.setString(2, entity.getLastName());
            prepStatement.setString(3, entity.getNickName());
            prepStatement.setString(4, entity.getPassword());
            prepStatement.setString(5, entity.getEmail());
            prepStatement.setFloat(6, entity.getMonthSalary());
            prepStatement.setInt(7, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getEntity(ResultSet resultSet) {
        try {
            return new User(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getCreateQuery() {
        return dbManager.getQuery("create.user");
    }

    @Override
    public String getDeleteQuery() {
        return dbManager.getQuery("delete.user.by.id");
    }

    @Override
    public String getUpdateQuery() {
        return dbManager.getQuery("update.user");
    }

    @Override
    public String getGetByIdQuery() {
        return dbManager.getQuery("get.goal.by.id");
    }

    @Override
    public String getGetByNickQuery() {
        return dbManager.getQuery("get.user.by.nick");
    }

    @Override
    public String getGetAllQuery() {
        return dbManager.getQuery("get.all.user");
    }
}