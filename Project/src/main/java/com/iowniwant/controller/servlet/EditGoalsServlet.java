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


@WebServlet(name = "EditGoalsServlet", urlPatterns = "editGoals")
public class EditGoalsServlet extends HttpServlet{

    public static Logger log = LoggerFactory.getLogger(EditGoalsServlet.class);
    UserDao userDao = UserDao.getInstance();
    GoalDao goalDao = GoalDao.getInstance();

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

        Goal goal = new Goal(title,cost,description,pubdate,shorten,user);
        goalDao.update(goal);
    }
}
