package com.tc.webapp.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionPoolListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //ConnectionPool cp = ConnectionPool.getInstance();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //try {
        //    ConnectionPool.getInstance().dispose();
        //} catch (ConnectionPoolException e) {
        //    throw new RuntimeException("Closing connection pool error", e);
        //}
    }
}
