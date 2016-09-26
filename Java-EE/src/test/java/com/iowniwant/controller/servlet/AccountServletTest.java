package com.iowniwant.controller.servlet;

import com.iowniwant.model.User;
import com.iowniwant.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.iowniwant.controller.helper.TestEntity.getTestUser;

@RunWith(MockitoJUnitRunner.class)
public class AccountServletTest extends Mockito {
    private static final String FORWARD_TARGET = "/account-page.jsp";

    private static final Long TEST_ID = 99L;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext servletContext;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private PrintWriter printWriter;
    @Mock
    private Context context;
    @Mock
    private UserService userService;

    @InjectMocks
    private AccountServlet accountServlet = new AccountServlet();

    private static User user;

    @Before
    public void setUp() throws Exception {
        when(request.getServletContext()).thenReturn(servletContext);
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getRequestDispatcher(FORWARD_TARGET)).thenReturn(requestDispatcher);

        user = getTestUser();
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void shouldProceedPostRequest() throws Exception {
        // given
        when(servletContext.getAttribute("user_id")).thenReturn(TEST_ID);

        when(userService.getById(TEST_ID))
                .thenReturn(user);

        doNothing().when(printWriter).write(anyString());

        // when
        accountServlet.doPost(request, response);

        // then
        InOrder inOrder = inOrder(request, response);

        inOrder.verify(request).getServletContext();
        inOrder.verify(request).setAttribute("user", user);
        inOrder.verify(response).setContentType("application/json");
        inOrder.verify(response).setCharacterEncoding("UTF-8");

        verify(request, atLeastOnce()).getRequestDispatcher(FORWARD_TARGET);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}
