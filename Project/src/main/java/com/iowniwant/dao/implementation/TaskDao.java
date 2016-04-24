package com.iowniwant.dao.implementation;

import com.iowniwant.model.Goal;
import com.iowniwant.model.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDao extends AbstractDaoImpl<Task> {
    private TaskDao() {}
    private GoalDao goalDao = GoalDao.getInstance();

    /**
     * Fills the PreparedStatement with given Task entity fields
     * to persist Task in the DataBase.
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity task to be persisted.
     */
    @Override
    public void fillCreateStatement(PreparedStatement prepStatement, Task entity) {
        try {
            prepStatement.setString(1, entity.getDescription());
            prepStatement.setInt(2, entity.getGoal().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills the PreparedStatement with given Task entity fields
     * to update Task in the DataBase.
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity task to be updated.
     */
    @Override
    public void fillUpdateStatement(PreparedStatement prepStatement, Task entity) {
        try {
            prepStatement.setString(1, entity.getDescription());
            prepStatement.setInt(2, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task getEntity(ResultSet resultSet) {
        try {
            int goal_id = resultSet.getInt("goal_id");
            Goal goal = goalDao.getById(goal_id);
            return new Task(resultSet, goal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return query to insert Task into the DataBase.
     */
    @Override
    public String getCreateQuery() {
        return dbManager.getQuery("create.task");
    }

    /**
     * @return query to delete Task from the DataBase using task_id.
     */
    @Override
    public String getDeleteQuery() {
        return dbManager.getQuery("delete.task.by.id");
    }

    /**
     * @return query to update Task in the DataBase using task_id.
     */
    @Override
    public String getUpdateQuery() {
        return dbManager.getQuery("update.task");
    }

    /**
     * @return query to retrieve all Tasks from the DataBase.
     */
    @Override
    public String getGetAllQuery() {
        return dbManager.getQuery("get.all.task");
    }

    /**
     * @return query to retrieve Task_View from the DataBase using task_id.
     */
    @Override
    public String getGetByIdQuery() {
        return dbManager.getQuery("get.task.view.by.task.id");
    }
}
