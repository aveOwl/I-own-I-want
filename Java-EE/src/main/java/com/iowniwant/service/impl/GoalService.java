package com.iowniwant.service.impl;

import com.iowniwant.dao.implementation.GoalDao;
import com.iowniwant.model.Goal;
import com.iowniwant.model.User;
import com.iowniwant.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

public class GoalService implements Service<Goal> {

    private static final Logger LOG = LoggerFactory.getLogger(GoalService.class);

    private GoalDao goalDao = new GoalDao();
    private UserService userService = new UserService();

    @Override
    public Goal save(final Goal goal) {
        Objects.requireNonNull(goal, "Goal cannot be null");

        if (goal.getId() != null) {
            LOG.error("Attempted to create a Goal object, but id attribute was not null.");
            throw new EntityExistsException(
                    "Cannot create new Goal with supplied id. The id attribute must be null.");
        }

        Goal savedGoal = this.goalDao.create(goal);
        LOG.debug("Persisted goal entity: {}", savedGoal);

        return savedGoal;
    }

    @Override
    public Goal getById(final Long id) {
        Objects.requireNonNull(id, "id cannot be null");

        Goal goal = this.goalDao.getById(id);

        if (goal == null) {
            LOG.error("Attempted to retrieve goal but no goals were found by id: {}", id);
            throw new EntityNotFoundException("No goals were found by id: " + id);
        }

        LOG.debug("Fetching goal: {}", goal);
        return goal;
    }

    public void delete(final Long id) {
        Objects.requireNonNull(id, "id cannot be null");

        this.goalDao.delete(id);

        LOG.debug("Goal with id: {} deleted", id);
    }

    public List<Goal> getGoalsByUserId(final Long id) {
        Objects.requireNonNull(id, "id cannot be null");

        User user = this.userService.getById(id);

        if (user == null) {
            LOG.error("Attempted to fetch goals for User object, but no users were found by id: {}", id);
            throw new EntityNotFoundException("Cannot fetch goals. No users found by provided id: " + id);
        }

        List<Goal> goals = this.goalDao.getGoalsByUserId(id);

        LOG.debug("Fetching goals: {} \n for User: {}", goals, user);
        return goals;
    }
}
