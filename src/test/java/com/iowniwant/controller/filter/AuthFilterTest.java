package com.iowniwant.controller.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RunWith(MockitoJUnitRunner.class)
public class AuthFilterTest extends Mockito {
    private static final String REDIRECT_TARGET = "login-page.jsp";

    @Mock
    private ServletContext servletContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private AuthFilter authFilter;

    @Before
    public void setUp() {
        when(request.getServletContext()).thenReturn(servletContext);
    }

    /**
     * Tests whether the servlet passes control to a further servlet
     * via reference to a {@link #filterChain} object. It also verifies
     * that the invocation of {@code sendRedirect()} method has not occurred.
     */
    @Test
    public void shouldNotRedirectIfLogged() throws Exception {
        // given
        when(servletContext.getAttribute("token")).thenReturn("logged");

        // when
        authFilter.doFilter(request, response, filterChain);

        // then
        verify(filterChain, times(1)).doFilter(request, response);
        verify(response, never()).sendRedirect(REDIRECT_TARGET);
    }

    /**
     * Tests whether the servlet redirects back to a login-page.jsp
     * and whether there were no references to the {@link #filterChain} object
     * during an execution.
     */
    @Test
    public void shouldRedirectIfNotLogged() throws Exception {
        // given
        when(servletContext.getAttribute("token")).thenReturn(null);

        // when
        authFilter.doFilter(request, response, filterChain);

        // then
        verify(response, times(1)).sendRedirect(REDIRECT_TARGET);
        verify(filterChain, never()).doFilter(request, response);
    }
}
