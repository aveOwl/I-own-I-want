package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Obtains user input, verifies required fields to be not null values,
 * creates User object and persists it to the DataBase, redirects user
 * to goals page.
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = "/registrationServlet")
public class RegistrationServlet extends HttpServlet {
    /**
     * Single {@link UserDao} instance.
     */
    private UserDao userDao = UserDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User(firstName, lastName, userName, password, email);
        userDao.create(user);

        request.setAttribute("userName", userName);
        request.setAttribute("password", password);

        request.getServletContext().getRequestDispatcher("/loginServlet")
                .forward(request, response);
    }
}
