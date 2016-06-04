package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.GoalDao;
import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.Goal;
import com.iowniwant.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Fetches goals for user who's id was obtained by ServletContext,
 * forwards obtained goals to goals page.
 */
@WebServlet(name = "ShowGoalsServlet", urlPatterns = {"/goalServlet"})
public class ShowGoalsServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ShowGoalsServlet.class);

    private GoalDao goalDao = GoalDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer user_id = (Integer) request.getServletContext().getAttribute("user_id");
        log.debug("fetching goals for user with id: {}", user_id);

        List<Goal> list = goalDao.getGoalsByUserId(user_id);
        log.debug("goals fetched: {}", list);
        request.setAttribute("goals_list", list);
        request.getRequestDispatcher("/goals.jsp").forward(request, response);
    }
}
