package com.iowniwant.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sulfur on 21.03.16.
 */
public class Goal extends BaseEntity{

    private String title;
    private double cost;
    private String description;
    private String pubdate;
    private String notes;

    public Goal(String title, double cost, String description, String pubdate, String notes) {
        this.title = title;
        this.cost = cost;
        this.description = description;
        this.pubdate = pubdate;
        this.notes = notes;
    }


    public Goal (ResultSet resultSet) {
        try {
            this.id = resultSet.getInt("goals_id");
            this.title = resultSet.getString("title");
            this.cost = resultSet.getDouble("cost");
            this.description = resultSet.getString("description");
            this.pubdate = resultSet.getString("pubdate");
            this.notes = resultSet.getString("notes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "title='" + title + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", notes='" + notes + '\'' +
                "} ";
    }
}