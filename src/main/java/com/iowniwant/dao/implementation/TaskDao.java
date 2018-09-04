package com.iowniwant.dao.implementation;

import com.iowniwant.model.Goal;
import com.iowniwant.model.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDao extends AbstractDaoImpl<Task> {
    private GoalDao goalDao = new GoalDao();

    /**
     * Fills the PreparedStatement with given Task entity fields
     * to persist Task in the DataBase.
     *
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity        task to be persisted.
     */
    @Override
    public void fillCreateStatement(PreparedStatement prepStatement, Task entity) {
        try {
            prepStatement.setString(1, entity.getDescription());
            prepStatement.setLong(2, entity.getGoal().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills the PreparedStatement with given Task entity fields
     * to update Task in the DataBase.
     *
     * @param prepStatement object that represents a precompiled SQL statement.
     * @param entity        task to be updated.
     */
    @Override
    public void fillUpdateStatement(PreparedStatement prepStatement, Task entity) {
        try {
            prepStatement.setString(1, entity.getDescription());
            prepStatement.setLong(2, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task getEntity(ResultSet resultSet) {
        try {
            Long goal_id = resultSet.getLong("goal_id");
            Goal goal = this.goalDao.getById(goal_id);
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
        return this.dbManager.getQuery("create.task");
    }

    /**
     * @return query to delete Task from the DataBase using task_id.
     */
    @Override
    public String getDeleteQuery() {
        return this.dbManager.getQuery("delete.task.by.id");
    }

    /**
     * @return query to update Task in the DataBase using task_id.
     */
    @Override
    public String getUpdateQuery() {
        return this.dbManager.getQuery("update.task");
    }

    /**
     * @return query to retrieve all Tasks from the DataBase.
     */
    @Override
    public String getGetAllQuery() {
        return this.dbManager.getQuery("get.all.task");
    }

    /**
     * @return query to retrieve Task_View from the DataBase using task_id.
     */
    @Override
    public String getGetByIdQuery() {
        return this.dbManager.getQuery("get.task.view.by.task.id");
    }
}
