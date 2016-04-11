package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Fills the user's profile info, with the information
 * from the DataBase.
 */
@WebServlet(name = "AccountServlet", urlPatterns = "/accountServlet")
public class AccountServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AccountServlet.class);
    private UserDao userDao = UserDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer user_id = (Integer) request.getServletContext().getAttribute("user_id");
        log.debug("user_id from servletContext: {}", user_id);
        User user = userDao.getById(user_id);
        log.debug("user from DataBase: {}", user);

        request.setAttribute("firstName", user.getFirstName());
        request.setAttribute("lastName", user.getLastName());
        request.setAttribute("userName", user.getUserName());
        request.setAttribute("email", user.getEmail());

        log.trace("sending to profile page ...");
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
}
