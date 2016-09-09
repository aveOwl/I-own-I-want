package com.iowniwant.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(displayName = "AuthFilter", urlPatterns = {"/showGoalsServlet"})
public class AuthFilter implements Filter {
    /**
     * Logging system.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("Initializing filter content...");
    }

    /**
     * @param request - the request to pass along the chain.
     * @param response - the response to pass along the chain.
     * @throws IOException on error.
     * @throws ServletException on error.
     * Filters user request whether user logged into the system or not.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String token = (String) httpServletRequest.getServletContext().getAttribute("token");

        LOG.debug("Token attribute from ServletContext: {}", token);

        if (token != null) {
            filterChain.doFilter(request, response);
        }
        else {
            LOG.info("Redirecting to login-page.jsp");
            httpServletResponse.sendRedirect("login-page.jsp");
        }
    }

    @Override
    public void destroy() {
        LOG.info("Destroying filter content...");
    }
}
