package com.iowniwant.controller.servlet;

import com.google.gson.Gson;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer userId = (Integer) request.getServletContext().getAttribute("user_id");
        log.debug("user_id from servletContext: {}", userId);
        
        User user = userDao.getById(userId);
        log.debug("user from DataBase: {}", user);

        request.setAttribute("user", user);

        String json = new Gson().toJson(user);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

        log.trace("sending data to account page");
        request.getRequestDispatcher("/account.jsp").forward(request, response);
    }
}
