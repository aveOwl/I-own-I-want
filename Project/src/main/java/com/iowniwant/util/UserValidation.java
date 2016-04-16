package com.iowniwant.util;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;

/**
 * Validates the user persistence in the DataBase.
 */
public class UserValidation {
    private static UserDao userDao = UserDao.getInstance();

    /**
     * /**
     * Inspects whether giving username and password
     * are valid and persistent in the DataBase.
     * @param username user's nickname to be validated.
     * @param password user's password to be validated.
     * @return true if user's username and password valid and present in the data-base
     * otherwise return false.
     */
    public static boolean isUserValid(String username, String password) {
        User user = userDao.getByNick(username);
        return user != null && password.equals(user.getPassword());
    }
}
