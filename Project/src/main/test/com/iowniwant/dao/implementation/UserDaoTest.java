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
import java.util.ArrayList;
import java.util.List;

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

    private UserDao userDao = UserDao.getInstance();
    private int id = 99;

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
        userDao.create(new User());

        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(preparedStatement, times(5)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setDouble(anyInt(), anyDouble());
        verify(resultSet, times(2)).next();
    }

    @Test
    public void userDaoCreateTestWithExceptions() throws SQLException {
        when(connection.prepareStatement(anyString(), anyInt()))
                .thenThrow(new SQLException());
        exception.expect(SQLException.class);

        try {
            userDao.create(new User());
            throw new SQLException();
        } catch (SQLException ex) {
            verify(connection, times(1)).prepareStatement(anyString(), anyInt());
            verify(preparedStatement, never()).setString(anyInt(), anyString());
            verify(preparedStatement, never()).setDouble(anyInt(), anyDouble());
            verify(resultSet, never()).next();
            throw ex;
        }
    }

    @Test
    public void userDaoDeleteTest() throws SQLException {
        userDao.delete(1);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).execute();
        verify(preparedStatement, times(1)).setInt(anyInt(), anyInt());
    }

    @Test
    public void userDaoDeleteTestWithException() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException());
        exception.expect(SQLException.class);

        try {
            userDao.delete(id);
            throw new SQLException();
        } catch (SQLException ex) {
            verify(connection, times(1)).prepareStatement(anyString());
            verify(preparedStatement, never()).execute();
            verify(preparedStatement, never()).setInt(anyInt(), anyInt());
            throw ex;
        }
    }

    @Test
    public void userDaoUpdateTest() throws SQLException {
        userDao.update(new User());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(5)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, times(1)).setInt(anyInt(), anyInt());
    }

    @Test
    public void userDaoUpdateTestWithException() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException());
        exception.expect(SQLException.class);

        try {
            userDao.update(new User());
            throw new SQLException();
        } catch (SQLException ex) {
            verify(connection, times(1)).prepareStatement(anyString());
            verify(preparedStatement, never()).setString(anyInt(), anyString());
            verify(preparedStatement, never()).setDouble(anyInt(), anyDouble());
            verify(preparedStatement, never()).setInt(anyInt(), anyInt());
            throw ex;
        }
    }

    @Test
    public void userDaoGetByIdTestWithException() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException());
        exception.expect(SQLException.class);

        try {
            userDao.getById(id);
            throw new SQLException();
        } catch (SQLException ex) {
            verify(connection, times(1)).prepareStatement(anyString());
            verify(preparedStatement, never()).executeQuery();
            verify(resultSet, never()).next();
            throw ex;
        }
    }

    @Test
    public void userDaoGetAllTest() throws SQLException {
        userDao.getAll();

        verify(connection, times(1)).prepareStatement(anyString());
        verify(resultSet, times(3)).next();
    }

    @Test
    public void userDaoGetAllTestWithException() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException());
        exception.expect(SQLException.class);

        try {
            userDao.getAll();
            throw new SQLException();
        } catch (SQLException ex) {
            verify(connection, times(1)).prepareStatement(anyString());
            verify(resultSet, times(0)).next();
            throw ex;
        }
    }

    @Test
    public void userDaoGetByNickTest() throws SQLException {
        userDao.getByNick("dullBoy");

        verify(connection, times(1)).prepareStatement(anyString());
        verify(resultSet, times(1)).next();
    }

    @Test
    public void userDaoGetByNickTestWithException() throws SQLException {
        when(connection.prepareStatement(anyString()))
                .thenThrow(new SQLException());
        exception.expect(SQLException.class);

        try {
            userDao.getByNick("dullBoy");
            throw new SQLException();
        } catch (SQLException ex) {
            verify(connection, times(1)).prepareStatement(anyString());
            verify(resultSet, times(0)).next();
            throw ex;
        }
    }
}
