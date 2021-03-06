package com.iowniwant.controller.servlet;

import com.iowniwant.model.Goal;
import com.iowniwant.service.GoalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.iowniwant.util.ContextHolder.getUserIdFromServletContext;

/**
 * Fetches goals for user who's id was obtained by ServletContext,
 * forwards obtained goals to goals page.
 */
@WebServlet(name = "ShowGoalsServlet", urlPatterns = {"/showGoalsServlet"})
public class ShowGoalsServlet extends HttpServlet {
    private static final String GOALS_PAGE_URI = "/goals-page.jsp";
    private static Logger LOG = LoggerFactory.getLogger(ShowGoalsServlet.class);
    private GoalService goalService = new GoalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = getUserIdFromServletContext(request);

        List<Goal> goalsList = this.goalService.getGoalsByUserId(id);

        request.setAttribute("goals_list", goalsList);

        LOG.info("Sending data to goals page...");
        request.getRequestDispatcher(GOALS_PAGE_URI).forward(request, response);
    }
}
