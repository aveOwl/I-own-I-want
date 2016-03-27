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

@WebServlet(name = "LoginServlet", urlPatterns = {"/loginServlet", "/welcome"})
public class LoginServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/goalServlet").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.debug("username from request: {}", username);
        log.debug("password from request: {}", password);

        User user = userDao.getByNick(username);

        log.debug("username from Dao: {}", user.getNickName());
        log.debug("password from Dao: {}", user.getPassword());

        if (isUserValid(username, password)) {

            log.debug("user_id from dao: {}", user.getId());

            request.getServletContext().setAttribute("user_id", user.getId());
            log.debug("user_id successfully persisted in ServletContext", user.getId());

            request.getServletContext().setAttribute("token", "logged");
            log.trace("setting token attribute via ServletContext");

            Cookie userCookie = new Cookie("ioiw.username", username);
            Cookie passCookie = new Cookie("ioiw.password", password);
            response.addCookie(userCookie);
            response.addCookie(passCookie);
            log.debug("Setting username: {} to userCookie: {}", username, userCookie);
            log.debug("Setting password: {} to passCookie: {}", password, passCookie);

            log.trace("redirection to welcome");
            response.sendRedirect("welcome");
        } else {
            log.debug("redirecting to login page");
            response.sendRedirect("login.jsp");
        }
    }
}