package com.tc.webapp.tools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tc.webapp.connection.ConnectionPool;
import com.tc.webapp.connection.ConnectionPoolException;

public class ConnectionPoolListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		//ServletContextListener.super.contextInitialized(servletContextEvent);
		ConnectionPool cp = ConnectionPool.getInstance();
	}
	
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		try {
			ConnectionPool.getInstance().dispose();
		} catch (ConnectionPoolException e) {
			System.out.println("Dispose Error");
		}
		//ServletContextListener.super.contextDestroyed(servletContextEvent);
	}
}
