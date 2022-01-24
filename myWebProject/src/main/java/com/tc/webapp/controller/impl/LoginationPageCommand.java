package com.tc.webapp.controller.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tc.webapp.controller.Command;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;
import com.tc.webapp.service.UserService;

public class LoginationPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceFactory factory = ServiceFactory.getInstance();
		UserService userService = factory.getUserService();

		String userLogin = request.getParameter("login");
		String userPassword = request.getParameter("password");
		String status = null;
		try {
			status = userService.authorisation(userLogin, userPassword);
		} catch (ServiceException e) {
			System.out.print("LOGIN ERROR");
		}
		System.out.println(status);
	}
}
