package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.GoalsDAO;
import com.iowniwant.model.Goal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GoalServlet", urlPatterns = {"/goalServlet"})
public class GoalsServlet extends HttpServlet{

    GoalsDAO goalsDAO = new GoalsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Goal> list = goalsDAO.getAll();

        request.setAttribute("goals_list",list);
        request.getRequestDispatcher("/goals.jsp").forward(request,response);
    }
}