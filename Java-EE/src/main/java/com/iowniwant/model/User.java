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
public class User implements Serializable, Comparable<User> {

    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private double monthSalary;

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
     * @throws IllegalArgumentException if monthSalary is NaN or infinitive.
     */
    public User(ResultSet resultSet) throws SQLException {
        if (Double.isNaN(monthSalary) || Double.isInfinite(monthSalary))
            throw new IllegalArgumentException("MonthSalary cannot be NaN or infinitive");
        this.id = resultSet.getInt("user_id");
        this.firstName = resultSet.getString("first_name");
        this.lastName = resultSet.getString("last_name");
        this.userName = resultSet.getString("nick_name");
        this.password = resultSet.getString("user_password");
        this.email = resultSet.getString("email");
        this.monthSalary = resultSet.getDouble("month_salary");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(double monthSalary) {
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
        hash = 31 * hash + ((Integer)id).hashCode();
        hash = 31 * hash + userName.hashCode();
        hash = 31 * hash + password.hashCode();
        hash = 31 * hash + email.hashCode();
        return hash;
    }

    /**
     * Compares this user to the specified user.
     *
     * @param  that the other transaction
     * @return { a negative integer, zero, a positive integer}, depending
     *         on whether the firstName, lastName, monthSalary of this user is { less than,
     *         equal to, or greater than } the firstName, lastName, monthSalary of that user.
     */
    @Override
    public int compareTo(User that) {
        int firstNameComp = this.firstName.compareTo(that.firstName);
        int lastNameComp = this.lastName.compareTo(that.lastName);
        if (firstNameComp != 0)
            return firstNameComp;
        if (lastNameComp != 0)
            return lastNameComp;

        return (this.monthSalary < that.monthSalary ? -1 :
                (this.monthSalary == that.monthSalary ? 0 : 1));
    }

    /**
     * Compares two users by monthSalary.
     */
    public static class howMuchGain implements Comparator<User> {
        @Override
        public int compare(User v, User w) {
            if      (v.monthSalary < w.monthSalary) return -1;
            else if (v.monthSalary > w.monthSalary) return +1;
            else                                    return 0;
        }
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
