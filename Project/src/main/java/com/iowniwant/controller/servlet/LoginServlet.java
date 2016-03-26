package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import com.iowniwant.util.UserValidation;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        getServletContext().getRequestDispatcher("/goalServlet").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.debug("username from request: {}", username);
        log.debug("password from request: {}", password);

        User user = userDao.getByNick(username);

        log.debug("username from Dao: {}", user.getNickName());
        log.debug("password from Dao: {}", user.getPassword());

        HttpSession session = request.getSession(true);

        if (isUserValid(username, password)) {
            session.setAttribute("token", "logged");
            log.trace("Setting token in HttpSession");

            log.debug("user_id from Dao: {}", user.getId());
            session.setAttribute("user_id", user.getId());
            log.trace("Setting user_id in HttpSession");

            String token =  (String) session.getAttribute("token");
            String user_id =  String.valueOf(session.getAttribute("user_id"));

            Cookie userCookie = new Cookie("ioiw.user_id", user_id);
            Cookie tokenCookie = new Cookie("ioiw.token", token);
            response.addCookie(userCookie);
            response.addCookie(tokenCookie);
            log.debug("Setting token in Cookie: {}", tokenCookie);
            log.debug("Setting user_id in Cookie: {}", userCookie);

            log.trace("redirection to welcome");
            response.sendRedirect("welcome");
        } else {
            log.debug("redirecting to login page");
            response.sendRedirect("login.jsp");
        }
    }
}