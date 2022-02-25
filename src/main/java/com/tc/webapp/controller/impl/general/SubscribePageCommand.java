package com.tc.webapp.controller.impl.general;

import com.tc.webapp.controller.Command;
import com.tc.webapp.controller.FrontCommand;
import com.tc.webapp.entity.bean.User;
import com.tc.webapp.entity.constant.ControllerConstant;
import com.tc.webapp.entity.constant.SQLCharacteristic;
import com.tc.webapp.service.JournalService;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SubscribePageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Subscribe Page");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        JournalService journalService = serviceFactory.getJournalService();
        String journalName = request.getParameter(ControllerConstant.JOURNAL_NAME);
        int subDuration = Integer.parseInt(request.getParameter(ControllerConstant.SUB_DURATION));
        System.out.println("Journal name: " + journalName);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SQLCharacteristic.USER);////????
        int balance = user.getBalance();
        String login = user.getLogin();
        System.out.println("User login: " + login);
        System.out.println("User balance: " + balance);
        boolean result;
        try {
            result = journalService.calculateSubscription(balance, subDuration, journalName, login);
            if (result) {
                result = journalService.subscribe(login, journalName, subDuration);
                if (result) {
                    response.sendRedirect("Controller?command=goToJournalPage&" + ControllerConstant.JOURNAL_NAME + "=" + journalName);
                } else {
                    response.sendRedirect("Controller?command=goToSubscribePage&result=error");
                }
            } else {
                response.sendRedirect("Controller?command=goToSubscribePage&result=notEnoughMoney");
            }
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_ERROR_PAGE);
        }
    }
}

