package com.iowniwant.controller.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sulfur on 13.04.16.
 */
@WebServlet(name = "AjaxServlet", urlPatterns = {"/ajaxServlet"})
public class AjaxServlet extends HttpServlet{
    private static Logger log = LoggerFactory.getLogger(AjaxServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        log.debug("Parameter fetched: {}", request.getParameter("description"));
        try {

        /* TODO output your response here.*/

            out.println(request.getParameter("description"));
        } finally {
            out.close();
        }



    }
}
