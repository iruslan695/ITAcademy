package com.tc.webapp.dao;

import com.tc.webapp.dao.impl.JournalDAOImpl;
import com.tc.webapp.dao.impl.SQLUserDAO;

public final class DAOFactory {
	private static final DAOFactory instance = new DAOFactory();

	private final UserDAO userDAO = new SQLUserDAO();
	private final JournalDAO journalDAO = new JournalDAOImpl();

	public static DAOFactory getInstance() {
		return instance;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public JournalDAO getPublisherDAO() {
		return journalDAO;
	}
}
