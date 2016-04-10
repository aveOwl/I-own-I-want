package com.iowniwant.util;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;

/**
 * Validates the user persistence in the DataBase.
 */
public class UserValidation {

    /**
     * Inspects whether giving username and password
     * are valid and persistent in the DataBase.
     * @param username: String input, entered by user.
     * @param password: String input, entered by user.
     * @return true if user's username and password valid and present in the data-base
     * otherwise return false.
     */
    public static boolean isUserValid(String username, String password) {
        User user = UserDao.getInstance().getByNick(username);
        return username.equals(user.getUserName()) && password.equals(user.getPassword());
    }
}
