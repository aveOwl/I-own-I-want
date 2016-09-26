package com.iowniwant.controller.servlet;

import com.iowniwant.model.User;
import com.iowniwant.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Obtains user name and password from the login page,
 * verifies if it present in the database, remembers user_id and
 * the fact that user is logged into the system in ServletContext,
 * attaches username and password to response Cookies, redirects
 * to welcome page if validation is passed, otherwise redirects to
 * login page.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/loginServlet"})
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/showGoalsServlet").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("userName");
        String password = request.getParameter("password");

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        User user = this.userService.getByUserName(username);

        if (user != null && password.equals(user.getPassword())) {

            request.getServletContext().setAttribute("user_id", user.getId());
            LOG.debug("New user_id: {} successfully persisted in ServletContext", user.getId());

            request.getServletContext().setAttribute("token", "logged");
            LOG.info("Token logged successfully persisted in ServletContext");

            Cookie userCookie = new Cookie("ioiw.username", username);
            Cookie passCookie = new Cookie("ioiw.password", password);

            response.addCookie(userCookie);
            response.addCookie(passCookie);

            LOG.debug("Setting username: {} to userCookie: {}", username, userCookie.getName());
            LOG.debug("Setting password: {} to passCookie: {}", password, passCookie.getName());

            LOG.info("Redirecting to goals page...");
            response.getWriter().write("success");
        } else {
            LOG.error("User verification failed...");
            response.getWriter().write("fail");
        }
    }
}
