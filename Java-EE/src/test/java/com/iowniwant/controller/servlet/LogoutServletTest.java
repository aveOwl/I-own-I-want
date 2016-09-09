package com.iowniwant.controller.servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RunWith(MockitoJUnitRunner.class)
public class LogoutServletTest extends Mockito {
    @Mock
    private ServletContext servletContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @InjectMocks
    private LogoutServlet logoutServlet = new LogoutServlet();

    @Before
    public void setUp() throws Exception {
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void shouldLogOutSuccessfully() throws Exception {
        logoutServlet.doGet(request, response);

        verify(request.getServletContext(), atLeastOnce()).removeAttribute("token");
        verify(request.getServletContext(), atLeastOnce()).removeAttribute("user_id");
        verify(request.getSession(), atLeastOnce()).invalidate();
        verify(response, atLeastOnce()).sendRedirect(anyString());
    }
}
