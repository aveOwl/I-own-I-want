package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.GoalDao;
import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by sulfur on 13.04.16.
 */

@WebServlet(name = "AddGoalsServlet", urlPatterns = {"/addGoalsServlet"})
public class AddGoalsServlet extends HttpServlet {

    private GoalDao goalDao = GoalDao.getInstance();
    private UserDao userDao = UserDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = userDao.getById((Integer) request.getServletContext().getAttribute("user_id"));
        String title = request.getParameter("title");
        String shorten = request.getParameter("shorten");
        String description = request.getParameter("description");
        Date pubdate = new Date(new java.util.Date().getTime());

        if (title != null && shorten != null && description != null && pubdate != null) {
//            Goal goal = new Goal(title,shorten,pubdate,description,user);
//            goalDao.create()
        }

    }
}
