package com.iowniwant.controller.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Removes user login data from ServletContext,
 * invalidates Session, redirects user to login page.
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logoutServlet"})
public class LogoutServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getServletContext().removeAttribute("token");
        request.getServletContext().removeAttribute("user_id");
        LOG.info("Removing user_id and token logged attribute from ServletContext...");

        request.getSession().invalidate();

        LOG.info("Invalidating session...");
        LOG.info("Redirecting to front page...");
        response.sendRedirect("front-page.jsp");
    }
}
