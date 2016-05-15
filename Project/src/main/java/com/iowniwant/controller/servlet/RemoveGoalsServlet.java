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

/**
 * Removes goal with the given ID from the DataBase.
 */
@WebServlet(name = "RemoveGoalsServlet", urlPatterns = "/removeGoalsServlet")
public class RemoveGoalsServlet extends HttpServlet {
    private static Logger log = LoggerFactory.getLogger(RemoveGoalsServlet.class);
    private GoalDao goalDao = GoalDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String temp = request.getParameter("id");
        Integer id = Integer.valueOf(temp.trim());

        // fetching goal using goal_id
        Goal goal = goalDao.getById(id);

        log.debug("deleting goal : {}", goal);

        // removing goal from DataBase
        goalDao.delete(id);
    }
}
