package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/registrationServlet")
public class RegistrationServlet extends HttpServlet {
    public static final Logger log = LoggerFactory.getLogger(RegistrationServlet.class);

    UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (name != null && surname != null && nickname != null && password != null) {
            User user = new User(name, surname, nickname, password, email);
            userDao.create(user);
//            request.setAttribute("user", user);
        }

        response.sendRedirect("html/main-page.html");
//        getServletContext().getRequestDispatcher("/register-test.jsp").forward(request, response);
    }
}