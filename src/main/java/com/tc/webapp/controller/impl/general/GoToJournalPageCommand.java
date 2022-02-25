package com.tc.webapp.controller.impl.general;

import com.tc.webapp.controller.Command;
import com.tc.webapp.controller.FrontCommand;
import com.tc.webapp.entity.bean.User;
import com.tc.webapp.entity.constant.ControllerConstant;
import com.tc.webapp.entity.constant.SQLCharacteristic;
import com.tc.webapp.service.JournalService;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToJournalPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String journalName = request.getParameter(ControllerConstant.JOURNAL_NAME);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        JournalService journalService = serviceFactory.getJournalService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SQLCharacteristic.USER);
        String login = user.getLogin();
        String description;
        int journalPrice;
        boolean isSubbed;
        try {
            description = journalService.getJournalDescription(journalName);
            journalPrice = journalService.getJournalPrice(journalName);
            isSubbed = journalService.isSubbed(login, journalName);
            request.setAttribute(ControllerConstant.DESCRIPTION, description);
            request.setAttribute(ControllerConstant.JOURNAL_NAME, journalName);
            request.setAttribute(ControllerConstant.JOURNAL_PRICE, journalPrice);
            request.setAttribute(SQLCharacteristic.IS_SUBBED, isSubbed);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/journalPage.jsp");
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_ERROR_PAGE);
        }
    }
}

