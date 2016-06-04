package com.iowniwant.util;

import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.iowniwant.util.UserValidation.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserValidationTest extends Mockito {
    @Mock
    private DataSource dataSource;
    @Mock
    private UserDao userDao;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private User user;

    @Before
    public void setUp() throws Exception {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                MockInitialContextFactory.class.getName());
        MockInitialContextFactory.bind("java:/jbdc/data-postgres", dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE);
    }

    @After
    public void tearDown() throws Exception {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
    }

    @Test
    public void userValidationTest() throws SQLException {
        when(user.getPassword()).thenReturn("test");
        when(userDao.getByNick("test")).thenReturn(user);

        isUserValid("test", "test");

        assertNotNull(userDao.getByNick("test"));
        assertEquals(user.getPassword(), "test");
    }
}
