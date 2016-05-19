package com.iowniwant.util;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static com.googlecode.catchexception.CatchException.*;

@RunWith(MockitoJUnitRunner.class)
public class DataBaseManagerTest extends Mockito {
    @Mock
    private DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        // sets up the InitialContextFactoryForMock as default factory.
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactoryMock.class.getName());
        InitialContextFactoryMock.bind("java:/jbdc/data-postgres", dataSource);

        when(dataSource.getConnection()).thenThrow(new SQLException());
    }

    @After
    public void tearDown() throws Exception {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
    }

    @Test
    public void testDataSource() throws Exception {
        Context context = new InitialContext();
        dataSource = (DataSource) context.lookup("java:/jbdc/data-postgres");

        DataBaseManager.getInstance().getConnection();

        verifyException(dataSource, SQLException.class).getConnection();

        verify(dataSource, times(2)).getConnection();

        verifyNoMoreInteractions(dataSource);
    }
}
