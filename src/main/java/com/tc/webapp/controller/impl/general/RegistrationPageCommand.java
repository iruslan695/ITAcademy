package com.tc.webapp.controller.impl.general;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tc.webapp.controller.Command;
import com.tc.webapp.controller.FrontCommand;
import com.tc.webapp.entity.bean.Person;
import com.tc.webapp.entity.bean.User;
import com.tc.webapp.entity.constant.SQLCharacteristic;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;
import com.tc.webapp.service.UserService;

public class RegistrationPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        Person newUser = null;
        try {
            String login = request.getParameter(SQLCharacteristic.LOGIN);
            String password = request.getParameter(SQLCharacteristic.PASSWORD);
            String name = request.getParameter(SQLCharacteristic.NAME);
            String surname = request.getParameter(SQLCharacteristic.SURNAME);
            String male = request.getParameter(SQLCharacteristic.MALE);
            int age = Integer.parseInt(request.getParameter(SQLCharacteristic.AGE));
            String[] interests = request.getParameterValues(SQLCharacteristic.INTERESTS);
            Person person = new Person(login, name, surname, male, interests, age);
            User user = userService.registration(person, password);
            System.out.println("Command" + user);
            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute(SQLCharacteristic.USER, user);
                response.sendRedirect("Controller?command=" + FrontCommand.ACCOUNT_PAGE + "&regInfo=regSuccess");
            } else {
                response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_REGISTRATION_PAGE + "&regInfo=regError");
            }
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_ERROR_PAGE);
        }
    }
}
