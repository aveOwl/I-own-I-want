package com.iowniwant.service;

import com.iowniwant.dao.implementation.GoalDao;
import com.iowniwant.model.Goal;
import com.iowniwant.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.iowniwant.controller.helper.TestEntity.getTestGoal;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GoalServiceTest {
    @Mock
    private GoalDao goalDao;

    @Mock
    private UserService userService;

    @Mock
    private User user;

    @InjectMocks
    private GoalService goalService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Goal goal;
    private List<Goal> goalList;

    @Before
    public void setUp() throws Exception {
        goal = getTestGoal();
        goalList = new ArrayList<>(Collections.singletonList(goal));
    }

    @After
    public void tearDown() throws Exception {
        goal = null;
        goalList = null;
    }

    @Test
    public void shouldSaveGoal() throws Exception {
        // given
        goal.setId(null);

        when(goalDao.create(goal))
                .thenReturn(goal);

        // when
        goalService.save(goal);

        // then
        verify(goalDao, atLeastOnce()).create(goal);
    }

    @Test
    public void shouldNotSaveExistingGoal() throws Exception {
        // given
        when(goalDao.create(goal))
                .thenReturn(goal);

        thrown.expect(EntityExistsException.class);
        thrown.expectMessage("Cannot create new Goal with supplied id. The id attribute must be null.");

        // when
        goalService.save(goal);

        // then
        verify(goalDao, never()).create(goal);
    }

    @Test
    public void shouldFetchGoalById() throws Exception {
        // given
        when(goalDao.getById(goal.getId()))
                .thenReturn(goal);

        // when
        Goal goalById = goalService.getById(goal.getId());

        // then
        verify(goalDao, atLeastOnce()).getById(goal.getId());
        assertEquals("fetched goal", goalById, goal);
    }

    @Test
    public void shouldNotFetchNullGoalById() throws Exception {
        // given
        when(goalDao.getById(goal.getId()))
                .thenReturn(null);

        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("No goals were found by id: " + goal.getId());

        // when
        Goal goalById = goalService.getById(goal.getId());

        // then
        verify(goalDao, atLeastOnce()).getById(goal.getId());
        assertEquals("goal is null", goalById, null);
    }

    @Test
    public void shouldFetchGoalsByUserId() throws Exception {
        // given
        when(userService.getById(99L))
                .thenReturn(user);
        when(goalDao.getGoalsByUserId(99L))
                .thenReturn(goalList);

        // when
        List<Goal> goalListByUserId = goalService.getGoalsByUserId(99L);

        // then
        verify(goalDao, atLeastOnce()).getGoalsByUserId(99L);
        assertEquals("fetched goals", goalListByUserId, goalList);
    }

    @Test
    public void shouldNotFetchGoalsByUserId() throws Exception {
        // given
        Long userId = 99L;
        when(userService.getById(userId))
                .thenReturn(null);

        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Cannot fetch goals. No users found by provided id: " + userId);

        // when
        List<Goal> goalListByUserId = goalService.getGoalsByUserId(userId);

        // then
        verify(goalDao, never()).getGoalsByUserId(anyLong());
        assertEquals("fetched goals", goalListByUserId, null);
    }
}