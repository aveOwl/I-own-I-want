package com.iowniwant.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.sql.Date;

/**
 * The Goal class is a mutable data type to encapsulate
 * properties of a goal created by a
 * @see User
 */
public class Goal implements Serializable, Comparable<Goal> {

    private int id;
    private int v_id;
    private String title;
    private double cost;
    private String description;
    private Date pubdate;
    private String notes;
    private User user;

    /**
     * All serializable objects required to have
     * a default constructor without parameters.
     */
    public Goal() {
    }

    /**
     * Initialize a new goal after its creation by a user
     * using its title, cost, description, pubdate, notes.
     * @param title goal's title.
     * @param cost goal's cost.
     * @param description goal's description.
     * @param pubdate goal's pubdate.
     * @param notes goal's notes.
     */
    public Goal(String title, double cost, String description, Date pubdate, String notes, User user) {
        this.title = title;
        this.cost = cost;
        this.description = description;
        this.pubdate = pubdate;
        this.notes = notes;
        this.user = user;
    }

    /**
     * Initialize goal using data from the obtained resultSet.
     * @param resultSet a table of data, obtained from the DataBase
     * in order to create a new goal instance.
     * @throws SQLException if some sqlException occurred.
     * @throws IllegalArgumentException if cost is NaN or infinite.
     */
    public Goal (ResultSet resultSet, User user) throws SQLException {
        if (Double.isNaN(cost) || Double.isInfinite(cost))
            throw new IllegalArgumentException("Cost cannot be NaN or infinite");
        this.id = resultSet.getInt("goal_id");
        this.v_id = resultSet.getInt("v_goal_id");
        this.title = resultSet.getString("title");
        this.cost = resultSet.getDouble("cost");
        this.description = resultSet.getString("description");
        this.pubdate = resultSet.getDate("pubdate");
        this.notes = resultSet.getString("notes");
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    /**
     * Compares this goal to the specified goal.
     * @param other the other goal.
     * @return true if this goal equals other; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Goal))
            return false;
        Goal that = (Goal) other;
        return (this.id == that.id);
    }

    /**
     * Returns a hash code for this goal.
     * @return a hash code for this goal.
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + ((Integer)id).hashCode();
        return hash;
    }

    /**
     * Compares this goal to the specified goal.
     *
     * @param  that the other transaction
     * @return { a negative integer, zero, a positive integer}, depending
     *         on whether the title, cost of this goal is { less than,
     *         equal to, or greater than } the title, cost of that goal.
     */
    @Override
    public int compareTo(Goal that) {
        int titleComp = this.title.compareTo(that.title);
        if (titleComp != 0)
            return titleComp;

        return (this.cost < that.cost ? -1 :
                (this.cost == that.cost ? 0 : 1));
    }

    /**
     * Compares two goals by cost.
     */
    public static class howMuchCost implements Comparator<Goal> {
        @Override
        public int compare(Goal v, Goal w) {
            if      (v.cost < w.cost) return -1;
            else if (v.cost > w.cost) return +1;
            else                      return 0;
        }
    }

    /**
     * Compares two transactions by pubDate.
     */
    public static class whenPublished implements Comparator<Goal> {
        @Override
        public int compare(Goal v, Goal w) {
            return v.pubdate.compareTo(w.pubdate);
        }
    }

    /**
     * Returns a string representation of this goal.
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
                '}';
    }
}