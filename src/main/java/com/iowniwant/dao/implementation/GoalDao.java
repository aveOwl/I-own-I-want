package com.iowniwant.dao.implementation;

import com.iowniwant.model.Goal;
import com.iowniwant.model.User;
import com.iowniwant.util.exceptions.EntityConstructionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fills the <code>PreparedStatement</code> with given
 *
 * @see Goal entity fields.
 */
public class GoalDao extends AbstractDaoImpl<Goal> {

    private UserDao userDao = new UserDao();

    /**
     * Fills the <code>PreparedStatement</code> with the given Goal entity fields
     * in order to persist the Goal in the DataBase
     *
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity        goal to be persisted.
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
            throw new EntityConstructionException("Failed to fill create statement for Goal: " + entity, e);
        }
    }

    /**
     * Fills the PreparedStatement with given Goal entity fields
     * to update Goal in the DataBase.
     *
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity        goal to be updated.
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
            throw new EntityConstructionException("Failed to fill update statement for Goal: " + entity, e);
        }
    }

    /**
     * Creates Goal entity providing <code>resultSet</code> and a user to the
     *
     * @param resultSet a table of data representing a database result set.
     * @return Goal entity.
     * @see Goal class constructor.
     */
    @Override
    public Goal getEntity(ResultSet resultSet) {
        try {
            User user = this.getUserForGoal(resultSet);
            return new Goal(resultSet, user);
        } catch (SQLException e) {
            throw new EntityConstructionException("Failed to create goal entity", e);
        }
    }

    private User getUserForGoal(ResultSet resultSet) throws SQLException {
        Long user_id = resultSet.getLong("user_id");

        return this.userDao.getById(user_id);
    }

    /**
     * Returns a List of all Goals associated with user,
     * who's id is the userId parameter.
     *
     * @param userId User identifier.
     * @return List of Goals.
     */
    public List<Goal> getGoalsByUserId(Long userId) {
        List<Goal> goals = new ArrayList<>();
        User user = this.userDao.getById(userId);

        try (Connection connection = this.dbManager.getDbConnection();
             PreparedStatement ps = this.goalsByUserIdPreparedStatement(connection, userId);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                goals.add(new Goal(rs, user));
            }
        } catch (SQLException e) {
            throw new EntityConstructionException("Failed to fetch goals for user with id: " + userId, e);
        }
        return goals;
    }

    private PreparedStatement goalsByUserIdPreparedStatement(Connection connection, Long id) throws SQLException {
        String query = this.getGoalByUserId();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, id);

        return ps;
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
