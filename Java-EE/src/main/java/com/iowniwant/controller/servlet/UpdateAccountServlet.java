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

/**
 * Updates users private data.
 */
@WebServlet(name = "UpdateAccountServlet", urlPatterns = "/updateAccountServlet")
public class UpdateAccountServlet extends HttpServlet {
    /**
     * Logging system.
     */
    private static Logger LOG = LoggerFactory.getLogger(UpdateAccountServlet.class);

    /**
     * Single {@link UserDao} instance.
     */
    private UserDao userDao = UserDao.getInstance();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer user_id = (Integer) request.getServletContext().getAttribute("user_id");
        LOG.debug("user_id from servletContext: {}", user_id);

        User user = userDao.getById(user_id);
        LOG.debug("user from database: {}", user);

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        Double monthSalary = Double.valueOf(request.getParameter("monthSalary"));
        String password = request.getParameter("confirm_password");

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setMonthSalary(monthSalary);
        user.setUserName(userName);
        if (password != null) {
            user.setPassword(password);
        }

        userDao.update(user);
        LOG.debug("user after Update: {}", user);
        response.sendRedirect("showGoalsServlet");
    }
}
