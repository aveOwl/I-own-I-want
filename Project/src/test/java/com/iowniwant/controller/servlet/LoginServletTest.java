package com.iowniwant.controller.servlet;

import com.iowniwant.util.MockInitialContextFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest extends Mockito{

    @Mock private DataSource dataSource;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private ServletContext context;
    @Mock private PrintWriter writer;
    @Mock private Connection connection;
    @Mock private PreparedStatement preparedStatement;
    @Mock private ResultSet resultSet;
    @Mock private RequestDispatcher requestDispatcher;

    private LoginServlet loginServlet = new LoginServlet();

    @Before
    public void setUp() throws Exception {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                MockInitialContextFactory.class.getName());
        MockInitialContextFactory.bind("java:/jbdc/data-postgres", dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE);

        when(request.getParameter("password")).thenReturn("pass");
        when(request.getServletContext()).thenReturn(context, context);
        when(response.getWriter()).thenReturn(writer);
        when(context.getRequestDispatcher("/showGoalsServlet")).thenReturn(requestDispatcher);
        doNothing().when(writer).write(anyString());
    }

    @After
    public void tearDown() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
    }

    @Test
    public void testDoGet() throws Exception {

        loginServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostUserValid() throws Exception {

        when(request.getParameter("userName")).thenReturn("name");
        when(resultSet.getString("user_password")).thenReturn("pass");

        loginServlet.doPost(request,response);

        verify(dataSource, times(2)).getConnection();
        verify(connection, times(2)).prepareStatement(anyString());
        verify(preparedStatement, times(2)).setString(eq(1), anyString());
        verify(preparedStatement, times(2)).executeQuery();
        verify(resultSet, times(2)).next();
        verify(connection, times(2)).close();
        verify(writer, times(1)).write("success");
        verifyNoMoreInteractions(connection);
    }

    @Test
    public void testDoPostUserInvalid() throws SQLException, ServletException, IOException {

        when(request.getParameter("userName")).thenReturn(null);

        loginServlet.doPost(request, response);

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(eq(1), anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(connection, times(1)).close();
        verifyNoMoreInteractions(connection);

        verify(writer, times(1)).write("fail");
    }
}