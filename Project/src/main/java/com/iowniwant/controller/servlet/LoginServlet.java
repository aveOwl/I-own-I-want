package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

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

        User user = userDao.getByNick(username);

        log.debug("User.getNickName() from DAO: {}", user.getNickName());
        log.debug("*****************************************");
        log.debug("User.getPassword() from DAO: {}", user.getPassword());
        log.debug("*****************************************");
        log.debug("username from request: {}", username);
        log.debug("*****************************************");
        log.debug("password from request: {}", password);
        log.debug("*****************************************");

        HttpSession session = request.getSession(true);

        if (username.equals(user.getNickName()) && password.equals(user.getPassword())) {
            session.setAttribute("token", "logged");

            log.debug("id from dao: {}", user.getId());
            session.setAttribute("user_id", user.getId());
            log.debug("*********************************");
            log.debug("id successfully persisted in the session object", user.getId());

            String token =  (String) session.getAttribute("token");
            String user_id =  String.valueOf(session.getAttribute("user_id"));

            Cookie userCookie = new Cookie("ioiw.user_id", user_id);
            Cookie tokenCookie = new Cookie("ioiw.token", token);
            response.addCookie(userCookie);
            response.addCookie(tokenCookie);

            response.sendRedirect("welcome");
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}