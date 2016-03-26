package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.GoalDao;
import com.iowniwant.model.Goal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GoalServlet", urlPatterns = {"/goalServlet"})
public class GoalServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(GoalServlet.class);

    GoalDao goalDao = new GoalDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer user_id = (Integer) request.getServletContext().getAttribute("user_id");

        List<Goal> list = goalDao.getGoalsByUserId(user_id);
        request.setAttribute("goals_list",list);
        request.getRequestDispatcher("/goals.jsp").forward(request,response);
    }
}