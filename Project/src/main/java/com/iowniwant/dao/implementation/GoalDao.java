package com.iowniwant.dao.implementation;

import com.iowniwant.model.Goal;
import com.iowniwant.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fills the PreparedStatement with given
 * @see Goal entity fields.
 */
public class GoalDao extends AbstractDaoImpl<Goal> {
    private static GoalDao instance;
    private GoalDao() {}

    /**
     * Provides GoalDao instance.
     * @return Every time the same GoalDao object is invoked.
     */
    public static GoalDao getInstance (){
        if (instance == null) {
            instance = new GoalDao();
        }
        return instance;
    }

    private UserDao userDao = UserDao.getInstance();

    /**
     * Fills the PreparedStatement with given Goal entity fields
     * to persist Goal in the DataBase.
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity goal to be persisted.
     */
    @Override
    public void fillCreateStatement(PreparedStatement prepStatement, Goal entity) {
        try {
            prepStatement.setString(1, entity.getTitle());
            prepStatement.setDouble(2, entity.getCost());
            prepStatement.setString(3, entity.getDescription());
            prepStatement.setDate(4, entity.getPubdate());
            prepStatement.setString(5, entity.getNotes());
            prepStatement.setInt(6, entity.getUser().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills the PreparedStatement with given Goal entity fields
     * to update Goal in the DataBase.
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity goal to be updated.
     */
    @Override
    public void fillUpdateStatement(PreparedStatement prepStatement, Goal entity) {
        try {
            prepStatement.setString(1, entity.getTitle());
            prepStatement.setDouble(2, entity.getCost());
            prepStatement.setString(3, entity.getDescription());
            prepStatement.setDate(4, entity.getPubdate());
            prepStatement.setString(5, entity.getNotes());
            prepStatement.setInt(6, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates Goal entity by providing resultSet and user to
     * @see Goal class constructor.
     * @param resultSet a table of data representing a database result set.
     * @return Goal entity.
     */
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

    /**
     * Returns a List of all Goals associated with user,
     * who's id is the userId parameter.
     * @param userId User identifier.
     * @return List of Goals.
     */
    public List<Goal> getGoalsByUserId(Integer userId) {
        List<Goal> goals = new ArrayList<>();
        User user = userDao.getById(userId);

        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            String query = getGoalByUserId();
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userId);

            resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                goals.add(new Goal(resultSet, user));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException ignored) {}
            if (prepStatement != null)      try { prepStatement.close(); } catch (SQLException ignored) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
        }

        return goals;
    }


    /**
     * @return query to retrieve all Goals_Views from the DataBase using user_id.
     */
    private String getGoalByUserId() {
        return dbManager.getQuery("get.goal.view.by.user.id");
    }

    /**
     * @return query to insert Goal into the DataBase.
     */
    @Override
    public String getCreateQuery() {
        return dbManager.getQuery("create.goal");
    }

    /**
     * @return query to delete Goal from the DataBase using goal_id.
     */
    @Override
    public String getDeleteQuery() {
        return dbManager.getQuery("delete.goal.by.id");
    }

    /**
     * @return query to update Goal in the DataBase using goal_id.
     */
    @Override
    public String getUpdateQuery() {
        return dbManager.getQuery("update.goal");
    }

    /**
     * @return query to retrieve all Goals from the DataBase.
     */
    @Override
    public String getGetAllQuery() {
        return dbManager.getQuery("get.all.goal");
    }

    /**
     * @return query to retrieve Goal_View from the DataBase using goal_id.
     */
    @Override
    public String getGetByIdQuery() {
        return dbManager.getQuery("get.goal.view.by.goal.id");
    }
}