package com.iowniwant.dao.implementation;

import com.iowniwant.model.Goal;
import com.iowniwant.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fills the <code>PreparedStatement</code> with given
 * @see Goal entity fields.
 */
public class GoalDao extends AbstractDaoImpl<Goal> {

    private UserDao userDao = new UserDao();

    /**
     * Fills the <code>PreparedStatement</code> with the given Goal entity fields
     * in order to persist the Goal in the DataBase
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
            prepStatement.setLong(6, entity.getUser().getId());
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
            prepStatement.setLong(6, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates Goal entity providing <code>resultSet</code> and a user to the
     * @see Goal class constructor.
     * @param resultSet a table of data representing a database result set.
     * @return Goal entity.
     */
    @Override
    public Goal getEntity(ResultSet resultSet) {
        try {
            Long user_id = resultSet.getLong("user_id");
            User user = this.userDao.getById(user_id);
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
    public List<Goal> getGoalsByUserId(Long userId) {
        List<Goal> goals = new ArrayList<>();
        User user = this.userDao.getById(userId);

        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dbManager.getDbConnection();
            String query = getGoalByUserId();
            prepStatement = connection.prepareStatement(query);
            prepStatement.setLong(1, userId);

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
        return this.dbManager.getQuery("get.goal.view.by.user.id");
    }

    /**
     * @return query to insert Goal into the DataBase.
     */
    @Override
    public String getCreateQuery() {
        return this.dbManager.getQuery("create.goal");
    }

    /**
     * @return query to delete Goal from the DataBase using goal_id.
     */
    @Override
    public String getDeleteQuery() {
        return this.dbManager.getQuery("delete.goal.by.id");
    }

    /**
     * @return query to update Goal in the DataBase using goal_id.
     */
    @Override
    public String getUpdateQuery() {
        return this.dbManager.getQuery("update.goal");
    }

    /**
     * @return query to retrieve all Goals from the DataBase.
     */
    @Override
    public String getGetAllQuery() {
        return this.dbManager.getQuery("get.all.goal");
    }

    /**
     * @return query to retrieve Goal_View from the DataBase using goal_id.
     */
    @Override
    public String getGetByIdQuery() {
        return this.dbManager.getQuery("get.goal.view.by.goal.id");
    }
}
