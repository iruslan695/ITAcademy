package com.tc.webapp.controller.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tc.webapp.controller.Command;
import com.tc.webapp.entity.User;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;
import com.tc.webapp.service.UserService;

public class RegistrationPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		ServiceFactory factory = ServiceFactory.getInstance();
		UserService userService = factory.getUserService();
		User newUser = null;
		try {
			newUser = userService.registration(request, response);
			String url = newUser.createInfoURL();
			response.sendRedirect("Controller?command=accountPage&regInfo=ok&" + url);
		} catch (ServiceException | IOException e) {
			System.out.print("error");
		}
	}
}
