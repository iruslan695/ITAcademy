package com.tc.webapp.controller.impl.general;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tc.webapp.controller.Command;
import com.tc.webapp.controller.FrontCommand;
import com.tc.webapp.entity.bean.User;
import com.tc.webapp.entity.constant.SQLCharacteristic;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;
import com.tc.webapp.service.UserService;

public class LoginationPageCommand implements Command {
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();

        String userLogin = request.getParameter(LOGIN);
        String userPassword = request.getParameter(PASSWORD);
        System.out.println(userLogin);
        User user = null;
        try {
            System.out.println("auth command");
            System.out.println(userLogin + " " + userPassword);
            user = userService.authorisation(userLogin, userPassword);
            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute(SQLCharacteristic.USER, user);
                response.sendRedirect("Controller?command=" + FrontCommand.ACCOUNT_PAGE + "&logInfo=loginSuccess");
            } else {
                response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_HELLO_PAGE + "&logInfo=loginError");
            }
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_ERROR_PAGE);
        }
    }
}
