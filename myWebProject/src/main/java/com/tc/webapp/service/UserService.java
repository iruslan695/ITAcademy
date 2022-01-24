package com.tc.webapp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tc.webapp.entity.User;

public interface UserService {

	String authorisation(String login, String password) throws ServiceException;

	User registration(HttpServletRequest request, HttpServletResponse response) throws ServiceException;

	void subscribe(String journalName);

	void unsubscribe(String journalName);

}
