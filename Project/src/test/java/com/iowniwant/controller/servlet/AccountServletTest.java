package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import com.iowniwant.util.MockInitialContextFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
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


@RunWith(MockitoJUnitRunner.class)
public class AccountServletTest extends Mockito{

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private ServletContext servletContext;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private User user;
    @Mock private PrintWriter printWriter;

    @Mock private Context context;
    @Mock private Connection connection;

    @Mock private UserDao userDao;
    @Mock private PreparedStatement preparedStatement;
    @Mock private ResultSet resultSet;

    @Mock private DataSource dataSource;

    @Spy private AccountServlet accountServlet = new AccountServlet();

    @Before
    public void setUp() throws Exception {

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                MockInitialContextFactory.class.getName());
        MockInitialContextFactory.bind("java:/jbdc/data-postgres", dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(eq(1), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE);

        when(request.getServletContext()).thenReturn(servletContext);
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getRequestDispatcher("/account.jsp")).thenReturn(requestDispatcher);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDoPost() throws Exception {

        when(servletContext.getAttribute("user_id")).thenReturn(1);
        doNothing().when(printWriter).write(anyString());

        accountServlet.doPost(request,response);

        InOrder inOrder = inOrder(request,response);

        inOrder.verify(request).getServletContext();
        inOrder.verify(request).setAttribute(eq("user"), any());
        inOrder.verify(response).setContentType("application/json");
        inOrder.verify(response).setCharacterEncoding("UTF-8");

        verify(requestDispatcher, atLeastOnce()).forward(request,response);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(servletContext.getAttribute("user_id")).thenReturn(1);
        doNothing().when(printWriter).write(anyString());

        accountServlet.doGet(request,response);
        verify(accountServlet, atLeastOnce()).doGet(request,response);
    }
}