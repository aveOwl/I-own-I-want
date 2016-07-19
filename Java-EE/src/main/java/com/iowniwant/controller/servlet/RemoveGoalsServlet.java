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

/**
 * Removes goal with the given ID from the DataBase.
 */
@WebServlet(name = "RemoveGoalsServlet", urlPatterns = "/removeGoalsServlet")
public class RemoveGoalsServlet extends HttpServlet {
    /**
     * Logging system.
     */
    private static Logger LOG = LoggerFactory.getLogger(RemoveGoalsServlet.class);

    /**
     * Single {@link GoalDao} instance.
     */
    private GoalDao goalDao = GoalDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id").trim());
        LOG.debug("getting id of the goal to be deleted id: {}", id);

        // fetching goal_view using goal_id
        Goal goal = goalDao.getById(id);
        LOG.debug("deleting goal : {}", goal);

        // removing goal from DataBase
        goalDao.delete(id);
    }
}
