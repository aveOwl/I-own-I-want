package com.iowniwant.controller.servlet;

import com.iowniwant.model.Goal;
import com.iowniwant.service.GoalService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ShowGoalsServletTest extends Mockito {
    private static final String FORWARD_TARGET = "/goal.jsp";

    @Mock
    private ServletContext servletContext;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private GoalService goalService;

    @InjectMocks
    private ShowGoalsServlet showGoalsServlet = new ShowGoalsServlet();

    private List<Goal> list;

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<>();

        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(goalService.getGoalsByUserId(99L)).thenReturn(list);
    }

    @After
    public void tearDown() throws Exception {
        list = null;
    }

    @Test
    public void shouldShowGoals() throws Exception {
        // given
        when(servletContext.getAttribute("user_id")).thenReturn(99L);

        // when
        showGoalsServlet.doGet(request, response);

        // then
        verify(servletContext, atLeastOnce()).getAttribute("user_id");
        verify(request, atLeastOnce()).setAttribute("goals_list", list);
        verify(request.getRequestDispatcher(FORWARD_TARGET), atLeastOnce())
                .forward(request, response);
    }
}
