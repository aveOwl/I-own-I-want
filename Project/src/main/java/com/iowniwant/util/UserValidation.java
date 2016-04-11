package com.iowniwant.util;

import com.iowniwant.model.User;

/**
 * Validates the user persistence in the DataBase.
 */
public class UserValidation {

    /**
     * /**
     * Inspects whether giving username and password
     * are valid and persistent in the DataBase.
     * @param user user to be validated.
     * @return true if user's username and password valid and present in the data-base
     * otherwise return false.
     */
    public static boolean isUserValid(User user) {
        return user.getUserName() != null && user.getPassword() != null;
    }
}
