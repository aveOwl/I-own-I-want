package com.iowniwant.dao.implementation;

import com.iowniwant.model.User;
import com.iowniwant.util.InitialContextFactoryMock;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;
import java.sql.*;

import static org.junit.Assert.*;

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

    private int id = 99;
    private User user = new User();
    private UserDao userDao = UserDao.getInstance();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws SQLException {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactoryMock.class.getName());
        InitialContextFactoryMock.bind("java:/jbdc/data-postgres", dataSource);

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
    public void userDaoCreateTest() throws SQLException {
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
    public void userDaoDeleteTest() throws SQLException {
        userDao.delete(id);

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, times(1)).execute();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void userDaoUpdateTest() throws SQLException {
        userDao.update(user);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(5)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, times(1)).setInt(anyInt(), anyInt());
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void userDaoGetByIdTest() throws SQLException {
        userDao.getById(id);

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setInt(eq(1), anyInt());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void userDaoGetAllTest() throws SQLException {
        userDao.getAll();

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void userDaoGetByNickTest() throws SQLException {
        userDao.getByNick("test");

        verify(connection, times(1)).prepareStatement(anyString());
        verify(resultSet, times(1)).next();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }
}
