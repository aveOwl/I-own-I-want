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

    private AuthFilter authFilter = new AuthFilter();

    @Before
    public void setUp() {
        when(request.getServletContext()).thenReturn(servletContext);
    }

    @After
    public void tearDown() {
    }

    /**
     * Tests whether the servlet passes control to a further servlet
     * via reference to a {@link #filterChain} object. It also verifies
     * that the invocation of {@code sendRedirect()} method has not occured
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void loggedAttrTest() throws IOException, ServletException {

        when(servletContext.getAttribute("token")).thenReturn("logged");

        new AuthFilter().doFilter(request,response,filterChain);

        verify(filterChain,times(1)).doFilter(request,response);
        verify(response,never()).sendRedirect("login-page.jsp");
    }

    /**
     * Tests whether the servlet redirects back to a login-page.jsp
     * and whether there were no references to the {@link #filterChain} object
     * during an execution
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void nullAttrTest() throws IOException, ServletException {

        when(servletContext.getAttribute("token")).thenReturn(null);

        new AuthFilter().doFilter(request,response,filterChain);

        verify(response,times(1)).sendRedirect("login-page.jsp");

        verify(filterChain,never()).doFilter(request,response);

    }
}
