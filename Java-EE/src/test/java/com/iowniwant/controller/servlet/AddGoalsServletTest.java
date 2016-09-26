package com.iowniwant.controller.servlet;

import com.iowniwant.model.Goal;
import com.iowniwant.model.User;
import com.iowniwant.service.GoalService;
import com.iowniwant.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.iowniwant.controller.helper.TestEntity.getTestGoal;
import static com.iowniwant.controller.helper.TestEntity.getTestUser;

@RunWith(MockitoJUnitRunner.class)
public class AddGoalsServletTest extends Mockito{
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext context;
    @Mock
    private PrintWriter writer;
    @Mock
    private UserService userService;
    @Mock
    private GoalService goalService;

    @InjectMocks
    private AddGoalsServlet addGoalsServlet = new AddGoalsServlet();

    private User user;
    private Goal goal;

    @Before
    public void setUp() throws Exception {
        when(response.getWriter()).thenReturn(writer);
        when(request.getServletContext()).thenReturn(context);
        doNothing().when(writer).print(anyString());
        doReturn(writer).when(response).getWriter();

        user = getTestUser();
        when(userService.getById(user.getId())).thenReturn(user);

        goal = getTestGoal();
        when(goalService.save(any(Goal.class))).thenReturn(goal);
    }

    @After
    public void tearDown() throws Exception {
        user = null;
        goal = null;
    }

    @Test
    public void shouldProceedPostRequest() throws Exception {
        // given
        when(context.getAttribute("user_id")).thenReturn(user.getId());
        when(request.getParameter("title")).thenReturn(goal.getTitle());
        when(request.getParameter("cost")).thenReturn("99.00");
        when(request.getParameter("shorten")).thenReturn("test");
        when(request.getParameter("description")).thenReturn(goal.getDescription());

        // when
        addGoalsServlet.doPost(request,response);

        // then
        verify(request, times(4)).getParameter(anyString());

        verify(response).setContentType("text/plain");
        verify(response).setCharacterEncoding("UTF-8");
        verify(writer).print(anyString());
    }
}
