package com.iowniwant.controller.filter;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthFilterTest extends Mockito {

    @Mock
    private ServletContext servletContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @Before
    public void setUp() {
        when(request.getServletContext()).thenReturn(servletContext);
    }

    @After
    public void tearDown() {}

    @Test
    public void filterTest() throws ServletException, IOException {
        // when user is logged in should proceed

        // setup - expectations
        when(servletContext.getAttribute("token")).thenReturn("logged");

        // exercise
        new AuthFilter().doFilter(request, response, filterChain);

        // verify
        assertNotNull(servletContext.getAttribute("token"));
        verify(filterChain, times(1)).doFilter(request, response);

        // when user is logged out he should be redirected to login page
        when(servletContext.getAttribute("token")).thenReturn(null);
        new AuthFilter().doFilter(request, response, filterChain);
        assertNull(servletContext.getAttribute("token"));
        verify(response, times(1)).sendRedirect("login.jsp");
    }
}
