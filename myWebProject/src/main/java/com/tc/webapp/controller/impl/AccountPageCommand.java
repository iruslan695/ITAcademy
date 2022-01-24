package com.tc.webapp.controller.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tc.webapp.controller.Command;

public class AccountPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Account Page entered");
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/accountPage.jsp");
		dispatcher.forward(request, response);
	}

}
