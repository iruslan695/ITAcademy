package com.tc.webapp.controller.impl.general;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tc.webapp.controller.Command;

public class GoToCatalogPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Catalog Page entered");
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/catalogPage.jsp");
		dispatcher.forward(request, response);
	}

}
