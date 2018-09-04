package com.iowniwant.controller.servlet;

import com.iowniwant.model.User;
import com.iowniwant.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationServlet.class);
    private static final String LOGIN_PAGE_URI = "/loginServlet";

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LOG.debug("Obtained user info from registration form: \n " +
                        "FirstName = {}, LastName = {}, UserName = {}, Email = {}, Password = {}",
                firstName, lastName, userName, email, password);

        User savedUser = this.userService.save(new User(firstName, lastName, userName, password, email));

        request.setAttribute("userName", userName);
        request.setAttribute("password", password);

        LOG.debug("User: {} registered", savedUser);

        LOG.info("Forwarding request to loginServlet...");
        request.getServletContext().getRequestDispatcher(LOGIN_PAGE_URI)
                .forward(request, response);
    }
}
