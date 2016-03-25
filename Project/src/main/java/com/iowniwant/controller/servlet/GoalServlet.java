package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.GoalDAO;
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
public class GoalServlet extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GoalServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer user_id = (Integer) request.getSession().getAttribute("user_id");
        log.debug("user_id attribute obtained from request");

        GoalDAO goalDAO = new GoalDAO();
        List<Goal> list = goalDAO.getGoalsByUserId(user_id);
        request.setAttribute("goals_list",list);
        request.getRequestDispatcher("/goals.jsp").forward(request,response);
    }

}
