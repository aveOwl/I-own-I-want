package com.iowniwant.controller.filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Initializes mocks annotated with Mock, so that explicit
 * usage of MockitoAnnotations.initMocks(Object) is not necessary.
 * Mocks are initialized before each test method.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthFilterTest {

    /**
     * We can use @Mock to create and inject mocked instances
     * without having to call Mockito.mock manually
     */
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private ServletContext servletContext;


    /**
     * Method specifies an object, which will be returned
     * when {@code getServletContext} is invoked
     */
    @Before
    public void setup() {
        when(request.getServletContext()).thenReturn(servletContext);
    }

    @After
    public void tearDown() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
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
        verify(response,never()).sendRedirect("login.jsp");
    }

    /**
     * Tests whether the servlet redirects back to a login.jsp
     * and whether there were no references to the {@link #filterChain} object
     * during an execution
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void nullAttrTest() throws IOException, ServletException {

        when(servletContext.getAttribute("token")).thenReturn(null);

        new AuthFilter().doFilter(request,response,filterChain);

        verify(response,times(1)).sendRedirect("login.jsp");

        verify(filterChain,never()).doFilter(request,response);

    }


}
