package com.iowniwant.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String nickName;
    private String password;
    private String email;
    private float monthSalary;
    private List<Goal> goalList;

    public User() {
    }

    public User(String firstName, String lastName, String nickName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }

    public User(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("user_id");
        this.firstName = resultSet.getString("first_name");
        this.lastName = resultSet.getString("last_name");
        this.nickName = resultSet.getString("nick_name");
        this.password = resultSet.getString("user_password");
        this.email = resultSet.getString("email");
        this.monthSalary = resultSet.getFloat("month_salary");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(float monthSalary) {
        this.monthSalary = monthSalary;
    }

    public List<Goal> getGoalList() {
        return goalList;
    }

    public void setGoalList(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", monthSalary=" + monthSalary +
                '}';
    }
}
