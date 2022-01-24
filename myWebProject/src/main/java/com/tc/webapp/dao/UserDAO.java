package com.tc.webapp.dao;

import com.tc.webapp.entity.User;

public interface UserDAO {

	String authorisation(String login, String password) throws DAOException;

	void registration(User user) throws DAOException;
}
