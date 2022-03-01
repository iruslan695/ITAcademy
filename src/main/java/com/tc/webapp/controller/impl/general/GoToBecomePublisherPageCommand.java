package com.tc.webapp.controller.impl.general;

import com.tc.webapp.controller.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToBecomePublisherPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Become publisher entered");
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/becomePublisherPage.jsp");
        dispatcher.forward(request, response);
    }
}
