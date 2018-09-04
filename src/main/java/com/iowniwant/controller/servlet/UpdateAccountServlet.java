package com.iowniwant.controller.servlet;

import com.iowniwant.model.User;
import com.iowniwant.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.iowniwant.util.ContextHolder.getUserIdFromServletContext;

@WebServlet(name = "UpdateAccountServlet", urlPatterns = "/updateAccountServlet")
public class UpdateAccountServlet extends HttpServlet {
    private static final String GOLAS_PAGE_URI = "showGoalsServlet";
    private static Logger LOG = LoggerFactory.getLogger(UpdateAccountServlet.class);
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = getUserIdFromServletContext(request);

        User user = this.userService.getById(id);

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        Double monthSalary = Double.valueOf(request.getParameter("monthSalary"));
        String password = request.getParameter("confirm_password");

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setMonthSalary(monthSalary);
        user.setUserName(userName);

        if (password != null) {
            user.setPassword(password);
        }

        this.userService.update(user);
        LOG.info("Redirecting to goals page...");
        response.sendRedirect(GOLAS_PAGE_URI);
    }
}
