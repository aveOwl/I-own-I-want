package com.iowniwant.util;

import com.iowniwant.controller.helper.InitialContextFactoryMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.spi.InitialContextFactory;
import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(MockitoJUnitRunner.class)
public class DataBaseManagerTest extends Mockito {
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactoryMock.class.getName());
        InitialContextFactoryMock.bind("java:/jdbc/data-postgres", dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
    }

    @After
    public void tearDown() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                InitialContextFactory.class.getName());
    }

    @Test
    public void shouldReceiveConnectionWithDataSource() throws Exception {
        Context context = new InitialContext();
        dataSource = (DataSource) context.lookup("java:/jdbc/data-postgres");

        DataBaseManager.getInstance().getDbConnection();

        verify(dataSource, times(1)).getConnection();
        verifyNoMoreInteractions(dataSource);
    }
}
