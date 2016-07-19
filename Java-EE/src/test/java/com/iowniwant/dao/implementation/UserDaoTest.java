package com.iowniwant.dao.implementation;

import com.iowniwant.model.User;
import com.iowniwant.util.InitialContextFactoryMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
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

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest extends Mockito {
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    /**
     * Test identification number.
     */
    private static final int ID = 99;

    /**
     * Test {@link User} entity.
     */
    private final User user = new User();

    /**
     * Test {@link UserDao} entity.
     */
    private final UserDao userDao = UserDao.getInstance();

    public UserDaoTest() {}
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws SQLException {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactoryMock.class.getName());
        InitialContextFactoryMock.bind("java:/jdbc/data-postgres", dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        doNothing().when(preparedStatement).setDouble(anyInt(), anyDouble());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
    }

    @After
    public void tearDown() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
    }

    @Test
    public void shouldCreateUser() throws SQLException {
        userDao.create(user);

        verify(dataSource, times(2)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(5)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).executeQuery();
        verify(preparedStatement, times(1)).getGeneratedKeys();
        verify(resultSet, times(1)).getInt(1);
        verify(resultSet, times(2)).next();
        verify(connection, times(2)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldDeleteUser() throws SQLException {
        userDao.delete(ID);

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).execute();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldUpdateUser() throws SQLException {
        userDao.update(user);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(5)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldReturnUserByUserId() throws SQLException {
        userDao.getById(ID);

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setInt(eq(1), anyInt());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shoudReturnAllUsers() throws SQLException {
        userDao.getAll();

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldReturnUserByUserName() throws SQLException {
        userDao.getByNick("test");

        verify(connection, times(1)).prepareStatement(anyString());
        verify(resultSet, times(1)).next();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }
}
