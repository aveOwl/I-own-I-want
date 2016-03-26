package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.GoalDao;
import com.iowniwant.model.Goal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GoalServlet", urlPatterns = {"/goalServlet"})
public class GoalServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(GoalServlet.class);

    GoalDao goalDao = new GoalDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer user_id = (Integer) request.getSession().getAttribute("user_id");

        if(user_id == null) {

            Cookie[] theCookies = request.getCookies();

            log.debug("Cookies arrays is empty");
            if (theCookies != null) {
                for (Cookie tempCoockie : theCookies) {

                    if ("ioiw.user_id".equals(tempCoockie.getName())) {
                        user_id =  Integer.valueOf(tempCoockie.getValue());
                        log.debug("***********************************");
                        log.debug("you have created a user_id parsing cookie: {}", user_id);
                        request.getServletContext().setAttribute("user_id", user_id);
                    }
                }
            }
            log.debug("you do access user_id == null clause");
        }
        log.debug("user_id attribute obtained from request: {}", user_id);

        List<Goal> list = goalDao.getGoalsByUserId(user_id);
        request.setAttribute("goals_list",list);
        request.getRequestDispatcher("/goals.jsp").forward(request,response);
    }
}