package com.iowniwant.controller.servlet;

import com.iowniwant.service.impl.GoalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Removes goal with the given id from the database.
 */
@WebServlet(name = "RemoveGoalsServlet", urlPatterns = "/removeGoalsServlet")
public class RemoveGoalsServlet extends HttpServlet {
    private static Logger LOG = LoggerFactory.getLogger(RemoveGoalsServlet.class);

    private GoalService goalService = new GoalService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id").trim());
        LOG.debug("Fetching id of the goal to be deleted: {}", id);

        this.goalService.delete(id);
    }
}
