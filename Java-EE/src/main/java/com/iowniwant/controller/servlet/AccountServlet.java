package com.iowniwant.controller.servlet;

import com.google.gson.Gson;
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
 * Fills the user's profile info, with the information
 * from the DataBase.
 */
@WebServlet(name = "AccountServlet", urlPatterns = "/accountServlet")
public class AccountServlet extends HttpServlet {
    /**
     * Logging system.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AccountServlet.class);

    /**
     * Single {@link UserDao} instance.
     */
    private UserDao userDao = UserDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer userId = (Integer) request.getServletContext().getAttribute("user_id");
        LOG.debug("user_id from servletContext: {}", userId);

        User user = userDao.getById(userId);
        LOG.debug("user from DataBase: {}", user);

        request.setAttribute("user", user);

        String json = new Gson().toJson(user);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

        LOG.info("sending data to account page");
        request.getRequestDispatcher("/account-page.jsp").forward(request, response);
    }
}
