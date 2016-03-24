package com.iowniwant.controller;

import com.iowniwant.model.Goal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sulfur on 21.03.16.
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet{

    public PreparedStatement preparedStatement;
    public Connection connection;
    public String title;
    public String description;
    public String pubdate;
    public String notes;
    public double cost;
    private static final String URL = "jdbc:postgresql://localhost:5432/DB-java-ee";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private final String GET_SOME = "SELECT * FROM users.goals";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Goal> list = new LinkedList<>();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
           /* preparedStatement = connection.prepareStatement(GET_SOME);
            preparedStatement.setString(1,key);*/
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_SOME);

            while (rs.next()) {
                title = rs.getString("title");
                cost = rs.getDouble("cost");
                description = rs.getString("description");
                pubdate = rs.getString("pubdate");
                notes = rs.getString("notes");
                list.add(new Goal(title,cost,description,pubdate,notes));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("goals_list",list);
        request.getRequestDispatcher("/jsp/goals.jsp").forward(request,response);

    }
}
