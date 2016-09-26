package com.iowniwant.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * The User class is a mutable data type to encapsulate
 * properties of a person involved in process of interaction with
 * the application.
 */
public class User implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private Double monthSalary;

    /**
     * All serializable object required to have
     * a default constructor without parameters.
     */
    public User() {
    }

    /**
     * Initialize a new user after his registration
     * using his firstName, lastName, username, password, email.
     * @param firstName user's firstName from the registration page.
     * @param lastName user's lastName from the registration page.
     * @param userName user's userName from the registration page.
     * @param password user's password from the registration page.
     * @param email user's email from the registration page.
     */
    public User(String firstName, String lastName, String userName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    /**
     * Initialize user using given data from the obtained resultSet.
     * @param resultSet a table of data, obtained from the DataBase
     * in order to create a new user instance.
     * @throws SQLException if there is some sqlException occurred.
     */
    public User(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getLong("user_id");
        this.firstName = resultSet.getString("first_name");
        this.lastName = resultSet.getString("last_name");
        this.userName = resultSet.getString("user_name");
        this.password = resultSet.getString("user_password");
        this.email = resultSet.getString("email");
        this.monthSalary = resultSet.getDouble("month_salary");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Double getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(Double monthSalary) {
        this.monthSalary = monthSalary;
    }

    /**
     * Compares this user to the specified user.
     * @param other the other user.
     * @return true if this user equals other; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User))
            return false;
        User that = (User) other;
        return (this.id == that.id) && (this.userName.equals(that.userName))
                                    && (this.password.equals(that.password))
                                    && (this.email.equals(that.email));
    }

    /**
     * Returns a hash code for this user.
     * @return a hash code for this user.
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + this.id.hashCode();
        hash = 31 * hash + this.userName.hashCode();
        hash = 31 * hash + this.password.hashCode();
        hash = 31 * hash + this.email.hashCode();
        return hash;
    }

    /**
     * Returns a string representation of this user.
     * @return a string representation of this user.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", monthSalary=" + monthSalary +
                '}';
    }
}
