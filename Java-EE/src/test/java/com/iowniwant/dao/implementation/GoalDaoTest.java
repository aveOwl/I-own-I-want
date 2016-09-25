package com.iowniwant.dao.implementation;

import com.iowniwant.model.Goal;
import com.iowniwant.model.User;
import com.iowniwant.controller.helper.InitialContextFactoryMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class GoalDaoTest extends Mockito {
    private static final Long GOAL_ID = 99L;

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Goal goal;
    @Mock
    private User user;

    @InjectMocks
    private GoalDao goalDao = new GoalDao();

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
        when(goal.getUser()).thenReturn(new User());
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        doNothing().when(preparedStatement).setDouble(anyInt(), anyDouble());
        doNothing().when(preparedStatement).setDate(anyInt(), any(Date.class));
        doNothing().when(preparedStatement).setLong(anyInt(), anyInt());
    }

    @After
    public void tearDown() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
    }

    @Test
    public void shouldReturnGoalsByUserId() throws Exception {
        goalDao.getGoalsByUserId(GOAL_ID);

        verify(connection, times(2)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).setLong(eq(1), anyInt());
        verify(preparedStatement, times(2)).setLong(eq(1), anyInt());
        verify(preparedStatement, times(2)).executeQuery();
    }

    @Test
    public void shouldCreateGoal() throws SQLException {
        when(goal.getUser()).thenReturn(user);

        goalDao.create(goal);

        verify(connection, atLeastOnce()).prepareStatement(anyString(), anyInt());
        verify(preparedStatement, atLeastOnce()).executeUpdate();

        InOrder inOrder = inOrder(connection, preparedStatement);
        inOrder.verify(connection).prepareStatement(anyString());
        inOrder.verify(preparedStatement).setLong(anyInt(), anyInt());
        inOrder.verify(preparedStatement).executeQuery();
    }

    @Test
    public void shouldReturnGoalByGoalId() throws SQLException {
        goalDao.getById(GOAL_ID);

        InOrder inOrder = inOrder(connection, preparedStatement);
        inOrder.verify(connection).prepareStatement(anyString());
        inOrder.verify(preparedStatement).setLong(anyInt(),anyInt());
        inOrder.verify(preparedStatement).executeQuery();
    }

    @Test
    public void shouldUpdateGoal() throws SQLException {
        goalDao.update(goal);

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());

        verify(preparedStatement, times(3)).setString(anyInt(),anyString());
        verify(preparedStatement, times(1)).setDouble(anyInt(), anyDouble());
        verify(preparedStatement, times(1)).setDate(anyInt(), any(Date.class));
        verify(preparedStatement, times(1)).setLong(anyInt(), anyInt());
        verify(preparedStatement, times(1)).executeUpdate();

        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldDeleteGoal() throws SQLException {
        goalDao.delete(GOAL_ID);

        verify(dataSource, times(1)).getConnection();
        verify(connection, atLeastOnce()).prepareStatement(anyString());
        verify(preparedStatement, atLeastOnce()).setLong(eq(1), anyInt());
        verify(preparedStatement, atLeastOnce()).execute();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void shouldReturnAllGoals() throws SQLException {
        goalDao.getAll();

        verify(dataSource, times(2)).getConnection();
        verify(connection, times(2)).prepareStatement(anyString());
        verify(preparedStatement, atLeastOnce()).executeQuery();
        verify(preparedStatement, times(1)).setLong(eq(1), anyInt());
        verify(resultSet, times(3)).next();
        verify(connection, times(2)).close();
        verifyNoMoreInteractions(connection);
    }
}
