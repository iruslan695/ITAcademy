package com.tc.webapp.controller.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tc.webapp.controller.Command;

public class GoToRegistrationPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Registration Page entered");
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/registrationPage.jsp");
		dispatcher.forward(request, response);
	}

}
