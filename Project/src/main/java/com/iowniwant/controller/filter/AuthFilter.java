package com.iowniwant.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(displayName = "AuthFilter", urlPatterns = "/welcome")
public class AuthFilter implements Filter {
    public static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.trace("initialize filter content");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Object token = httpServletRequest.getSession().getAttribute("token");
        log.debug("****************************************");
        log.debug("token object obtained from attribute: {}", token);

        if (token != null) {

            filterChain.doFilter(request, response);
        } else {
            log.debug("*************************************");
            log.debug("YOU WERE FUCKING REDIRECTED BACK TO THE LOGIN JSP");
            httpServletResponse.sendRedirect("login.jsp");
        }
    }

    @Override
    public void destroy() {
        log.trace("destroy filter content");
    }
}