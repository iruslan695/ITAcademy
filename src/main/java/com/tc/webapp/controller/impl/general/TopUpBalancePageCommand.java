package com.tc.webapp.controller.impl.general;

import com.tc.webapp.controller.Command;
import com.tc.webapp.controller.FrontCommand;
import com.tc.webapp.entity.bean.User;
import com.tc.webapp.entity.constant.ControllerConstant;
import com.tc.webapp.entity.constant.SQLCharacteristic;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;
import com.tc.webapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TopUpBalancePageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int value = Integer.parseInt(request.getParameter(ControllerConstant.VALUE));
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SQLCharacteristic.USER);
        String login = user.getLogin();
        boolean result;
        try {
            result = userService.topUpBalance(login, value);
            System.out.println("dsdasd " + result);
            if (result) {
                System.out.println(login);
                int newBalance = userService.getBalance(login);
                System.out.println(newBalance);
                user.setBalance(newBalance);
                session.setAttribute(SQLCharacteristic.USER, user);
                response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_TOP_UP_BALANCE_PAGE + "&topUpResult=ok");
            } else {
                response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_TOP_UP_BALANCE_PAGE + "&topUpResult=error");
            }
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_ERROR_PAGE);
        }
    }
}
