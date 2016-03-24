package com.iowniwant.util;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;

public class UserValidation {

    /**
     * @param username: String input, entered by user.
     * @param password: String input, entered by user.
     * @return true if user's nick-name and password valid & present in the DataBase.
     */
    public static boolean isUserValid(String username, String password) {
        UserDao userDao = new UserDao();
        User user = userDao.getByNick(username);
        return username.equals(user.getNickName()) && password.equals(user.getPassword());
    }
}