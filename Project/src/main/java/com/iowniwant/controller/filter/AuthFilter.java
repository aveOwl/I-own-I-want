package com.iowniwant.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(displayName = "AuthFilter", urlPatterns = {"/showGoalsServlet"})
public class AuthFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.trace("initialize filter content");
    }

    /**
     * @param request - the request to pass along the chain.
     * @param response - the response to pass along the chain.
     * @throws IOException
     * @throws ServletException
     * Filters user request whether user logged into the system or not.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String token = (String) httpServletRequest.getServletContext().getAttribute("token");

        log.debug("token attribute from ServletContext: {}", token);

        if (token != null) {
            filterChain.doFilter(request, response);
        }
        else {
            log.debug("redirecting to login.jsp");
            httpServletResponse.sendRedirect("login.jsp");
        }
    }

    @Override
    public void destroy() {
        log.trace("destroy filter content");
    }
}
