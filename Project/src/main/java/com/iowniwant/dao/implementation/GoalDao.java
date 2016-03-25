package com.iowniwant.dao.implementation;


import com.iowniwant.model.Goal;
import com.iowniwant.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
    Таковыми будут реализации методов класса GoalsDAO в зависимости от обьектов переданных в
качестве параметров его методам
*/
public class GoalDao extends AbstractDaoImpl<Goal> {

    UserDao userDao = new UserDao();

    @Override
    public void fillCreateStatement(PreparedStatement prepStatement, Goal entity) {
        try {
            prepStatement.setString(1, entity.getTitle());
            prepStatement.setDouble(2, entity.getCost());
            prepStatement.setString(3, entity.getDescription());
            prepStatement.setString(4, entity.getPubdate());
            prepStatement.setString(5, entity.getNotes());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void fillUpdateStatement(PreparedStatement prepStatement, Goal entity) {
        try {
            prepStatement.setString(1, entity.getTitle());
            prepStatement.setDouble(2, entity.getCost());
            prepStatement.setString(3, entity.getDescription());
            prepStatement.setString(4, entity.getPubdate());
            prepStatement.setString(5, entity.getNotes());
            prepStatement.setInt(6, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Goal getEntity(ResultSet resultSet) {
        try {
            int user_id = resultSet.getInt("user_id");
            User user = userDao.getById(user_id);
            return new Goal(resultSet, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Goal> getGoalsByUserId(Integer userId) {

        ArrayList<Goal> goals = new ArrayList<>();
        User user = userDao.getById(userId);

        try (Connection connection = dbManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(dbManager.getQuery("get.goal.by.user.id"))) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    goals.add(new Goal(rs, user));
                }
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return goals;
    }

    @Override
    public String getGetByNickQuery() {
        return null;
    }

    @Override
    public String getCreateQuery() {
        return dbManager.getQuery("create.goal");
    }

    @Override
    public String getDeleteQuery() {
        return dbManager.getQuery("delete.goal.by.id");
    }

    @Override
    public String getUpdateQuery() {
        return dbManager.getQuery("update.goal");
    }

    @Override
    public String getGetByIdQuery() {
        return dbManager.getQuery("get.goal.by.id");
    }

    @Override
    public String getGetAllQuery() {
        return dbManager.getQuery("get.all.goal");
    }
}