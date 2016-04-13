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
import java.sql.Date;

/**
 * Created by sulfur on 13.04.16.
 */

@WebServlet(name = "AddGoalsServlet", urlPatterns = {"/addGoalsServlet"})
public class AddGoalsServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(AjaxServlet.class);
    private GoalDao goalDao = GoalDao.getInstance();
    private UserDao userDao = UserDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = userDao.getById((Integer) request.getServletContext().getAttribute("user_id"));
        log.debug("id obtained from the context: {}", request.getServletContext().getAttribute("user_id"));
        response.getWriter().println(request.getServletContext().getAttribute("user_id"));
        String title = request.getParameter("title");
        log.debug("Title was obtained due to the ajax function: {}", title);
        Double cost = Double.valueOf(request.getParameter("cost"));
        log.debug("Title was obtained due to the ajax function: {}", cost);
        String shorten = request.getParameter("shorten");
        log.debug("Title was obtained due to the ajax function: {}", shorten);
        String description = request.getParameter("description");
        log.debug("Title was obtained due to the ajax function: {}", description);
        Date pubdate = new Date(new java.util.Date().getTime());
        log.debug("This date will be persisted in the databse: {}", pubdate);

        if (title != null && shorten != null && description != null && pubdate != null) {
            Goal goal = new Goal(title,cost,shorten,pubdate,description,user);
            goalDao.create(goal);
        }

    }
}
