package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.GoalDao;
import com.iowniwant.dao.implementation.UserDao;
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
 * Created by sulfur on 14.04.16.
 */


@WebServlet(name = "RemoveGoalsServlet", urlPatterns = "/removeGoalsServlet")
public class RemoveGoalsServlet extends HttpServlet{

    private static Logger log = LoggerFactory.getLogger(AddGoalsServlet.class);
    private GoalDao goalDao = GoalDao.getInstance();
    private UserDao userDao = UserDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = userDao.getById((Integer) request.getServletContext().getAttribute("user_id"));
        log.debug("id obtained from the context: {}", request.getServletContext().getAttribute("user_id"));
        response.getWriter().println(request.getServletContext().getAttribute("user_id"));
        String temp = request.getParameter("id");
        log.debug("Title was obtained due to the ajax function: {}", temp);
        Integer id = Integer.valueOf(temp.trim());
        log.debug("Goal with the following id is about to be deleted: {}", id);
        goalDao.delete(goalDao.getByViewId(id).getId());

       /* if (title != null) {

        }*/
    }
}
