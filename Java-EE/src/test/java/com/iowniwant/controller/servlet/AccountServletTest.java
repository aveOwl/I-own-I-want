package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.iowniwant.controller.helper.TestEntity.getTestUser;

@RunWith(MockitoJUnitRunner.class)
public class AccountServletTest extends Mockito{
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext servletContext;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private PrintWriter printWriter;
    @Mock
    private Context context;
    @Mock
    private UserDao userDao;

    @InjectMocks
    private AccountServlet accountServlet = new AccountServlet();

    private static User user;

    @Before
    public void setUp() throws Exception {
        when(request.getServletContext()).thenReturn(servletContext);
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getRequestDispatcher("/account-page.jsp")).thenReturn(requestDispatcher);

        user = getTestUser();

        when(servletContext.getAttribute("user_id")).thenReturn(user.getId());
        when(userDao.getById(user.getId())).thenReturn(user);
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void shouldProceedPostRequest() throws Exception {
        doNothing().when(printWriter).write(anyString());

        accountServlet.doPost(request,response);

        InOrder inOrder = inOrder(request,response);

        inOrder.verify(request).getServletContext();
        inOrder.verify(request).setAttribute("user", user);
        inOrder.verify(response).setContentType("application/json");
        inOrder.verify(response).setCharacterEncoding("UTF-8");

        verify(requestDispatcher, atLeastOnce()).forward(request,response);
    }
}
