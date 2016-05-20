package com.iowniwant.controller.servlet;

import com.iowniwant.util.InitialContextFactoryMock;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest extends Mockito {
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private ServletContext servletContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter writer;
    @Mock
    private RequestDispatcher requestDispatcher;

    private LoginServlet loginServlet = new LoginServlet();

    @Before
    public void setUp() throws Exception {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactoryMock.class.getName());
        InitialContextFactoryMock.bind("java:/jbdc/data-postgres", dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE);

        when(request.getServletContext()).thenReturn(servletContext);
        when(response.getWriter()).thenReturn(writer);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @After
    public void tearDown() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
    }

    @Test
    public void loginServletSuccessTest() throws Exception {
        when(request.getParameter("userName")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");

        when(resultSet.getString("user_password")).thenReturn("test");

        loginServlet.doPost(request, response);
        verify(request.getServletContext(), atLeastOnce()).setAttribute(eq("user_id"), anyInt());
        verify(request.getServletContext(), atLeastOnce()).setAttribute(eq("token"), eq("logged"));
        verify(response, times(2)).addCookie(any(Cookie.class));
        verify(response.getWriter(), atLeastOnce()).write("success");
    }

    @Test
    public void loginServletFailTest() throws Exception {
        when(request.getParameter("userName")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");

        when(resultSet.getString("user_password")).thenReturn(null);

        loginServlet.doPost(request, response);
        verify(request.getServletContext(), never()).setAttribute(eq("user_id"), anyInt());
        verify(request.getServletContext(), never()).setAttribute(eq("token"), eq("logged"));
        verify(response, never()).addCookie(any(Cookie.class));
        verify(response.getWriter(), atLeastOnce()).write("fail");
    }

    @Test
    public void loginServletDoGetTest() throws Exception {
        loginServlet.doGet(request, response);

        verify(servletContext.getRequestDispatcher("/showGoalsServlet"), atLeastOnce()).forward(request, response);
    }
}