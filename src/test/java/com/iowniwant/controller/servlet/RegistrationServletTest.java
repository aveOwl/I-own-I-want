package com.iowniwant.controller.servlet;

import com.iowniwant.model.User;
import com.iowniwant.service.UserService;
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
    private static final String FORWARD_TARGET = "/loginServlet";

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

    private User user;

    @Before
    public void setUp() {
        user = getTestUser();

        when(userService.save(user)).thenReturn(user);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher(FORWARD_TARGET)).thenReturn(requestDispatcher);
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void shouldRegisterSuccessfully() throws Exception {
        // given
        when(request.getParameter("userName")).thenReturn(user.getUserName());
        when(request.getParameter("password")).thenReturn(user.getPassword());

        // when
        registrationServlet.doPost(request, response);

        // then
        verify(request, times(5)).getParameter(anyString());
        verify(request, atLeastOnce()).setAttribute("userName", user.getUserName());
        verify(request, atLeastOnce()).setAttribute("password", user.getPassword());
        verify(servletContext.getRequestDispatcher(FORWARD_TARGET), atLeastOnce())
                .forward(request, response);
    }
}
