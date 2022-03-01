package com.tc.webapp.controller.impl.general;

import com.tc.webapp.controller.Command;
import com.tc.webapp.controller.FrontCommand;
import com.tc.webapp.entity.constant.ControllerConstant;
import com.tc.webapp.service.JournalService;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class GoToReadJournalPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String journalName = request.getParameter(ControllerConstant.JOURNAL_NAME);
        int releaseNumber = Integer.parseInt(request.getParameter(ControllerConstant.RELEASE_NUMBER));
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        JournalService journalService = serviceFactory.getJournalService();
        String pubName;
        try {
            pubName = journalService.getPublisherName(journalName);
            String downloadPath = ControllerConstant.UPLOAD_PATH + File.separator + pubName + File.separator + journalName
                    + File.separator + releaseNumber + ControllerConstant.FILE_EXTENSION;
            File journal = new File(downloadPath);
            request.setAttribute(ControllerConstant.JOURNAL_NAME, journalName);
            request.setAttribute(ControllerConstant.RELEASE_NUMBER, releaseNumber);
            request.setAttribute(ControllerConstant.JOURNAL, journal);
            request.setAttribute("url", downloadPath);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/readBookPage.jsp");
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_ERROR_PAGE);
        }
    }
}
