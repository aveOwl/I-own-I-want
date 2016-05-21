package com.iowniwant.controller.servlet;

import com.iowniwant.model.User;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAccountServletTest extends Mockito {
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
    @Mock
    private User user;

    private UpdateAccountServlet updateAccountServlet = new UpdateAccountServlet();

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
    public void updateAccountServletSuccessTest() throws Exception {
        when(servletContext.getAttribute("user_id")).thenReturn(99);
        when(request.getParameter("monthSalary")).thenReturn("100.0");

        updateAccountServlet.doPost(request, response);

        assertNotNull(servletContext.getAttribute("user_id"));
        verify(request, times(6)).getParameter(anyString());
        verify(response, atLeastOnce()).sendRedirect("showGoalsServlet");
    }
}