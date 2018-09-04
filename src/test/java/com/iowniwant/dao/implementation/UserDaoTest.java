package com.iowniwant.dao.implementation;

import com.iowniwant.controller.helper.InitialContextFactoryMock;
import com.iowniwant.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.iowniwant.controller.helper.TestEntity.getTestUser;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest extends Mockito {
    private static final Long TEST_ID = 99L;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private UserDao userDao = new UserDao();
    private User user;

    @Before
    public void setUp() throws SQLException {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactoryMock.class.getName());
        InitialContextFactoryMock.bind("java:/jdbc/data-postgres", dataSource);

        user = getTestUser();

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        doNothing().when(preparedStatement).setDouble(anyInt(), anyDouble());
        doNothing().when(preparedStatement).setLong(anyInt(), anyInt());
    }

    @After
    public void tearDown() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
        user = null;
    }

    @Test
    public void shouldCreateUser() throws SQLException {
        // when
        userDao.create(user);

        // then
        verify(dataSource, times(2)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(5)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, times(1)).setLong(anyInt(), anyInt());
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).executeQuery();
        verify(preparedStatement, times(1)).getGeneratedKeys();
        verify(resultSet, times(1)).getLong(1);
        verify(resultSet, times(2)).next();
        verify(connection, times(2)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldDeleteUser() throws SQLException {
        // when
        userDao.delete(TEST_ID);

        // then
        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setLong(anyInt(), anyInt());
        verify(preparedStatement, times(1)).execute();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldUpdateUser() throws SQLException {
        // when
        userDao.update(user);

        // then
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(5)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, times(1)).setLong(anyInt(), anyInt());
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldReturnUserByUserId() throws SQLException {
        // when
        userDao.getById(TEST_ID);

        // then
        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setLong(eq(1), anyInt());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldReturnAllUsers() throws SQLException {
        // when
        userDao.getAll();

        // then
        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldReturnUserByUserName() throws SQLException {
        // when
        userDao.getByUserName(user.getUserName());

        // then
        verify(connection, times(1)).prepareStatement(anyString());
        verify(resultSet, times(1)).next();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }
}
