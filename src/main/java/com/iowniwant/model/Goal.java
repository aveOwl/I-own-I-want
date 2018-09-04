package com.iowniwant.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * The Goal class is a mutable data type to encapsulate
 * properties of a goal created by a
 *
 * @see User
 */
public class Goal implements Serializable {

    private Long id;
    private String title;
    private Double cost;
    private String description;
    private Date pubdate;
    private String notes;
    private User user;
    private Long v_id;

    /**
     * All serializable objects required to have
     * a default constructor without parameters.
     */
    public Goal() {
    }

    /**
     * Initialize a new goal after its creation by a user
     * using its title, cost, description, pubdate, notes.
     *
     * @param title       goal's title.
     * @param cost        goal's cost.
     * @param description goal's description.
     * @param pubdate     goal's pubdate.
     * @param notes       goal's notes.
     */
    public Goal(String title, Double cost, String description, Date pubdate, String notes, User user) {
        this.title = title;
        this.cost = cost;
        this.description = description;
        this.pubdate = pubdate;
        this.notes = notes;
        this.user = user;
    }

    /**
     * Initialize goal using data from the obtained resultSet.
     *
     * @param resultSet a table of data, obtained from the DataBase
     *                  in order to create a new goal instance.
     * @throws SQLException if some sqlException occurred.
     */
    public Goal(ResultSet resultSet, User user) throws SQLException {
        this.id = resultSet.getLong("goal_id");
        this.v_id = resultSet.getLong("v_goal_id");
        this.title = resultSet.getString("title");
        this.cost = resultSet.getDouble("cost");
        this.description = resultSet.getString("description");
        this.pubdate = resultSet.getDate("pubdate");
        this.notes = resultSet.getString("notes");
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getV_id() {
        return v_id;
    }

    public void setV_id(Long v_id) {
        this.v_id = v_id;
    }

    /**
     * Compares this goal to the specified goal.
     *
     * @param other the other goal.
     * @return true if this goal equals other; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Goal))
            return false;
        Goal that = (Goal) other;
        return (Objects.equals(this.id, that.id));
    }

    /**
     * Returns a hash code for this goal.
     *
     * @return a hash code for this goal.
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + this.id.hashCode();
        return hash;
    }

    /**
     * Returns a string representation of this goal.
     *
     * @return a string representation of this goal.
     */
    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", notes='" + notes + '\'' +
                ", user_id=" + user.getId() +
                ", goal_v_id=" + v_id +
                '}';
    }
}
