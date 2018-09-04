package com.iowniwant.service;

import com.iowniwant.dao.implementation.UserDao;
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

import static com.iowniwant.controller.helper.TestEntity.getTestUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;
    private User user;

    @Before
    public void setUp() {
        user = getTestUser();
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void shouldSaveUser() {
        // given
        user.setId(null);

        when(userDao.create(user))
                .thenReturn(user);

        // when
        userService.save(user);

        // then
        verify(userDao, atLeastOnce()).create(user);
    }

    @Test
    public void shouldNotSaveExistingUser() {
        // given
        when(userDao.create(user))
                .thenReturn(user);

        thrown.expect(EntityExistsException.class);
        thrown.expectMessage("Cannot create new User with supplied id. The id attribute must be null.");

        // when
        userService.save(user);

        // then
        verify(userDao, never()).create(user);
    }

    @Test
    public void shouldFetchUserById() {
        // given
        when(userDao.getById(user.getId()))
                .thenReturn(user);

        // when
        User userById = userService.getById(user.getId());

        // then
        verify(userDao, atLeastOnce()).getById(user.getId());
        assertEquals("fetched user", userById, user);
    }

    @Test
    public void shouldNotFetchNullUserById() {
        // given
        when(userDao.getById(user.getId()))
                .thenReturn(null);

        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("No users were found by id: " + user.getId());

        // when
        User userById = userService.getById(user.getId());

        // then
        verify(userDao, atLeastOnce()).getById(user.getId());
        assertNull("user is null", userById);
    }

    @Test
    public void shouldUpdateUser() {
        // given
        when(userDao.update(user))
                .thenReturn(user);

        // when
        userService.update(user);

        // then
        verify(userDao, atLeastOnce()).update(user);
    }

    @Test
    public void shouldNotUpdateNonExistingUSer() {
        // given
        user.setId(null);

        when(userDao.update(user))
                .thenReturn(user);

        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Cannot preform update. The id attribute cannot be null.");

        // when
        userService.update(user);

        // then
        verify(userDao, never()).update(user);
    }

    @Test
    public void shouldFetchUserByUserName() {
        // given
        when(userDao.getByUserName(user.getUserName()))
                .thenReturn(user);

        // when
        User userByNickName = userService.getByUserName(user.getUserName());

        // then
        verify(userDao, atLeastOnce()).getByUserName(user.getUserName());
        assertEquals("fetched user", userByNickName, user);
    }

    @Test
    public void shouldNotFetchNullUserByUserName() {
        // given
        when(userDao.getByUserName(user.getUserName()))
                .thenReturn(null);

        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("No users were found by UserName: " + user.getUserName());

        // when
        User userByNickName = userService.getByUserName(user.getUserName());

        // then
        verify(userDao, atLeastOnce()).getById(user.getId());
        assertNull("user is null", userByNickName);
    }
}