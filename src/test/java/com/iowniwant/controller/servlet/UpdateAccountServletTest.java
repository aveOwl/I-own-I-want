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
import java.io.PrintWriter;

import static com.iowniwant.controller.helper.TestEntity.getTestUser;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAccountServletTest extends Mockito {
    private static final String REDIRECT_TARGET = "showGoalsServlet";

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
    private UserService userService;

    @InjectMocks
    private UpdateAccountServlet updateAccountServlet = new UpdateAccountServlet();

    private static User user;

    @Before
    public void setUp() throws Exception {
        when(request.getServletContext()).thenReturn(servletContext);
        when(response.getWriter()).thenReturn(writer);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        user = getTestUser();

        when(servletContext.getAttribute("user_id")).thenReturn(user.getId());
        when(userService.getById(user.getId())).thenReturn(user);
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void shouldUpdateAccountSuccessfully() throws Exception {
        // given
        when(request.getParameter("firstName")).thenReturn(user.getFirstName());
        when(request.getParameter("lastName")).thenReturn(user.getLastName());
        when(request.getParameter("userName")).thenReturn(user.getUserName());
        when(request.getParameter("email")).thenReturn(user.getEmail());
        when(request.getParameter("monthSalary")).thenReturn("99.00");
        when(request.getParameter("confirm_password")).thenReturn(user.getPassword());

        // when
        updateAccountServlet.doPost(request, response);

        // then
        verify(userService, atLeastOnce()).update(user);
        verify(request, times(6)).getParameter(anyString());
        verify(response, atLeastOnce()).sendRedirect(REDIRECT_TARGET);
    }
}
