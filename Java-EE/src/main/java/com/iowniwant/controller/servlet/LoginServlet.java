package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Obtains user name and password from the login page,
 * verifies if it present in the DataBase, remembers user_id and
 * the fact that user is logged into the system in ServletContext,
 * attaches username and password to response Cookies, redirects
 * to welcome page if validation is passed, otherwise redirects to
 * login page.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/loginServlet"})
public class LoginServlet extends HttpServlet {
    /**
     * Logging system.
     */
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    /**
     * Single {@link UserDao} instance.
     */
    private UserDao userDao = UserDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/showGoalsServlet").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("userName");
        String password = request.getParameter("password");

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        User user = userDao.getByNick(username);

        if (user != null && password.equals(user.getPassword())) {

            request.getServletContext().setAttribute("user_id", user.getId());
            LOG.debug("user_id: {} successfully persisted in ServletContext", user.getId());

            request.getServletContext().setAttribute("token", "logged");
            LOG.trace("token successfully persisted in ServletContext");

            Cookie userCookie = new Cookie("ioiw.username", username);
            Cookie passCookie = new Cookie("ioiw.password", password);

            response.addCookie(userCookie);
            response.addCookie(passCookie);

            LOG.debug("setting username: {} to userCookie: {}", username, userCookie.getName());
            LOG.debug("setting password: {} to passCookie: {}", password, passCookie.getName());

            LOG.trace("redirection to goals page");
            response.getWriter().write("success");
        } else {
            response.getWriter().write("fail");
        }
    }
}
