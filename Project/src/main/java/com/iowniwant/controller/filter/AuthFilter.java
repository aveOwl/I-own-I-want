package com.iowniwant.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//тут уже стоял /goalServlet зачем не знаю
@WebFilter(displayName = "AuthFilter", urlPatterns = {"/welcome", "/goalServlet"})
public class AuthFilter implements Filter {
    public static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

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

        HttpSession session = httpServletRequest.getSession(true);

        log.debug("Session with ID: {} created", session.getId());

        log.debug("******************************************");
        log.debug("username obtained from servlet context: {}", request.getServletContext().getAttribute("username"));
        log.debug("******************************************");
        log.debug("password obtained from servlet context: {}", request.getServletContext().getAttribute("password"));

         if (request.getServletContext().getAttribute("username") != null
                && request.getServletContext().getAttribute("password") != null){
            log.debug("**************************************");
            log.debug("error occurs in the context obtaining if");
            httpServletRequest.getRequestDispatcher("loginServlet").forward(request,response);
        } else if (session.getAttribute("token") != null) {
             session.setAttribute("logged", true);
             log.debug("**************************************");
             log.debug("program commits to the token if");
             log.debug("your token prameter equals: {}", session.getAttribute("token"));
             filterChain.doFilter(request, response);
         }
        else {
            log.debug("You were redirected back to the login jsp");
            httpServletResponse.sendRedirect("login.jsp");
        }
    }

    @Override
    public void destroy() {
        log.trace("destroy filter content");
    }
}