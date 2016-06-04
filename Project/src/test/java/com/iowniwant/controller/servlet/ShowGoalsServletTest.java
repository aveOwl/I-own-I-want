package com.iowniwant.controller.servlet;

import com.iowniwant.model.Goal;
import com.iowniwant.util.MockInitialContextFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ShowGoalsServletTest {

    @Mock private DataSource dataSource;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private ServletContext context;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private Connection connection;
    @Mock private PreparedStatement preparedStatement;
    @Mock private ResultSet resultSet;

    private int id = 1;
    private ShowGoalsServlet showGoals = new ShowGoalsServlet();
    private List<Goal> list = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                MockInitialContextFactory.class.getName());
        MockInitialContextFactory.bind("java:/jbdc/data-postgres", dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute("user_id")).thenReturn(id);
        when(request.getRequestDispatcher("/goals.jsp")).thenReturn(requestDispatcher);
    }

    @After
    public void tearDown() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
    }

    @Test
    public void testDoGet() throws Exception {

        showGoals.doGet(request, response);

        verify(dataSource, times(2)).getConnection();
        verify(connection, times(2)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).setInt(eq(1), anyInt());
        verify(preparedStatement, times(2)).executeQuery();
        verify(resultSet, times(2)).next();
        verify(connection,times(2)).close();
        verifyNoMoreInteractions(connection);
        verify(requestDispatcher, atLeastOnce()).forward(request,response);
    }
}