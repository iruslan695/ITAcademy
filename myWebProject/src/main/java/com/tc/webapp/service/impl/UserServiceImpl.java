package com.tc.webapp.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.tc.webapp.dao.DAOException;
import com.tc.webapp.dao.DAOFactory;
import com.tc.webapp.dao.UserDAO;
import com.tc.webapp.entity.User;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public String authorisation(String login, String password) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		UserDAO userDAO = factory.getUserDAO();
		try {
			userDAO.authorisation(login, password);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return "admin yeah";

	}

	@Override
	public User registration(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		User user = new User(request,response);
		DAOFactory factory = DAOFactory.getInstance();
		UserDAO userDAO = factory.getUserDAO();
		try {
			userDAO.registration(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return user;
	}

	@Override
	public void subscribe(String journalName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsubscribe(String journalName) {
		// TODO Auto-generated method stub

	}
}
