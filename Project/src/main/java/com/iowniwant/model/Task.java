package com.iowniwant.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Task class is a mutable data type to encapsulate
 * properties of a task associated with specific goal
 * @see Goal
 */
public class Task implements Serializable {
    private int id;
    private int v_id;
    private String description;
    private Goal goal;

    /**
     * All serializable objects required to have
     * a default constructor without parameters.
     */
    public Task() {
    }

    /**
     * Initialize a new task after its creation by a user
     * using its description and associated goal.
     * @param description task's description.
     * @param goal goal associated with this task.
     */
    public Task(String description, Goal goal) {
        this.description = description;
        this.goal = goal;
    }

    /**
     * Initialize task using data from the obtained resultSet.
     * @param resultSet a table of data, obtained from the DataBase
     * in order to create a new goal instance.
     * @throws SQLException if some sqlException occurred.
     */
    public Task (ResultSet resultSet, Goal goal) throws SQLException {
        this.id = resultSet.getInt("task_id");
        this.v_id = resultSet.getInt("v_task_id");
        this.description = resultSet.getString("description");
        this.goal = goal;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    /**
     * Compares this task to the specified task.
     * @param other the other task.
     * @return true if this task equals other; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Task))
            return false;
        Task that = (Task) other;
        return (this.id == that.id);
    }

    /**
     * Returns a hash code for this task.
     * @return a hash code for this task.
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + ((Integer)id).hashCode();
        return hash;
    }

    /**
     * Returns a string representation of this goal.
     * @return a string representation of this goal.
     */
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", goal_id=" + goal.getId() +
                '}';
    }
}
