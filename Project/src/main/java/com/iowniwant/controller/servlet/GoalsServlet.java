package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.Goal;
import com.iowniwant.util.DataBaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GoalServlet", urlPatterns = {"/GoalServlet"})
public class GoalsServlet extends HttpServlet{

    public String title;
    public String description;
    public String pubdate;
    public String notes;
    public double cost;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Logger log = LoggerFactory.getLogger(GoalsServlet.class);
        DataBaseManager dbManager = new DataBaseManager();
        UserDao userDao = new UserDao();

        Connection connection = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;
        List<Goal> list = new ArrayList<>();

        try {
            connection = dbManager.getConnection();
            String query = dbManager.getQuery("get.all.goal");
            prepStatement = connection.prepareStatement(query);
            resultSet = prepStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new Goal(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)  try { resultSet.close(); } catch (SQLException logOrIgnore) {}
            if (prepStatement != null) try { prepStatement.close(); } catch (SQLException logOrIgnore) {}
            if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
        }

        request.setAttribute("goals_list",list);
        request.getRequestDispatcher("/goals.jsp").forward(request,response);

    }
}
