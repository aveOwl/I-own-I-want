package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import com.iowniwant.service.impl.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.iowniwant.controller.helper.TestEntity.getTestUser;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServletTest extends Mockito {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext servletContext;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationServlet registrationServlet = new RegistrationServlet();

    private static User user;

    @Before
    public void setUp() throws Exception {
        user = getTestUser();

        when(userService.save(user)).thenReturn(user);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/loginServlet")).thenReturn(requestDispatcher);
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void shouldRegisterSuccessfully() throws Exception {
        when(request.getParameter("userName")).thenReturn(user.getUserName());
        when(request.getParameter("password")).thenReturn(user.getPassword());

        registrationServlet.doPost(request, response);

        verify(request, times(5)).getParameter(anyString());
        verify(request, atLeastOnce()).setAttribute("userName", user.getUserName());
        verify(request, atLeastOnce()).setAttribute("password", user.getPassword());
        verify(servletContext.getRequestDispatcher("/loginServlet"), atLeastOnce())
                .forward(request, response);
    }
}
