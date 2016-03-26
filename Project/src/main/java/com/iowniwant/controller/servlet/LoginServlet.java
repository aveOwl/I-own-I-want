package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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



        if (username.equals(user.getNickName()) && password.equals(user.getPassword())) {
            request.getSession().setAttribute("token", 1);

            log.debug("id from dao: {}", user.getId());
            request.getSession().setAttribute("user_id", user.getId());

            Cookie[] theCookies = request.getCookies();

            if (theCookies == null) {
                Cookie userCoockie = new Cookie("ioiw.user_id", password);
                Cookie loggedCoockie = new Cookie("ioiw.logged", password);
                response.addCookie(userCoockie);
            }
            response.sendRedirect("welcome");
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}