package com.iowniwant.controller.helper;

import com.iowniwant.model.Goal;
import com.iowniwant.model.User;

import java.sql.Date;

public class TestEntity {
    private static User user;
    private static Goal goal;

    public static User getTestUser() {
        user = new User();
        user.setId(99);
        user.setUserName("test");
        user.setPassword("test");
        user.setFirstName("test");
        user.setLastName("test");
        user.setEmail("test");
        user.setMonthSalary(99.00);
        return user;
    }

    public static Goal getTestGoal() {
        goal = new Goal();
        goal.setId(99);
        goal.setDescription("test");
        goal.setNotes("test");
        goal.setTitle("test");
        goal.setCost(99.00);
        goal.setUser(getTestUser());
        goal.setPubdate(new Date(9L));
        goal.setV_id(99);
        return goal;
    }
}
