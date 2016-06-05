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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RunWith(MockitoJUnitRunner.class)
public class AddGoalsServletTest extends Mockito{

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private DataSource dataSource;
    @Mock
    private ServletContext context;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private PrintWriter writer;

    private AddGoalsServlet addGoalsServlet = new AddGoalsServlet();

    @Before
    public void setUp() throws Exception {

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactoryMock.class.getName());
        InitialContextFactoryMock.bind("java:/jbdc/data-postgres", dataSource);

        doReturn(writer).when(response).getWriter();
        when(response.getWriter()).thenReturn(writer);
        when(resultSet.next()).thenReturn(Boolean.TRUE);
        when(request.getServletContext()).thenReturn(context);
        doReturn(connection).when(dataSource).getConnection();
        doReturn(preparedStatement).when(connection).prepareStatement(anyString(), anyInt());
        doReturn(preparedStatement).when(connection).prepareStatement(anyString());
        doNothing().when(preparedStatement).setInt(eq(1), anyInt());
        doNothing().when(writer).print(anyString());
        doReturn(resultSet).when(preparedStatement).executeQuery();
        doReturn(resultSet).when(preparedStatement).getGeneratedKeys();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDoPost() throws Exception {

        doReturn(99).when(context).getAttribute("user_id");
        doReturn("314").when(request).getParameter("cost");
        doReturn("title").when(request).getParameter("title");

        addGoalsServlet.doPost(request,response);

        verify(request, times(4)).getParameter(anyString());

        verify(response).setContentType("text/plain");
        verify(response).setCharacterEncoding("UTF-8");
        verify(writer).print(anyString());
    }
}
