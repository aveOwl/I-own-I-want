package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import static com.iowniwant.util.UserValidation.*;

/**
 * Obtains user name and password from the login page,
 * verifies if it present in the DataBase, remembers user_id and
 * the fact that user is logged into the system in ServletContext,
 * attaches username and password to response Cookies, redirects
 * to welcome page if validation is passed, otherwise redirects to
 * login page.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/loginServlet", "/welcome"})
public class LoginServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    private UserDao userDao = UserDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/goalServlet").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("userName");
        String password = request.getParameter("password");

        User user = userDao.getByNick(username);

        log.debug("username present in the DataBase: {}", username.equals(user.getUserName()));
        log.debug("password present in the DataBase: {}", password.equals(user.getPassword()));

        if (isUserValid(user)) {

            log.debug("user_id from DataBase: {}", user.getId());

            request.getServletContext().setAttribute("user_id", user.getId());
            log.debug("user_id successfully persisted in ServletContext", user.getId());

            request.getServletContext().setAttribute("token", "logged");
            log.trace("token successfully persisted in ServletContext");

            Cookie userCookie = new Cookie("ioiw.username", username);
            Cookie passCookie = new Cookie("ioiw.password", password);
            response.addCookie(userCookie);

            log.debug("Setting user_id in Cookie: {}", userCookie);

            response.addCookie(passCookie);
            log.debug("setting username: {} to userCookie: {}", username, userCookie.getName());
            log.debug("setting password: {} to passCookie: {}", password, passCookie.getName());

            log.trace("redirection to welcome");
            response.sendRedirect("welcome");
        } else {
            log.debug("redirecting to login page");
            response.sendRedirect("login.jsp");
        }
    }
}
