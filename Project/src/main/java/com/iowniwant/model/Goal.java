package com.iowniwant.model;

/**
 * Created by sulfur on 21.03.16.
 */
public class Goal {

    private Long id;
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
}