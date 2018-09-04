package com.iowniwant.service;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;

public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao = new UserDao();

    public User save(final User user) {
        Objects.requireNonNull(user, "User cannot be null");

        if (user.getId() != null) {
            LOG.error("Attempted to create a User object, but id attribute was not null.");
            throw new EntityExistsException(
                    "Cannot create new User with supplied id. The id attribute must be null.");
        }

        User savedUser = this.userDao.create(user);
        LOG.debug("Persisted user entity: {}", savedUser);

        return savedUser;
    }

    public User getById(final Long id) {
        Objects.requireNonNull(id, "id cannot be null");

        User user = this.userDao.getById(id);

        if (user == null) {
            LOG.error("Attempted to retrieve user but no users were found by id: {}", id);
            throw new EntityNotFoundException("No users were found by id: " + id);
        }

        LOG.debug("Fetching user: {}", user);
        return user;
    }

    public User update(final User user) {
        Objects.requireNonNull(user, "User cannot be null");

        if (user.getId() == null) {
            LOG.error("Attempted to update a User object, but id attribute was null.");
            throw new EntityNotFoundException("Cannot preform update. The id attribute cannot be null.");
        }

        User updatedUser = this.userDao.update(user);

        LOG.info("Updated user entity: {}", updatedUser);

        return updatedUser;
    }

    public User getByUserName(final String userName) {
        Objects.requireNonNull(userName, "UserName cannot be null");

        User user = this.userDao.getByUserName(userName);

        if (user == null) {
            LOG.error("Attempted to retrieve user but no users were found by UserName: {}", userName);
            throw new EntityNotFoundException("No users were found by UserName: " + userName);
        }

        LOG.debug("Fetching user: {}", user);
        return user;
    }
}
